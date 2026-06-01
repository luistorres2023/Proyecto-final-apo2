package usc.edu;

import javax.sound.sampled.*;
import java.net.URL;


public class SoundManager {

    private static Clip clip;

    public void playMusic(String path) {

        try {

            stopMusic();

            System.out.println(path);

            URL url = getClass().getResource(path);

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(audio);

            FloatControl volume =
                    (FloatControl) clip.getControl(
                            FloatControl.Type.MASTER_GAIN);

            float minVolume = volume.getMinimum();

            volume.setValue(minVolume);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            fadeIn(volume, minVolume, -5.0f);

        } catch (Exception e) {

            System.out.println("ERROR DE AUDIO:");
            e.printStackTrace();
        }
    }

    private void fadeIn(FloatControl volume,
                        float start,
                        float end) {

        new Thread(() -> {

            try {

                for (float v = start; v <= end; v += 1f) {

                    volume.setValue(v);
                    Thread.sleep(120);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void stopMusic() {

        if (clip != null) {

            try {

                FloatControl volume =
                        (FloatControl) clip.getControl(
                                FloatControl.Type.MASTER_GAIN);

                for (float v = volume.getValue();
                     v >= volume.getMinimum();
                     v -= 1f) {

                    volume.setValue(v);
                    Thread.sleep(50);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            clip.stop();
            clip.close();
        }
    }

    public void setVolume(float db) {

        if (clip != null) {

            FloatControl volume =
                    (FloatControl) clip.getControl(
                            FloatControl.Type.MASTER_GAIN);

            volume.setValue(db);
        }
    }
}