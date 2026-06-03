package usc.edu;

import javax.sound.sampled.*;
import java.net.URL;

public class NecroMusicManager {

    private Clip necroClip;

    public void load() {
        try {
            URL url = getClass().getResource("/assets/music/returnbydeath.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            necroClip = AudioSystem.getClip();
            necroClip.open(audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (necroClip == null) return;

        stop();

        necroClip.setFramePosition(0);
        necroClip.loop(Clip.LOOP_CONTINUOUSLY);
        necroClip.start();
    }

    public void stop() {
        if (necroClip != null) {
            necroClip.stop();
            necroClip.setFramePosition(0);
        }
    }
}