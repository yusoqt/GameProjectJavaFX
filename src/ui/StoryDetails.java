package ui;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class StoryDetails {
	private Stage storyStage;
	private Stage mainStage;

	public StoryDetails(Stage mainStage) {
		this.mainStage = mainStage;
		storyStage = new Stage();
	}

	public void showStory() {
		ImageView storyImage = new ImageView(new Image("images/story.png"));
		storyImage.setFitWidth(960);
		storyImage.setFitHeight(600);
		storyImage.setPreserveRatio(false);

		Button closeButton = new Button("Back to Main Menu");
		closeButton.setStyle("-fx-font-size: 14px; "
				+ "-fx-background-color: linear-gradient(to bottom, #c98b72, #8c5a44); " + "-fx-text-fill: #f4e1d2; "
				+ "-fx-font-weight: bold; " + "-fx-padding: 10px; " + "-fx-border-color: #6b3e2e; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-background-radius: 5px; "
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0, 0, 3); ");

		closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-font-size: 14px; "
				+ "-fx-background-color: linear-gradient(to bottom, #d49a80, #9a614a); " + "-fx-text-fill: #ffffff; "
				+ "-fx-font-weight: bold; " + "-fx-padding: 10px; " + "-fx-border-color: #6b3e2e; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-background-radius: 5px; "
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 12, 0, 0, 5); " + "-fx-scale-x: 1.05; "
				+ "-fx-scale-y: 1.05;"));

		closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-font-size: 14px; "
				+ "-fx-background-color: linear-gradient(to bottom, #c98b72, #8c5a44); " + "-fx-text-fill: #f4e1d2; "
				+ "-fx-font-weight: bold; " + "-fx-padding: 10px; " + "-fx-border-color: #6b3e2e; "
				+ "-fx-border-width: 2px; " + "-fx-border-radius: 5px; " + "-fx-background-radius: 5px; "
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0, 0, 3); " + "-fx-scale-x: 1.0; "
				+ "-fx-scale-y: 1.0;"));
		
		AudioClip clickSound = loadClickSound();

		closeButton.setOnAction(e -> {
			if (clickSound != null) {
				clickSound.play();
			}
			storyStage.close();
			mainStage.show();
		});
		closeButton.setTranslateX(380);
		closeButton.setTranslateY(-270);

		StackPane layout = new StackPane();
		layout.getChildren().addAll(storyImage, closeButton);

		Scene scene = new Scene(layout, 960, 600);
		storyStage.setTitle("Story Details");
		storyStage.setScene(scene);
		storyStage.show();

		mainStage.hide();
	}
	
	private AudioClip loadClickSound() {
		try {
			URL soundURL = getClass().getClassLoader().getResource("sound/click.mp3");
			if (soundURL != null) {
				return new AudioClip(soundURL.toString());
			} else {
				System.out.println("Error: Sound file not found!");
			}
		} catch (Exception e) {
			System.out.println("Error loading sound: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
