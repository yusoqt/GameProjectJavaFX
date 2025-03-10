package game;

import java.util.*;
import java.util.stream.Collectors;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import characters.Character;
import effects.BaseDotEffect;
import enemies.*;
import items.Item;
import player.Player;
import skills.Skill;
import soundmanager.SoundManager;
import ui.UI_GameSummary;

public class Game {
	private static Game currentGame;

	private final Stage gameStage;
	private final Difficulty gameDifficulty;
	private final List<Theme> themeList;
	private final List<Monster> monsterList;
	private final List<BaseBoss> bossList;
	private final Random randomGenerator;

	private Theme currentTheme;

	private int playerHP;
	private int enemyHP;

	private Label playerHPLabel;
	private Label enemyHPLabel;
	private Label enemyNameLabel; // Label for displaying enemy name
	private ProgressBar playerHealth;
	private ProgressBar playerMana;
	private ProgressBar enemyHealth;

	private Player player; // เปลี่ยนจาก Character เป็น Player
	private Character currentEnemy;

	private HBox mainButtonBox;
	private VBox skillButtonBox;

	private Set<Monster> defeatedMonsters; // เปลี่ยนจาก List เป็น Set
	private boolean isFightingBoss;
	private GameStats gameStats;
	private List<Skill> playerSkills; // เพิ่มตัวแปร playerSkills
	private Label playerMPLabel; // เพิ่มตัวแปรสำหรับ Label MP
	private Map<String, Integer> skillCooldowns = new HashMap<>(); // เพิ่มตัวแปรใหม่
	private Label playerStatusLabel; // สำหรับแสดงสถานะของผู้เล่น
	private Label enemyStatusLabel; // สำหรับแสดงสถานะของศัตรู
	private Scene scene;

	private ImageView playerImage;
	private ImageView enemyImage = new ImageView();

	private ImageView ThemeGame = new ImageView();

	private DifficultyManager difficultyManager;

	public Game(Stage stage, Difficulty difficulty) {
		this.gameStage = stage;
		this.gameDifficulty = difficulty;
		this.gameStats = new GameStats(); // สร้าง instance ของ GameStats
		gameStage.setResizable(false); // ไม่ให้ปรับขนาดหน้าต่างได้
		currentGame = this;
		this.themeList = new ArrayList<>();
		this.monsterList = new ArrayList<>();
		this.bossList = new ArrayList<>();
		this.randomGenerator = new Random();
		this.playerHP = 100; // Initial player HP
		this.enemyHP = 0; // Initial monster HP
		this.defeatedMonsters = new HashSet<>(); // ใช้ HashSet แทน ArrayList
		this.isFightingBoss = false;

		player = new Player("Adventure"); // สร้าง Player แทน Character
		this.playerSkills = ((Player) player).getSkills(); // เก็บ skills ของ player

		this.difficultyManager = new DifficultyManager(difficulty);
		Monster.setDifficultyManager(difficultyManager); // ส่ง manager ไปให้ Monster class

		initializeThemes();
		initializeMonstersAndBosses();

		createScene();

		gameStage.setScene(scene);
		gameStage.show();

		fight();
	}

	public Scene getScene() {
		return scene;
	}

