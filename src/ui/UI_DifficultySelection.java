package ui;

import game.Difficulty;
import game.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import soundmanager.SoundManager;

public class UI_DifficultySelection {

	private Scene scene;

	public UI_DifficultySelection(Stage stage) {
		System.out.println("Choose Difficult");
		Image gameBackgroundImage = new Image(ClassLoader.getSystemResource("images/StageSelector.png").toString());
		ImageView backgroundGame = new ImageView(gameBackgroundImage);
		backgroundGame.setFitWidth(960);
		backgroundGame.setFitHeight(600);

		Button easyButton = createButton("Easy");
		Button mediumButton = createButton("Medium");
		Button hardButton = createButton("Hard");
		Button nightmareButton = createButton("Nightmare");
		Button backButton = createButton("Go Back");

		easyButton.setOnAction(e -> {
			System.out.println("Choose Easy Mode!");
			startGame(stage, Difficulty.EASY);
		});
		mediumButton.setOnAction(e -> {
			System.out.println("Choose Medium Mode!");
			startGame(stage, Difficulty.MEDIUM);
		});
		hardButton.setOnAction(e -> {
			System.out.println("Choose Hard Mode!");
			startGame(stage, Difficulty.HARD);
		});
		nightmareButton.setOnAction(e -> {
			System.out.println("Choose Nightmare Mode!");
			startGame(stage, Difficulty.NIGHTMARE);
		});

		backButton.setOnAction(e -> {
			System.out.println("Back to MainMenu");
			backToMainMenu(stage);
		});

		Label stageLabel = new Label("Choose Difficulty");
		stageLabel.setStyle(
				"-fx-font-size: 90px;" + "-fx-text-fill: linear-gradient(from 0% 0% to 100% 100%, #ffcc00, #ff6600);"
						+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 4, 0.5, 0.0, 2.0);"
						+ "-fx-font-weight: bold;" + "-fx-background-color: transparent;");

		VBox vbox = new VBox(20, stageLabel, easyButton, mediumButton, hardButton, nightmareButton, backButton);
		vbox.setStyle("-fx-alignment: center;");
		vbox.setSpacing(15);

		StackPane root = new StackPane();
		root.getChildren().addAll(backgroundGame, vbox);
		this.scene = new Scene(root, 960, 600);
	}

	public Scene getScene() {
		return scene;
	}

	private Button createButton(String text) {
		Button button = new Button(text);
		button.setMinSize(300, 25);

		String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #101820, #1A1F2B);"
				+ "-fx-text-fill: #FFD700;" + "-fx-font-size: 18px;" + "-fx-font-weight: bold;"
				+ "-fx-border-radius: 25px;" + "-fx-background-radius: 25px;" + "-fx-border-color: #00FFFF;"
				+ "-fx-border-width: 3px;" + "-fx-padding: 6px;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 255, 255, 0.6), 10, 0, 0, 0);";

		String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #002244, #003366);"
				+ "-fx-text-fill: #FFD700;" + "-fx-font-size: 22px;" + "-fx-font-weight: bold;"
				+ "-fx-border-color: #FFD700;" + "-fx-text-fill: #FFFFFF;" + "-fx-border-radius: 25px;"
				+ "-fx-background-radius: 25px;" + "-fx-padding: 6px;"
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

	private void backToMainMenu(Stage stage) {
		MainMenu mainMenu = new MainMenu(stage);
		stage.setScene(mainMenu.getScene());
	}

	private void startGame(Stage stage, Difficulty difficulty) {
		Game game = new Game(stage, difficulty);
		stage.setScene(game.getScene());
	}

}
