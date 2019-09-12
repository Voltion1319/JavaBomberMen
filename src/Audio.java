import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Audio implements Runnable {

	@Override
	public void run() {
		try {
			FileInputStream f = new FileInputStream("resource/sound.mp3");
			try {
				Player player = new Player(f);
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