	public void createScene() {
		BorderPane root = new BorderPane();

		Pane topPane = new Pane();
		topPane.setPrefHeight(400);

		ThemeGame.setFitWidth(960);
		ThemeGame.setFitHeight(400);
		ThemeGame.setOpacity(0.8);

		topPane.getChildren().add(ThemeGame);

		Rectangle frameEnemy = new Rectangle();
		frameEnemy.setX(650); // ปรับตำแหน่งให้ใหญ่กว่า Label เล็กน้อย
		frameEnemy.setY(290);
		frameEnemy.setWidth(250); // ปรับความกว้างให้ครอบคลุมทั้งหมด
		frameEnemy.setHeight(100); // ปรับความสูงให้ครอบคลุมทั้งหมด
		frameEnemy.setFill(Color.WHITE.deriveColor(0, 1, 1, 0.5));
		frameEnemy.setStroke(null);
		frameEnemy.setArcWidth(20); // ความโค้งในแนวนอน
		frameEnemy.setArcHeight(20); // ความโค้งในแนวตั้ง

		enemyImage.setFitWidth(250);
		enemyImage.setFitHeight(280);
		enemyImage.setLayoutX(650);
		enemyImage.setLayoutY(20);

		enemyNameLabel = new Label();
		enemyNameLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
		enemyNameLabel.setLayoutX(660);
		enemyNameLabel.setLayoutY(290);

		enemyHPLabel = new Label("HP: " + "0" + "/" + "0");
		enemyHPLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
		enemyHPLabel.setLayoutX(660);
		enemyHPLabel.setLayoutY(320);

		enemyStatusLabel = new Label("Effects: None");
		enemyStatusLabel.setLayoutX(660);
		enemyStatusLabel.setLayoutY(340);

		enemyHealth = new ProgressBar(1.0);// เริ่มต้นที่เต็มหลอด
		enemyHealth.setStyle("-fx-accent: #D22B2B;");
		enemyHealth.setPrefHeight(30);
		enemyHealth.setPrefWidth(250);
		enemyHealth.setLayoutX(650);
		enemyHealth.setLayoutY(360);

		// Player Zone
		playerImage = new ImageView(new Image(ClassLoader.getSystemResource("images/Player/Player.png").toString()));
		playerImage.setFitWidth(250);
		playerImage.setFitHeight(250);
		playerImage.setLayoutX(80);
		playerImage.setLayoutY(150);

		Rectangle framePlayer = new Rectangle();
		framePlayer.setX(70); // ปรับตำแหน่งให้ใหญ่กว่า Label เล็กน้อย
		framePlayer.setY(20);
		framePlayer.setWidth(250); // ปรับความกว้างให้ครอบคลุมทั้งหมด
		framePlayer.setHeight(100); // ปรับความสูงให้ครอบคลุมทั้งหมด
		framePlayer.setFill(Color.WHITE.deriveColor(0, 1, 1, 0.5));
		framePlayer.setStroke(null);
		framePlayer.setArcWidth(20); // ความโค้งในแนวนอน
		framePlayer.setArcHeight(20); // ความโค้งในแนวตั้ง

		Label playerName = new Label("Player");
		playerName.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
		playerName.setLayoutX(80);
		playerName.setLayoutY(30);

		playerHPLabel = new Label("HP: " + player.getHp() + "/" + player.getMaxHp());
		playerHPLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
		playerHPLabel.setLayoutX(80);
		playerHPLabel.setLayoutY(60);

		playerMPLabel = new Label("MP: " + player.getMp() + "/" + player.getMaxMp());
		playerMPLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
		playerMPLabel.setLayoutX(190);
		playerMPLabel.setLayoutY(60);

		playerStatusLabel = new Label("Effects: None");
		playerStatusLabel.setLayoutX(80);
		playerStatusLabel.setLayoutY(80);

		playerHealth = new ProgressBar(1.0);// เริ่มต้นที่เต็มหลอด
		playerHealth.setStyle("-fx-accent: #D22B2B;");
		playerHealth.setPrefHeight(30);
		playerHealth.setPrefWidth(250);
		playerHealth.setLayoutX(70);
		playerHealth.setLayoutY(100);

		playerMana = new ProgressBar(0.0);
		playerMana.setPrefHeight(15);
		playerMana.setPrefWidth(250);
		playerMana.setLayoutX(70);
		playerMana.setLayoutY(130);

		topPane.getChildren().addAll(framePlayer, playerImage, playerStatusLabel, playerName, playerHPLabel,
				playerMPLabel, playerHealth, playerMana, frameEnemy, enemyImage, enemyStatusLabel, enemyNameLabel,
				enemyHPLabel, enemyHealth);

		root.setTop(topPane);

		VBox bottomPane = new VBox();

		mainButtonBox = new HBox();
		mainButtonBox.setSpacing(10);
		mainButtonBox.setPrefHeight(200);

		ImageView barImage = new ImageView(new Image(ClassLoader.getSystemResource("images/Scene/Bar.png").toString()));
		barImage.setFitWidth(960);
		barImage.setFitHeight(200);
		BackgroundImage backgroundImage = new BackgroundImage(barImage.getImage(), // ใช้รูปภาพจาก ImageView
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(960, 200, false, false, false, false));

		bottomPane.setBackground(new Background(backgroundImage));
		bottomPane.setPrefSize(960, 200); // ตั้งขนาดของ VBox

		Button skillButton = createStyledButton("Use Skill", e -> {
			mainButtonBox.setVisible(false);
			mainButtonBox.setManaged(false);

			skillButtonBox.getChildren().clear();

			GridPane gridPane = new GridPane();
			gridPane.setHgap(10);
			gridPane.setVgap(5);
			gridPane.setAlignment(Pos.CENTER);

			int column = 0, row = 0;

			for (Skill skill : playerSkills) {
				Button skillButtonInner = createSkillButton(skill);
				gridPane.add(skillButtonInner, column, row);
				column++;
				if (column == 3) { // เมื่อครบ 3 คอลัมน์ ให้ขึ้นแถวใหม่
					column = 0;
					row++;
				}
			}

			Button backButton = new Button("Back");
			backButton.setPrefWidth(930);
			backButton.setPrefHeight(50);
			backButton.setStyle("""
					-fx-background-color: #e74c3c;
					-fx-text-fill: white;
					-fx-font-size: 16px;
					-fx-background-radius: 5;
					""");

			backButton.setOnMouseEntered(ev -> backButton.setStyle("""
					-fx-background-color: #c0392b;
					-fx-text-fill: white;
					-fx-font-size: 16px;
					-fx-background-radius: 5;
					"""));

			backButton.setOnMouseExited(ev -> backButton.setStyle("""
					-fx-background-color: #e74c3c;
					-fx-text-fill: white;
					-fx-font-size: 16px;
					-fx-background-radius: 5;
					"""));

			backButton.setOnAction(ev -> {
				mainButtonBox.setVisible(true);
				mainButtonBox.setManaged(true);
				skillButtonBox.setVisible(false);
				skillButtonBox.setManaged(false);
			});

			gridPane.add(backButton, 0, row + 1, 3, 1); // แถวสุดท้ายรวม 3 คอลัมน์

			skillButtonBox.getChildren().add(gridPane);
			skillButtonBox.setVisible(true);
			skillButtonBox.setManaged(true);
		});

		Button itemButton = createStyledButton("Use Item", e -> useItem());

		mainButtonBox.getChildren().addAll(skillButton, itemButton);
		mainButtonBox.setAlignment(Pos.CENTER);

		skillButtonBox = new VBox(10);
		skillButtonBox.setAlignment(Pos.CENTER);
		skillButtonBox.setPadding(new Insets(20));
		skillButtonBox.setVisible(false);
		skillButtonBox.setManaged(false);

		bottomPane.getChildren().addAll(mainButtonBox, skillButtonBox);

		root.setBottom(bottomPane);

		this.scene = new Scene(root, 960, 600);
	}

