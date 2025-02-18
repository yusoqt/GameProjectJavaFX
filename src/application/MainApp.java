package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.MainMenu;

public class MainApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		MainMenu mainMenu = new MainMenu(primaryStage);

		primaryStage.setTitle("Song of Twelve Feathers");
		primaryStage.setScene(mainMenu.getScene());
		primaryStage.setResizable(false);
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
