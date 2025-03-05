package soundmanager;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundManager {

	private static AudioClip loadSound(String fileName) {
		try {
			URL soundURL = SoundManager.class.getClassLoader().getResource("sound/" + fileName);
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

	public static void playClickSound() {
		AudioClip Sound = loadSound("click.mp3");
		if (Sound != null) {
			Sound.play();
		}
	}

	public static void playLoseSound() {
		AudioClip Sound = loadSound("lose.mp3");
		if (Sound != null) {
			Sound.play();
		}

	}

}