	private Button createStyledButton(String text, EventHandler<ActionEvent> handler) {
		Button button = new Button(text);
		button.setPrefWidth(200);
		button.setPrefHeight(50);
		button.setStyle("-fx-font-size: 18px; " + "-fx-background-color: #34495e; " + "-fx-text-fill: white; "
				+ "-fx-padding: 10px; " + "-fx-border-color: #455a64; " + "-fx-border-width: 2px;");

		button.setOnMouseEntered(e -> button.setStyle(button.getStyle() + "-fx-background-color: #455a64;"));

		button.setOnMouseExited(e -> button.setStyle(button.getStyle() + "-fx-background-color: #34495e;"));

		button.setOnAction(handler);
		return button;
	}

	private void showMainButtons() {
		skillButtonBox.setVisible(false);
		skillButtonBox.setManaged(false);

		PauseTransition delay = new PauseTransition(Duration.seconds(2)); // ใส่เวลา delay ที่ต้องการ
		mainButtonBox.setVisible(true);
		skillButtonBox.setManaged(true);
		mainButtonBox.setDisable(true);

		delay.setOnFinished(event -> {
			mainButtonBox.setDisable(false);
		});

		delay.play();

	}

	private void initializeThemes() {
		Collections.addAll(themeList, Theme.values());
		Collections.shuffle(themeList);
		currentTheme = themeList.remove(0);
	}

