package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.Player;
import items.Item;

public class UI_Inventory {
    private Stage stage;
    private Player player;
    private Scene previousScene;

    public UI_Inventory(Stage stage, Player player, Scene previousScene) {
        this.stage = stage;
        this.player = player;
        this.previousScene = previousScene;
        initialize();
    }

    private void initialize() {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: #2c3e50;");

        Label inventoryLabel = new Label("Inventory");
        inventoryLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // สร้างรายการไอเทม
        VBox itemsList = new VBox(10);
        for (Item item : player.getInventory()) {
            HBox itemRow = new HBox(20);
            itemRow.setAlignment(Pos.CENTER_LEFT);
            
            Label nameLabel = new Label(item.getName());
            nameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
            
            Label descLabel = new Label(item.getDescription());
            descLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6;");
            
            Button useButton = new Button("Use");
            useButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            useButton.setOnAction(e -> {
                player.useItem(item);
                initialize(); // รีเฟรชหน้า UI
            });
            
            itemRow.getChildren().addAll(nameLabel, descLabel, useButton);
            itemsList.getChildren().add(itemRow);
        }
        
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 18px;");
        backButton.setOnAction(e -> stage.setScene(previousScene));
        
        ScrollPane scrollPane = new ScrollPane(itemsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        layout.getChildren().addAll(inventoryLabel, scrollPane, backButton);
        
        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Inventory");
    }
}