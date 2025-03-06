package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import soundmanager.SoundManager;

public class UI_StoryDetails {
private Stage mainStage;
private Scene MainMenuScene;

public UI_StoryDetails(Stage mainStage) {
this.mainStage = mainStage;
this.MainMenuScene = mainStage.getScene(); // เก็บ Scene เดิม
}

public void showStory() {
Image storyImg = new Image("images/Scene/story.png");
ImageView storyImage = new ImageView(storyImg);

storyImage.setFitWidth(960);
storyImage.setFitHeight(600);
storyImage.setPreserveRatio(false);

Button closeButton = createCloseButton();
StackPane layout = new StackPane(storyImage, closeButton);
StackPane.setAlignment(closeButton, javafx.geometry.Pos.TOP_RIGHT);

Scene scene = new Scene(layout, 960, 600);
mainStage.setScene(scene);
}

private Button createCloseButton() {
Button closeButton = new Button("Back to Main Menu");
closeButton.setStyle(
"-fx-font-size: 14px; " + "-fx-background-color: linear-gradient(to bottom, #c98b72, #8c5a44); "
+ "-fx-text-fill: #f4e1d2; " + "-fx-font-weight: bold; " + "-fx-padding: 10px; "
+ "-fx-border-color: #6b3e2e; -fx-border-width: 2px; "
+ "-fx-border-radius: 5px; -fx-background-radius: 5px;");

closeButton.setOnAction(e -> {
System.out.println("Back to MainMenu");
SoundManager.playClickSound();
mainStage.setScene(MainMenuScene);
});
return closeButton;
}
}