	private void initializeMonstersAndBosses() {
		monsterList.clear();
		bossList.clear();
		System.out.println("Initializing monsters and bosses for theme: " + currentTheme);

		switch (currentTheme) {
		case FROST:
			System.out.println("=== Frost Theme ===");
			ThemeGame.setImage(new Image(ClassLoader.getSystemResource("images/ThemeGame/Theme_Ice.png").toString()));
			bossList.add(new FrostfangQueen()); // Boss with ice-based abilities
			monsterList.add(new SnowGoblin()); // Basic ice monster
			monsterList.add(new FrostWisp()); // Magical ice creature
			monsterList.add(new IceDrake()); // Dragon-type ice monster
			monsterList.add(new PolarYeti()); // Strong ice beast
			break;

		case LAVA:
			System.out.println("=== Lava Theme ===");
			ThemeGame.setImage(new Image(ClassLoader.getSystemResource("images/ThemeGame/Theme_Lava.png").toString()));
			bossList.add(new VolcanoBoss()); // Boss with fire-based abilities
			monsterList.add(new FlameImp()); // Quick fire attacker
			monsterList.add(new LavaSlime()); // Basic lava monster
			monsterList.add(new MagmaWolf()); // Aggressive fire monster
			monsterList.add(new IgneousGolem()); // Tanky fire monster
			break;

		case STEAMPUNK:
			System.out.println("=== Steampunk Theme ===");
			ThemeGame.setImage(new Image(ClassLoader.getSystemResource("images/ThemeGame/Theme_Time.png").toString()));
			bossList.add(new ClockworkRequiem()); // Mechanical boss
			monsterList.add(new RustyAutomaton()); // Basic mechanical monster
			monsterList.add(new SteamSpider()); // Quick mechanical monster
			monsterList.add(new MechanicalHound()); // Aggressive mechanical monster
			monsterList.add(new BatteryMantis()); // Electric-based monster
			break;
		}

		System.out.println("\nMonsters in " + currentTheme + " theme:");
		for (Monster monster : monsterList) {
			System.out.println("- " + monster.getName());
		}
		System.out.println("\nBoss in " + currentTheme + " theme:");
		for (BaseBoss boss : bossList) {
			System.out.println("- " + boss.getName());
		}
	}

	private void fight() {
		if (isFightingBoss && !bossList.isEmpty()) {
			currentEnemy = bossList.get(0);
			System.out.println("Fighting boss: " + currentEnemy.getName());
		} else if (defeatedMonsters.size() >= monsterList.size() && !bossList.isEmpty() && !isFightingBoss) {
			currentEnemy = bossList.get(0);
			isFightingBoss = true;
			System.out.println("Starting boss fight: " + currentEnemy.getName());
		} else if ((defeatedMonsters.size() >= monsterList.size() && bossList.isEmpty())
				|| (isFightingBoss && bossList.isEmpty())) {
			isFightingBoss = false;

			if (!themeList.isEmpty()) {
				currentTheme = themeList.remove(0);
				defeatedMonsters.clear();
				System.out.println("\nChanging to new theme: " + currentTheme);
				initializeMonstersAndBosses();
				fight();
				return;
			} else {
				showGameCompleteAlert();
				return;
			}
		} else {
			List<Monster> availableMonsters = monsterList.stream().filter(m -> !defeatedMonsters.contains(m))
					.collect(Collectors.toList());

			if (!availableMonsters.isEmpty()) {
				currentEnemy = availableMonsters.get(randomGenerator.nextInt(availableMonsters.size()));
				System.out.println("Fighting monster: " + currentEnemy.getName());
			} else {
				System.out.println("No available monsters left! Moving to boss fight.");
				fight();
				return;
			}
		}

		resetEnemyStats();
		updateUI();
		determineFirstAttacker();
	}

