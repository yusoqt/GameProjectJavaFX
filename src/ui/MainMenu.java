package ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import soundmanager.SoundManager;

public class MainMenu {
	private Scene scene;
	private Stage primaryStage;
	private ImageView backgroundGame;
	private Image defaultBackgroundImage;

	public MainMenu(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
	}

	private void initialize() {

		defaultBackgroundImage = new Image(ClassLoader.getSystemResource("images/MainMenu.png").toString());
		
		backgroundGame = new ImageView(defaultBackgroundImage);
		backgroundGame.setFitWidth(960);
		backgroundGame.setFitHeight(600);

		Button startButton = createGameButton("Start");
		Button storyButton = createGameButton("Story");
		Button exitButton = createGameButton("Exit");

		startButton.addEventHandler(ActionEvent.ACTION, new StartButtonHandler());
		storyButton.addEventHandler(ActionEvent.ACTION, new StoryButtonHandler());

		exitButton.addEventHandler(ActionEvent.ACTION, e -> {
			System.out.println("Thank you for play this game, Good Luck!!");
			new Thread(() -> {
				try {
					SoundManager.playClickSound();
					Thread.sleep(500);
				} catch (InterruptedException error) {
					error.printStackTrace();
				}

				Platform.runLater(() -> {
					Platform.exit();
					System.exit(0);
				});
			}).start();
		});

		VBox vbox = new VBox(20, startButton, storyButton, exitButton);
		vbox.setStyle("-fx-alignment: center;");
		vbox.setTranslateX(-380);
		vbox.setTranslateY(120);

		StackPane root = new StackPane(backgroundGame, vbox);
		this.scene = new Scene(root, 960, 600);
	}

	public Scene getScene() {
		return scene;
	}

	private Button createGameButton(String text) {
		Button button = new Button(text);
		button.setMinSize(300, 25);

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

	private class StartButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Platform.runLater(() -> {
				UI_DifficultySelection difficultySelection = new UI_DifficultySelection(primaryStage);
				primaryStage.setScene(difficultySelection.getScene());
			});
		}
	}

	private class StoryButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			System.out.println("Entered Story Page");
			Platform.runLater(() -> {
				UI_StoryDetails storyDetails = new UI_StoryDetails(primaryStage);
				storyDetails.showStory();
			});
		}
	}

}
