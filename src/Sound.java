import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	private Clip yesClip;
	private Clip noClip;
	private boolean filesLoaded;
	
	public Sound() {
		try {
			// Laddar videofilerna
			AudioInputStream yesAudioInputStream = AudioSystem.getAudioInputStream(new File("src\\yes.wav"));
			yesClip = AudioSystem.getClip();
			yesClip.open(yesAudioInputStream);

			AudioInputStream noAudioInputStream = AudioSystem.getAudioInputStream(new File("src\\no.wav"));
			noClip = AudioSystem.getClip();
			noClip.open(noAudioInputStream);
			
			filesLoaded = true;
		} catch (Exception e) {
			System.out.println("Kunde inte ladda ljudfiler ");
			filesLoaded = false;
			e.printStackTrace();
		}
	}
	
	public void playYesSound() {
		if (filesLoaded) {
			yesClip.setMicrosecondPosition(0);
			yesClip.start();
		}
	}
	
    public void playNoSound() {
		if (filesLoaded) {
			noClip.setMicrosecondPosition(0);
			noClip.start();
		}
    }
}
