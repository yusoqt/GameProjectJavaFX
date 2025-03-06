package managers;

import javafx.stage.Stage;
import javafx.scene.Scene;  // เพิ่ม import Scene
import player.Player;       // เพิ่ม import Player
import ui.*;

public class UIManager {
    private Stage stage;

    public UIManager(Stage stage) {
        this.stage = stage;
    }

    public void showDifficultySelection() {
        new UI_DifficultySelection(stage);
    }

    public void showInventoryScreen(Player player, Scene previousScene) {
        new UI_Inventory(stage, player, previousScene);
    }
}