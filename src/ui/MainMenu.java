package ui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import soundmanager.SoundManager;

public class MainMenu {
	private Scene scene;
	private Stage primaryStage;

	public MainMenu(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
	}

	private void initialize() {

		Image backgroundImage = new Image(ClassLoader.getSystemResource("images/main.gif").toString());
		ImageView background = new ImageView(backgroundImage);
		background.setFitWidth(960);
		background.setFitHeight(600);

		Label title = new Label();
		title.setStyle("-fx-font-size: 60px; " + "-fx-font-weight: bold; " + "-fx-text-fill: yellow; "
				+ "-fx-effect: dropshadow(gaussian, orange, 10, 0, 0, 0); ");

		playTypewriterEffect(title, "Song of Twelve Feathers");

		Button startButton = createGameButton("Start");
		Button storyButton = createGameButton("Story");
		Button exitButton = createGameButton("Exit");

		storyButton.addEventHandler(ActionEvent.ACTION, new StoryButtonHandler());

		exitButton.addEventHandler(ActionEvent.ACTION, e -> {
			new Thread(() -> {
				try {
					SoundManager.playClickSound();
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				Platform.runLater(() -> {
					Platform.exit();
					System.exit(0);
				});
			}).start();
		});

		VBox vbox = new VBox(20, title, startButton, storyButton, exitButton);
		vbox.setStyle("-fx-alignment: center;");

		StackPane root = new StackPane(background, vbox);
		this.scene = new Scene(root, 960, 600);
	}

	public Scene getScene() {
		return scene;
	}

	private void playTypewriterEffect(Label label, String text) {
		Timeline timeline = new Timeline();
		for (int i = 0; i < text.length(); i++) {
			final int index = i;
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200 * i), e -> {
				Platform.runLater(() -> label.setText(text.substring(0, index + 1)));
			}));
		}
		timeline.play();
	}

	private Button createGameButton(String text) {
		Button button = new Button(text);
		button.setMinSize(400, 70);

		String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);"
				+ "-fx-text-fill: white;" + "-fx-font-size: 24px;" + "-fx-font-weight: bold;"
				+ "-fx-border-radius: 15px;" + "-fx-background-radius: 15px;" + "-fx-padding: 10px 20px;"
				+ "-fx-border-color: #ff6600;" + "-fx-border-width: 4px;";

		String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #ff9900, #ff6600);"
				+ "-fx-border-color: #ff3300;" + "-fx-border-radius: 30px;" + "-fx-background-radius: 30px;";

		button.setStyle(buttonStyle);
		createSmoothHoverEffect(button, buttonStyle, hoverStyle);

		button.addEventHandler(ActionEvent.ACTION, e -> {
			new Thread(() -> {
				SoundManager.playClickSound();
				Platform.runLater(() -> button.setDisable(false));
			}).start();
		});

		return button;
	}

	private void createSmoothHoverEffect(Button button, String normalStyle, String hoverStyle) {
		ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
		scaleUp.setToX(1.1);
		scaleUp.setToY(1.1);
		scaleUp.setInterpolator(Interpolator.EASE_OUT);

		ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
		scaleDown.setToX(1.0);
		scaleDown.setToY(1.0);
		scaleDown.setInterpolator(Interpolator.EASE_IN);

		button.setOnMouseEntered(e -> {
			button.setStyle(hoverStyle);
			scaleUp.play();
		});

		button.setOnMouseExited(e -> {
			button.setStyle(normalStyle);
			scaleDown.play();
		});
	}

	private class StoryButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Platform.runLater(() -> {
				StoryDetails storyDetails = new StoryDetails(primaryStage);
				storyDetails.showStory();
			});
		}
	}

}
