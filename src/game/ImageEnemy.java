package game;

import javafx.scene.image.Image;

public class ImageEnemy {
	public static Image getEnemyImage(String name) {
		String fileImage = "images/";
		switch (name) {
		// Ice
		case "Snow Goblin":
			fileImage += "Enemy/Ice/Goblin";
			break;
		case "Frost Wisp":
			fileImage += "Enemy/Ice/Wisp";
			break;
		case "Ice Drake":
			fileImage += "Enemy/Ice/Drake";
			break;
		case "Polar Yeti":
			fileImage += "Enemy/Ice/Yeti";
			break;
		case "Frostfang Queen":
			fileImage += "Enemy/Ice/Queen";
			break;

		// Lava
		case "Flame Imp":
			fileImage += "Enemy/Lava/Imp";
			break;
		case "Lava Slime":
			fileImage += "Enemy/Lava/Slime";
			break;
		case "Magma Wolf":
			fileImage += "Enemy/Lava/Wolf";
			break;
		case "Igneous Golem":
			fileImage += "Enemy/Lava/Golem";
			break;
		case "Volcano Boss":
			fileImage += "Enemy/Lava/Volcano";
			break;

		// Time
		case "Rusty Automaton":
			fileImage += "Enemy/Time/Automaton";
			break;
		case "Steam Spider":
			fileImage += "Enemy/Time/Spider";
			break;
		case "Mechanical Hound":
			fileImage += "Enemy/Time/Hound";
			break;
		case "Battery Mantis":
			fileImage += "Enemy/Time/Mantis";
			break;
		case "Clockwork Requiem":
			fileImage += "Enemy/Time/Requiem";
			break;
		default:
			fileImage += "Scene/MainMenu";
			System.out.println("Don't have this enemy in this game");
			break;
		}

		fileImage += ".png";

		return new Image(ClassLoader.getSystemResource(fileImage).toString());
	}
}
