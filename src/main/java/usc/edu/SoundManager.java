package usc.edu;

import javax.sound.sampled.*;
import java.net.URL;

public class SoundManager {

    private Clip clip;

    public void playMusic(String path) {

        try {

            System.out.println(path);
            URL url = getClass().getResource(path);
            System.out.println(url);

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audio);

            FloatControl volume =
                (FloatControl) clip.getControl(
                    FloatControl.Type.MASTER_GAIN);

            volume.setValue(-5.0f);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {

            System.out.println("ERROR DE AUDIO:");
            e.printStackTrace();
        }
    }

    public void stopMusic() {

        if (clip != null) {

            clip.stop();
            clip.close();
        }
    }
}