package usc.edu;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

import usc.edu.GamePanel;
import usc.edu.Main;
import usc.edu.StartMenu;


public class WaveMenu extends JFrame {

    private BufferedImage fondo;
    private Clip music;

    public WaveMenu() {


        try {
            URL url = getClass().getResource("/assets/ui/menu.png");
System.out.println(url);
            if (url == null) {
                System.out.println(" se encontró la imagen menu.png (revisa la ruta)");
            } else {
                fondo = ImageIO.read(url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        playMusic();

        setTitle("Select Waves");
        setSize(1000, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);


        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;


                if (fondo != null) {
                    g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                String title = "SELECT WAVES";

                g2.setFont(MedievalFont.getFont(60f));

                FontMetrics fm = g2.getFontMetrics();

                int x = (getWidth() - fm.stringWidth(title)) / 2;
                int y = 100;

                g2.setColor(new Color(240, 210, 120));
                g2.drawString(title, x, y);
            }
        };

        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);


        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
        buttons.setBackground(new Color(0, 0, 0, 0));

        addButton(buttons, "10 WAVES", 10);
        addButton(buttons, "20 WAVES", 20);
        addButton(buttons, "30 WAVES", 30);
        addButton(buttons, "40 WAVES", 40);
        addButton(buttons, "INFINITE", -1);

        JButton back = createButton("BACK");

        back.addActionListener(e -> {

            stopMusic();
            dispose();

            Main.frame = new JFrame("Tower Defense");
            Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Main.frame.setContentPane(new StartMenu());
            Main.frame.pack();
            Main.frame.setLocationRelativeTo(null);
            Main.frame.setVisible(true);
        });

        buttons.add(Box.createVerticalStrut(10));
        buttons.add(back);

        panel.add(buttons);

        setContentPane(panel);
        setVisible(true);


        revalidate();
        repaint();
    }

    private JButton createButton(String text) {

        JButton b = new JButton(text);

        b.setFont(MedievalFont.getFont(30f));
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(20, 20, 20, 220));

        b.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(212, 175, 55), 4), BorderFactory.createEmptyBorder(10, 40, 10, 40)
                )
        );

        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(320, 60));
        b.setMaximumSize(new Dimension(320, 60));
        b.setMinimumSize(new Dimension(320, 60));
        
        b.setAlignmentX(Component.CENTER_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(90, 65, 20, 240));
                b.setForeground(new Color(255, 230, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(20, 20, 20, 220));
                b.setForeground(Color.WHITE);
            }
        });

        return b;
    }

    private void addButton(JPanel panel, String text, int waves) {

        JButton b = createButton(text);

        b.addActionListener(e -> {

            stopMusic();

            JFrame gameFrame = new JFrame("Tower Defense");

            GamePanel game = new GamePanel(waves);

            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setContentPane(game);
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setResizable(false);
            gameFrame.setVisible(true);

            dispose();
        });

        panel.add(b);
        panel.add(Box.createVerticalStrut(12));
    }

    private void playMusic() {

        try {

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(
                            getClass().getResource("/assets/music/background.wav")
                    );

            music = AudioSystem.getClip();
            music.open(audio);

            FloatControl gainControl =
                    (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);

            gainControl.setValue(-10.0f);

            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();

        } catch (Exception e) {
            System.out.println("No se pudo cargar la musica");
        }
    }

    private void stopMusic() {

        if (music != null) {
            music.stop();
            music.close();
        }
    }
}