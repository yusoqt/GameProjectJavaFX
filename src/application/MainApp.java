package application;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.MainMenu;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu(primaryStage); // ส่ง primaryStage เข้าไป

        primaryStage.setTitle("Song of Twelve Feathers");
        primaryStage.setScene(mainMenu.getScene());
        primaryStage.setResizable(false); // ปิดการยืดหดหน้าต่าง
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
