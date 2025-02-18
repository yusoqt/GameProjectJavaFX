package ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import soundmanager.SoundManager;

public class StoryDetails {
	private Stage storyStage;
	private Stage mainStage;

	public StoryDetails(Stage mainStage) {
		this.mainStage = mainStage;
		storyStage = new Stage();
	}

	public void showStory() {
		new Thread(() -> {
			Image storyImg = new Image("images/story.png");
			ImageView storyImage = new ImageView(storyImg);

			Platform.runLater(() -> {
				storyImage.setFitWidth(960);
				storyImage.setFitHeight(600);
				storyImage.setPreserveRatio(false);

				Button closeButton = createCloseButton();
				StackPane layout = new StackPane(storyImage, closeButton);
				Scene scene = new Scene(layout, 960, 600);

				storyStage.setTitle("Story Details");
				storyStage.setScene(scene);
				storyStage.show();
				mainStage.hide();
			});
		}).start();
	}

	private Button createCloseButton() {
		Button closeButton = new Button("Back to Main Menu");
		closeButton.setStyle(
				"-fx-font-size: 14px; " + "-fx-background-color: linear-gradient(to bottom, #c98b72, #8c5a44); "
						+ "-fx-text-fill: #f4e1d2; -fx-font-weight: bold; -fx-padding: 10px; "
						+ "-fx-border-color: #6b3e2e; -fx-border-width: 2px; "
						+ "-fx-border-radius: 5px; -fx-background-radius: 5px;");

		closeButton.setOnAction(e -> {
			new Thread(() -> {
				SoundManager.playClickSound();

				Platform.runLater(() -> {
					storyStage.close();
					mainStage.show();
				});
			}).start();
		});

		closeButton.setTranslateX(380);
		closeButton.setTranslateY(-270);
		return closeButton;
	}

} 