	private void enemyAttack() {
		System.out.println("\nEnemy's turn!");

		TranslateTransition move = new TranslateTransition(Duration.seconds(0.3), enemyImage);
		move.setToX(150 - 650);
		move.setToY(100 - 20);

		SoundManager.playEnemyAttackSound();

		move.setOnFinished(ev -> {
			TranslateTransition back = new TranslateTransition(Duration.seconds(1), enemyImage);
			back.setToX(0);
			back.setToY(0);
			back.play();
		});

		move.play();

		List<Skill> enemySkills = currentEnemy.getSkills();
		if (enemySkills != null && !enemySkills.isEmpty()) {
			Random randomGenerator = new Random();
			int index = randomGenerator.nextInt(enemySkills.size());
			Skill chosenSkill = enemySkills.get(index);

			System.out.println(currentEnemy.getName() + " uses " + chosenSkill.getName() + "!");
			int hpBefore = player.getHp();

			float difficultyModifier = difficultyManager.getDamageMultiplier() * 0.8f; // ลดจาก 0.9f เป็น 0.8f

			int prevHp = player.getHp();
			chosenSkill.use(currentEnemy, player);

			int damage = prevHp - player.getHp();
			int adjustedDamage = (int) (damage * difficultyModifier);
			int newHp = prevHp - adjustedDamage;
			if (newHp > prevHp - damage) { // ถ้าได้รับความเสียหายน้อยลง
				player.setHp(newHp); // แก้ไข HP เป็นค่าที่ปรับแล้ว
			}

			int hpAfter = player.getHp();
			int damageTaken = hpBefore - hpAfter;
			gameStats.addDamageTaken(damageTaken);

			playerHPLabel.setText("Player HP: " + player.getHp());
		} else {
			if (doesAttackHit(currentEnemy, player)) {
				// ใช้ตัวคูณความยาก (ลดลงจากเดิม)
				float difficultyModifier = difficultyManager.getDamageMultiplier() * 0.8f; // ลดจาก 0.9f เป็น 0.8f
				int damage = Math.max((int) (currentEnemy.getAtk() * difficultyModifier) - player.getDef(), 1);
				player.takeDamage(damage);
				gameStats.addDamageTaken(damage);

				System.out.println(currentEnemy.getName() + " attacks Player causing " + damage + " damage.");
				playerHPLabel.setText("Player HP: " + player.getHp());
			} else {
				System.out.println(currentEnemy.getName() + " missed!");
			}
		}

		if (player.getHp() <= 0) {
			SoundManager.playLoseSound();
			showDefeatAlert();
		} else {
			if (player instanceof Player) {
				Player p = (Player) player;

				for (BaseDotEffect effect : p.getActiveEffects()) {
					effect.tickEffect(p);
				}

				p.getActiveEffects().removeIf(effect -> effect.getDuration() <= 0);
				p.restoreMp(10);
				reduceCooldowns();
			}

			for (BaseDotEffect effect : currentEnemy.getActiveEffects()) {
				effect.tickEffect(currentEnemy);
			}
			currentEnemy.getActiveEffects().removeIf(effect -> effect.getDuration() <= 0);

			updateUI();
			System.out.println("Enemy turn complete. Now player's turn.");
		}
	}

	private void useSkill(String skillName) {
		if (player instanceof Player) {
			Player playerChar = (Player) player;
			List<Skill> playerSkills = playerChar.getSkills();

			Skill selectedSkill = playerSkills.stream().filter(skill -> skill.getName().equals(skillName)).findFirst()
					.orElse(null);

			if (selectedSkill != null && playerChar.getMp() >= selectedSkill.getManaCost()) {
				if (skillCooldowns.getOrDefault(skillName, 0) > 0) {
					System.out.println("Skill is on cooldown: " + skillCooldowns.get(skillName) + " turns remaining!");
					return;
				}

				System.out.println("Using skill: " + selectedSkill.getName());

				playerChar.useMp(selectedSkill.getManaCost());
				skillCooldowns.put(skillName, selectedSkill.getCooldown());

				int enemyHpBefore = currentEnemy.getHp();
				selectedSkill.use(player, currentEnemy);
				int enemyHpAfter = currentEnemy.getHp();

				enemyHP = enemyHpAfter;
				enemyHPLabel.setText("HP: " + enemyHP);
				playerMPLabel.setText("MP: " + playerChar.getMp() + "/" + playerChar.getMaxMp());

				int damage = enemyHpBefore - enemyHpAfter;
				gameStats.addDamageDealt(damage);
				gameStats.incrementSkillsUsed();

				playerChar.restoreMp(10); // เปลี่ยนจาก 20 เป็น 10

				updateUI();

				if (enemyHP <= 0) {
					System.out.println("Enemy defeated!");
                    if (isFightingBoss && currentEnemy instanceof BaseBoss) {
                        showVictoryAlert(currentEnemy);  // ไม่ต้อง cast เป็น Monster อีกต่อไป
                        return;
                    } else if (currentEnemy instanceof Monster) {
                        showVictoryAlert(currentEnemy);  // ไม่ต้อง cast เป็น Monster อีกต่อไป
                        return;
                    }
				} else {
					// ให้เอฟเฟคทำงานก่อนจบเทิร์น
					for (BaseDotEffect effect : currentEnemy.getActiveEffects()) {
						effect.tickEffect(currentEnemy);
					}
					currentEnemy.getActiveEffects().removeIf(effect -> effect.getDuration() <= 0);

					PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
					delay.setOnFinished(event -> {
						enemyAttack();
					});
					delay.play();
				}
			} else {
				System.out.println("Not enough MP to use skill!");
			}
		}
	}

