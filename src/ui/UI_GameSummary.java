package ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import player.Player;
import soundmanager.SoundManager;
import game.GameStats;

public class UI_GameSummary extends StackPane {
	private Stage stage;
	private Player player;
	private GameStats stats;
	private Scene scene;
	private ImageView backgroundImage;
	
	public UI_GameSummary(Stage stage, Player player, GameStats stats) {
		this.stage = stage;
		this.player = player;
		this.stats = stats;
		initialize();
	}

	private void initialize() {
		backgroundImage = new ImageView();
	    backgroundImage.setFitWidth(960);
	    backgroundImage.setFitHeight(600);

	    if (player.getHp() == 0) 
		    SceneLose();
	    else 
	    	SceneWin(); 
	    
	    VBox allSummarize = new VBox();
	    allSummarize.setSpacing(18);
	    
	    // หัวข้อ
	    Label titleLabel = createStyledLabel("Game Summarize", 40);
	    titleLabel.setStyle(titleLabel.getStyle() + "-fx-text-fill: gold;");


	    // สถานะตัวละคร
	    Label playerStats = createStyledLabel(String.format("""
	            Final Character Status:
	            Level: %d
	            HP: %d/%d
	            ATK: %d
	            DEF: %d
	            SPD: %d
	            """, player.getLevel(), player.getHp(), player.getMaxHp(), player.getAtk(), player.getDef(),
	            player.getSpd()), 16);

	    // สถิติการเล่น
	    Label gameStats = createStyledLabel(String.format("""
	            Battle Statistics:
	            Monsters Defeated: %d
	            Bosses Defeated: %d
	            Total Experience Gained: %d
	            Total Damage Dealt: %d
	            Total Damage Taken: %d
	            Skills Used: %d
	            """, stats.getMonstersDefeated(), stats.getBossesDefeated(), stats.getTotalXP(),
	            stats.getTotalDamageDealt(), stats.getTotalDamageTaken(), stats.getSkillsUsed()), 16);

	    // ปุ่มกลับเมนูหลัก
	    Button mainMenuButton = createGameButton("Return to Main Menu");

	    mainMenuButton.setPrefWidth(380);
	    mainMenuButton.setOnAction(e -> backToMainMenu());

	    allSummarize.getChildren().addAll(titleLabel, playerStats, gameStats, mainMenuButton);
	    allSummarize.setPadding(new Insets(50, 50, 50, 120));
	    
	    this.getChildren().addAll(backgroundImage, allSummarize);
	    
	    this.scene = new Scene(this, 960, 600);
	}


	private Label createStyledLabel(String text, int fontSize) {
		Label label = new Label(text);
		label.setFont(Font.font("System", FontWeight.BOLD, fontSize));
		label.setStyle("-fx-text-fill: white;");
		return label;
	}
	
	private Button createGameButton(String text) {
		Button button = new Button(text);

		String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #101820, #1A1F2B);"
				+ "-fx-text-fill: #FFD700;" + "-fx-font-size: 24px;" + "-fx-font-weight: bold;"
				+ "-fx-border-radius: 25px;" + "-fx-background-radius: 25px;" + "-fx-border-color: #00FFFF;"
				+ "-fx-border-width: 3px;" + "-fx-padding: 12px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 255, 255, 0.6), 10, 0, 0, 0);";

		String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #002244, #003366);"
				+ "-fx-text-fill: #FFD700;" + "-fx-font-size: 28px;" + "-fx-font-weight: bold;"
				+ "-fx-border-color: #FFD700;" + "-fx-text-fill: #FFFFFF;" + "-fx-border-radius: 25px;"
				+ "-fx-background-radius: 25px;" + "-fx-padding: 12px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(255, 215, 0, 0.8), 15, 0, 0, 0);";

		button.setStyle(buttonStyle);
		button.addEventHandler(ActionEvent.ACTION, e -> {
			new Thread(() -> {
				SoundManager.playClickSound();
				Platform.runLater(() -> button.setDisable(false));
			}).start();
		});

		button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
		button.setOnMouseExited(e -> button.setStyle(buttonStyle));

		return button;
	}

	private void backToMainMenu() {
		MainMenu mainMenu = new MainMenu(stage);
		stage.setScene(mainMenu.getScene());
	}

	public void SceneWin() {
		backgroundImage.setImage((new Image(ClassLoader.getSystemResource("images/win.png").toString())));
	}
	
	public void SceneLose() {
		backgroundImage.setImage((new Image(ClassLoader.getSystemResource("images/lose.png").toString())));
	}
	
}