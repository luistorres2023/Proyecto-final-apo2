package usc.edu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JFrame {

    BufferedImage fondo;
    Clip music;

    public MainMenu() {

        try {
            fondo = ImageIO.read(getClass().getResource("/assets/ui/menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        playMusic();

        setTitle("Tower Defense");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel() {
            {
    setOpaque(true);
    setBackground(Color.BLACK);
}

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                if (fondo != null) {
                    g2.drawImage(fondo,0,0,getWidth(),getHeight(),null);
                }

                GradientPaint gradient = new GradientPaint(0,0,new Color(0,0,0,80),0,getHeight(),new Color(0,0,0,180));

                g2.setPaint(gradient);
                g2.fillRect(0,0,getWidth(),getHeight());

                String title = "TOWER DEFENSE";

                g2.setFont(MedievalFont.getFont(90f));

                FontMetrics fm = g2.getFontMetrics();

                int x = (getWidth() - fm.stringWidth(title)) / 2;
                int y = 140;

                g2.setColor(new Color(0,0,0,180));
                g2.drawString(title,x + 6,y + 6);
                g2.setColor(new Color(240,210,120));
                g2.drawString(title,x,y);
            }
        };

        panel.setLayout(null);

        JPanel buttonContainer = new JPanel();

        buttonContainer.setOpaque(false);
        buttonContainer.setLayout(new BoxLayout(buttonContainer,BoxLayout.Y_AXIS));

        JButton play = new JButton("PLAY");
        JButton config = new JButton("CONFIG");
        JButton exit = new JButton("EXIT");

        styleButton(play);
        styleButton(config);
        styleButton(exit);

        play.addActionListener(e -> {

            stopMusic();

            dispose();

            SwingUtilities.invokeLater(() -> {
                new WaveMenu();
            });
        });

        exit.addActionListener(e -> System.exit(0));

        buttonContainer.add(play);
        buttonContainer.add(Box.createVerticalStrut(25));
        buttonContainer.add(config);
        buttonContainer.add(Box.createVerticalStrut(25));
        buttonContainer.add(exit);
        buttonContainer.setBounds(430,220,420,340);
        panel.add(buttonContainer);

        setContentPane(panel);
        revalidate();
        repaint();
        setVisible(true);
    }

    private void styleButton(JButton button) {

        button.setFont(MedievalFont.getFont(36f));
        button.setPreferredSize(new Dimension(420,85));
        button.setMaximumSize(new Dimension(420,85));
        button.setMinimumSize(new Dimension(420,85));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(20,20,20,220));
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(212,175,55),4),BorderFactory.createEmptyBorder(10,40,10,40)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                button.setBackground(new Color(90,65,20,240));

                button.setForeground(new Color(255,230,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {

                button.setBackground(new Color(20,20,20,220));

                button.setForeground(Color.WHITE);
            }
        });
    }

    private void playMusic() {

        try {

            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/assets/music/background.wav"));

            music = AudioSystem.getClip();

            music.open(audio);

            FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);

            gainControl.setValue(-15.0f);

            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void stopMusic() {

        if (music != null) {

            music.stop();

            music.close();
        }
    }
}