	private void useItem() {
        if (!(player instanceof Player)) return;
        Player playerChar = (Player) player;
        
        List<Item> inventory = playerChar.getInventory();
        if (inventory.isEmpty()) {
            Alert noItemsAlert = new Alert(Alert.AlertType.INFORMATION);
            noItemsAlert.setTitle("No Items");
            noItemsAlert.setHeaderText("Inventory Empty");
            noItemsAlert.setContentText("You don't have any items to use.");
            noItemsAlert.showAndWait();
            return;
        }
        
        // สร้างไดอะล็อกเพื่อเลือกไอเทม
        ChoiceDialog<String> dialog = new ChoiceDialog<>(
            inventory.get(0).getName(), 
            inventory.stream().map(Item::getName).collect(Collectors.toList())
        );
        dialog.setTitle("Use Item");
        dialog.setHeaderText("Select an item to use:");
        dialog.setContentText("Item:");
        
        // จัดการผลลัพธ์เมื่อผู้ใช้เลือกไอเทม
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(itemName -> {
            // หาไอเทมที่มีชื่อตรงกัน
            Item selectedItem = inventory.stream()
                    .filter(item -> item.getName().equals(itemName))
                    .findFirst()
                    .orElse(null);
                    
            if (selectedItem != null) {
                playerChar.useItem(selectedItem);
                updateUI(); // อัพเดต UI หลังจากใช้ไอเทม
                System.out.println("Used " + selectedItem.getName());
            }
        });
    }

	private boolean doesAttackHit(Character attacker, Character defender) {
		if (defender instanceof Player) {
			// เพิ่มโอกาสพลาดโจมตีผู้เล่นมากขึ้น
			return Math.random() * 100 > defender.getSpd() * 0.3; // เพิ่มจาก 0.25 เป็น 0.3
		}
		// โอกาสผู้เล่นโจมตีพลาดคงเดิม
		return Math.random() * 100 > defender.getSpd() * 0.2;
	}

	public void startUserTurn() {
		System.out.println("User's turn starts again!");
	}

	public static void grantUserExtraTurn() {
		System.out.println("An extra turn has been granted to the user!");
		if (currentGame != null) {
			currentGame.startUserTurn();
		}
	}

	private void showVictoryAlert(Character defeatedEnemy) {
		int baseXP = calculateBaseXP(defeatedEnemy);
		int bonusXP = isFightingBoss ? baseXP * 2 : 0;
		int totalXP = baseXP + bonusXP;

		Alert victoryAlert = new Alert(Alert.AlertType.INFORMATION);
		victoryAlert.setTitle("Victory!");

		if (isFightingBoss) {

			bossList.remove(0);
			isFightingBoss = false;

			victoryAlert.setHeaderText("Boss Defeated: " + defeatedEnemy.getName());
			victoryAlert.setContentText(String.format(
					"Congratulations!\nBase XP: %d\nBoss Bonus: %d\nTotal XP gained: %d", baseXP, bonusXP, totalXP));
			gameStats.incrementBossesDefeated();

			if (player instanceof Player) {
				((Player) player).addXP(totalXP);
				gameStats.addXP(totalXP); // เพิ่ม XP ลงในสถิติ
				System.out.println("Player gained " + totalXP + " XP");
			}

			victoryAlert.showAndWait();

			if (!themeList.isEmpty()) {
				currentTheme = themeList.remove(0);
				defeatedMonsters.clear();
				System.out.println("\nChanging to new theme: " + currentTheme);
				initializeMonstersAndBosses();
			} else {
				showGameCompleteAlert();
				return;
			}

			fight();
		} else {
			if (defeatedEnemy instanceof Monster) {
				defeatedMonsters.add((Monster) defeatedEnemy);
			}
			victoryAlert.setHeaderText("Defeated: " + defeatedEnemy.getName());
			victoryAlert.setContentText(String.format("Experience gained: %d", totalXP));
			gameStats.incrementMonstersDefeated();

			if (player instanceof Player) {
				((Player) player).addXP(totalXP);
				gameStats.addXP(totalXP);
				System.out.println("Player gained " + totalXP + " XP");
			}

			victoryAlert.showAndWait();
			fight();
		}
	}

