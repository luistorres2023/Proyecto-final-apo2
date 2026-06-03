package usc.edu;

import javax.sound.sampled.*;
import java.net.URL;


public class SoundManager {

    private static Clip clip;
    private Clip returnByDeathClip;

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
    public void playNecroMusicInstant() {

    stopMusicInstant();

    System.out.println("returnByDeathClip = " + returnByDeathClip);

    if(returnByDeathClip != null){

        System.out.println("isOpen = " + returnByDeathClip.isOpen());

        clip = returnByDeathClip;

        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }
}
public void loadNecroMusic() {

    try {

        URL url =
            getClass().getResource("/assets/music/returnbydeath.wav");

        System.out.println("URL NECRO = " + url);

        AudioInputStream audio =
            AudioSystem.getAudioInputStream(url);

        returnByDeathClip = AudioSystem.getClip();
        returnByDeathClip.open(audio);

        System.out.println("NECRO MUSIC CARGADA");

    } catch (Exception e) {

        System.out.println("ERROR CARGANDO NECRO MUSIC");
        e.printStackTrace();
    }
}
    public void playMusicInstant(String path) {

    try {

        stopMusicInstant();

        URL url = getClass().getResource(path);

        AudioInputStream audio =
                AudioSystem.getAudioInputStream(url);

        clip = AudioSystem.getClip();
        clip.open(audio);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();

    } catch (Exception e) {

        e.printStackTrace();
    }
}
public void stopMusicInstant() {

    if (clip != null) {

        clip.stop();
        clip = null;
    }
}

    private void fadeIn(FloatControl volume,float start,float end) {

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

            FloatControl volume =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            volume.setValue(db);
        }
    }
}