	private int calculateBaseXP(Character enemy) {
		int baseXP = (enemy.getMaxHp() / 10) + (enemy.getAtk() * 2) + (enemy.getDef() * 2) + (enemy.getSpd());

		switch (gameDifficulty) {
		case EASY:
			baseXP = (int) (baseXP * 0.8);
			break;
		case HARD:
			baseXP = (int) (baseXP * 1.2);
			break;
		case NIGHTMARE:
			baseXP = (int) (baseXP * 2);
			break;
		default: // NORMAL
			break;
		}

		return baseXP;
	}

	private void showDefeatAlert() {
        Alert defeatAlert = new Alert(Alert.AlertType.INFORMATION);
        defeatAlert.setTitle("Defeat!");
        defeatAlert.setHeaderText("You have been defeated!");
        defeatAlert.setContentText("Better luck next time!");
        
        defeatAlert.setOnShown(e -> {
            Platform.runLater(this::showGameCompleteAlert);
        });
        
        defeatAlert.show();
    }

	public List<Skill> getSkills() {
		return player.getSkills();
	}

	private void showGameCompleteAlert() {
		UI_GameSummary summary = new UI_GameSummary(gameStage, (Player) player, gameStats);
		gameStage.setScene(summary.getScene()); // เปลี่ยนเป็น getCustomScene()
	}

	private void determineFirstAttacker() {
		if (player.getSpd() > currentEnemy.getSpd()) {
			System.out.println("Player is faster! Player goes first.");
		} else if (player.getSpd() < currentEnemy.getSpd()) {
			System.out.println("Enemy is faster! Enemy goes first.");
			enemyAttack();
		} else {
			if (randomGenerator.nextBoolean()) {
				System.out.println("Equal speed! Random decided player goes first.");
			} else {
				System.out.println("Equal speed! Random decided enemy goes first.");
				enemyAttack();
			}
		}
	}

	private void resetEnemyStats() {
		if (currentEnemy == null)
			return;

		if (isFightingBoss && currentEnemy instanceof BaseBoss) {
			if (!bossList.isEmpty() && bossList.get(0).getName().equals(currentEnemy.getName())) {
				currentEnemy = new BaseBoss(currentEnemy.getName(), currentEnemy.getMaxHp(), currentEnemy.getAtk(),
						currentEnemy.getDef(), currentEnemy.getSpd(), currentEnemy.getSkills(),
						((BaseBoss) currentEnemy).getFieldEffect());
			}
		} else if (currentEnemy instanceof Monster && !defeatedMonsters.contains((Monster) currentEnemy)) {
			currentEnemy = new Monster(currentEnemy.getName(), currentEnemy.getMaxHp(), currentEnemy.getAtk(),
					currentEnemy.getDef(), currentEnemy.getSpd(), currentEnemy.getSkills());
		}
		enemyHP = currentEnemy.getHp();
	}

	private void updateUI() {
		enemyImage.setImage(ImageEnemy.getEnemyImage(currentEnemy.getName()));
		enemyNameLabel.setText(currentEnemy.getName());
		enemyHPLabel.setText(String.format("HP: %d/%d", enemyHP, currentEnemy.getMaxHp()));
		playerHPLabel.setText(String.format("HP: %d/%d", player.getHp(), player.getMaxHp()));

		Platform.runLater(() -> playerHealth.setProgress((double) player.getHp() / player.getMaxHp()));
		Platform.runLater(() -> enemyHealth.setProgress((double) currentEnemy.getHp() / currentEnemy.getMaxHp()));

		if (player instanceof Player) {
			Player p = (Player) player;
			playerMPLabel.setText(String.format("MP: %d/%d", p.getMp(), p.getMaxMp()));
			Platform.runLater(() -> playerMana.setProgress((double) player.getMp() / player.getMaxMp()));

			List<String> playerEffects = p.getActiveEffects().stream()
					.map(effect -> String.format("%s (%d turns)", effect.getName(), effect.getDuration()))
					.collect(Collectors.toList());

			if (playerEffects.isEmpty()) {
				playerStatusLabel.setText("Status Effects: None");
				playerStatusLabel.setStyle("-fx-text-fill: #22771a;");
			} else {
				playerStatusLabel.setText("Status Effects: " + String.join(", ", playerEffects));
				playerStatusLabel.setStyle("-fx-text-fill: #ad1c0b;");
			}
		}

		List<String> enemyEffects = currentEnemy.getActiveEffects().stream()
				.map(effect -> String.format("%s (%d turns)", effect.getName(), effect.getDuration()))
				.collect(Collectors.toList());

		if (enemyEffects.isEmpty()) {
			enemyStatusLabel.setText("Status Effects: None");
			enemyStatusLabel.setStyle("-fx-text-fill: #22771a;");
		} else {
			enemyStatusLabel.setText("Status Effects: " + String.join(", ", enemyEffects));
			enemyStatusLabel.setStyle("-fx-text-fill: #ad1c0b;");
		}

		if (isFightingBoss) {
			System.out.println("Fighting boss: " + currentEnemy.getName());
		} else {
			System.out.println("Fighting monster: " + currentEnemy.getName());
		}
	}

	private Button createSkillButton(Skill skill) {
		Button skillButton = new Button(skill.getName());
		skillButton.setPrefWidth(300);
		skillButton.setPrefHeight(50);

		String tooltipText = String.format("%s\nMP: %d | Cooldown: %d\n%s", skill.getName(), skill.getManaCost(),
				skill.getCooldown(), skill.getDescription());

		Tooltip tooltip = new Tooltip(tooltipText);
		tooltip.setStyle("""
				-fx-font-size: 14px;
				-fx-background-color: #2c3e50;
				-fx-text-fill: white;
				-fx-padding: 5px;
				""");

		tooltip.setShowDelay(Duration.millis(50));
		tooltip.setHideDelay(Duration.millis(100));
		tooltip.setShowDuration(Duration.INDEFINITE);

		skillButton.setTooltip(tooltip);

		int currentCooldown = skillCooldowns.getOrDefault(skill.getName(), 0);
		if (currentCooldown > 0) {
			skillButton.setStyle("""
					-fx-background-color: #7f8c8d;
					-fx-text-fill: white;
					-fx-font-size: 16px;
					""");
			skillButton.setDisable(true);
			skillButton.setText(String.format("%s (CD: %d)", skill.getName(), currentCooldown));
		} else if (skill.getManaCost() > ((Player) player).getMp()) {
			skillButton.setStyle("""
					-fx-background-color: #95a5a6;
					-fx-text-fill: white;
					-fx-font-size: 16px;
					""");
			skillButton.setDisable(true);
		} else {
			skillButton.setStyle("""
					-fx-background-color: #3498db;
					-fx-text-fill: white;
					-fx-font-size: 16px;
					""");
		}

		skillButton.setOnAction(e -> {

			TranslateTransition move = new TranslateTransition(Duration.seconds(0.3), playerImage);
			move.setToX(450 - 80);
			move.setToY(30 - 150);

			SoundManager.playPlayerAttackSound();

			move.setOnFinished(ev -> {
				TranslateTransition back = new TranslateTransition(Duration.seconds(1), playerImage);
				back.setToX(0);
				back.setToY(0);
				back.play();
			});

			move.play();

			useSkill(skill.getName());
			showMainButtons();

		});

		return skillButton;
	}

	private void reduceCooldowns() {
		Map<String, Integer> updatedCooldowns = new HashMap<>();
		for (Map.Entry<String, Integer> entry : skillCooldowns.entrySet()) {
			int remainingCooldown = entry.getValue() - 1;
			if (remainingCooldown > 0) {
				updatedCooldowns.put(entry.getKey(), remainingCooldown);
			}
		}
		skillCooldowns = updatedCooldowns;
	}
}