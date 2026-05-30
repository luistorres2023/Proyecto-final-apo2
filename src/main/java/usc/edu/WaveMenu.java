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

    private int record10,record20,record30,record40;
    private int time10,time20,time30,time40;
    private double lives10,lives20,lives30,lives40;

    public WaveMenu() {

        loadRecords();

        try {
            URL url = getClass().getResource("/assets/ui/menu.png");
            if(url != null) {
                fondo = ImageIO.read(url);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        playMusic();

        setTitle("Select Waves");
        setSize(1000,650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                if(fondo != null) {
                    g2.drawImage(fondo,0,0,getWidth(),getHeight(),this);
                }

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

                String title = "SELECT WAVES";
                g2.setFont(MedievalFont.getFont(40f));
                g2.setColor(Color.BLACK);
                FontMetrics fm = g2.getFontMetrics();
                int x = 80;
                
                g2.drawString(title,x,100);
                g2.setFont(MedievalFont.getFont(25f));
g2.setColor(Color.BLACK);

int baseX = 450;
int baseY = 90;

g2.drawString("CLASIFICACION",baseX,baseY);

g2.setFont(MedievalFont.getFont(20f));
g2.setColor(Color.WHITE);

g2.drawRect(baseX - 20,baseY + 20,420,240);

g2.drawLine(baseX + 60,baseY + 20,baseX + 60,baseY + 260);
g2.drawLine(baseX + 170,baseY + 20,baseX + 170,baseY + 260);
g2.drawLine(baseX + 290,baseY + 20,baseX + 290,baseY + 260);

g2.drawLine(baseX - 20,baseY + 60,baseX + 400,baseY + 60);
g2.drawLine(baseX - 20,baseY + 110,baseX + 400,baseY + 110);
g2.drawLine(baseX - 20,baseY + 160,baseX + 400,baseY + 160);
g2.drawLine(baseX - 20,baseY + 210,baseX + 400,baseY + 210);

g2.drawString("Wave",baseX,baseY + 50);
g2.drawString("Record",baseX + 80,baseY + 50);
g2.drawString("Tiempo",baseX + 190,baseY + 50);
g2.drawString("Vidas",baseX + 320,baseY + 50);

g2.drawString("10",baseX + 10,baseY + 95);
g2.drawString(String.valueOf(record10),baseX + 95,baseY + 95);
g2.drawString(formatTime(time10),baseX + 200,baseY + 95);
g2.drawString(String.valueOf(lives10),baseX + 325,baseY + 95);

g2.drawString("20",baseX + 10,baseY + 145);
g2.drawString(String.valueOf(record20),baseX + 95,baseY + 145);
g2.drawString(formatTime(time20),baseX + 200,baseY + 145);
g2.drawString(String.valueOf(lives20),baseX + 325,baseY + 145);

g2.drawString("30",baseX + 10,baseY + 195);
g2.drawString(String.valueOf(record30),baseX + 95,baseY + 195);
g2.drawString(formatTime(time30),baseX + 200,baseY + 195);
g2.drawString(String.valueOf(lives30),baseX + 325,baseY + 195);

g2.drawString("40",baseX + 10,baseY + 245);
g2.drawString(String.valueOf(record40),baseX + 95,baseY + 245);
g2.drawString(formatTime(time40),baseX + 200,baseY + 245);
g2.drawString(String.valueOf(lives40),baseX + 325,baseY + 245);

g2.setFont(MedievalFont.getFont(25f));
g2.setColor(Color.BLACK);

int topX = 450;
int topY = 390;

g2.drawString("TOP 4 INFINITE",topX,topY);

g2.setFont(MedievalFont.getFont(20f));
g2.setColor(Color.WHITE);

g2.drawRect(topX - 20,topY + 20,380,190);

g2.drawLine(topX + 40,topY + 20,topX + 40,topY + 210);
g2.drawLine(topX + 120,topY + 20,topX + 120,topY + 210);
g2.drawLine(topX + 220,topY + 20,topX + 220,topY + 210);

g2.drawLine(topX - 20,topY + 60,topX + 360,topY + 60);
g2.drawLine(topX - 20,topY + 100,topX + 360,topY + 100);
g2.drawLine(topX - 20,topY + 140,topX + 360,topY + 140);
g2.drawLine(topX - 20,topY + 180,topX + 360,topY + 180);

g2.drawString("TOP",topX - 5,topY + 50);
g2.drawString("WAVE",topX + 55,topY + 50);
g2.drawString("TIEMPO",topX + 140,topY + 50);
g2.drawString("VIDAS",topX + 255,topY + 50);

g2.drawString("1°",topX,topY + 90);
g2.drawString("---",topX + 55,topY + 90);
g2.drawString("---",topX + 145,topY + 90);
g2.drawString("---",topX + 265,topY + 90);

g2.drawString("2°",topX,topY + 130);
g2.drawString("---",topX + 55,topY + 130);
g2.drawString("---",topX + 145,topY + 130);
g2.drawString("---",topX + 265,topY + 130);

g2.drawString("3°",topX,topY + 170);
g2.drawString("---",topX + 55,topY + 170);
g2.drawString("---",topX + 145,topY + 170);
g2.drawString("---",topX + 265,topY + 170);

g2.drawString("4°",topX,topY + 210);
g2.drawString("---",topX + 55,topY + 210);
g2.drawString("---",topX + 145,topY + 210);
g2.drawString("---",topX + 265,topY + 210);
            }
        };

        panel.setLayout(null);
        panel.setOpaque(false);

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
        buttons.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));

        addButton(buttons,"10 WAVES",10);
        addButton(buttons,"20 WAVES",20);
        addButton(buttons,"30 WAVES",30);
        addButton(buttons,"40 WAVES",40);
        addButton(buttons,"INFINITE",-1);

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

        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setMaximumSize(new Dimension(320,60));
        buttons.add(Box.createVerticalStrut(-1));
        buttons.add(back);
        buttons.setBounds(40, 90, 350, 450);
        panel.add(buttons);

        setContentPane(panel);
        setVisible(true);
    }

    private void loadRecords() {
        try {
            java.io.File file = new java.io.File("record.txt");

            if(file.exists()) {
                java.util.Scanner sc = new java.util.Scanner(file);
                sc.useLocale(java.util.Locale.US);

                record10 = sc.nextInt();
                time10 = sc.nextInt();
                lives10 = sc.nextDouble();

                record20 = sc.nextInt();
                time20 = sc.nextInt();
                lives20 = sc.nextDouble();

                record30 = sc.nextInt();
                time30 = sc.nextInt();
                lives30 = sc.nextDouble();

                record40 = sc.nextInt();
                time40 = sc.nextInt();
                lives40 = sc.nextDouble();

                sc.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);

        b.setFont(MedievalFont.getFont(30f));
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(20,20,20,220));

        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212,175,55),4),
                BorderFactory.createEmptyBorder(10,40,10,40)
        ));

        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(320,60));
        b.setMaximumSize(new Dimension(320,60));
        b.setMinimumSize(new Dimension(320,60));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(90,65,20,240));
                b.setForeground(new Color(255,230,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(20,20,20,220));
                b.setForeground(Color.WHITE);
            }
        });

        return b;
    }
    private String formatTime(int seconds) {

    int min = seconds / 60;
    int sec = seconds % 60;

    return String.format("%d:%02d", min, sec);
}

    private void addButton(JPanel panel,String text,int waves) {
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
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    getClass().getResource("/assets/music/magodeoz.wav"));

            music = AudioSystem.getClip();
            music.open(audio);

            FloatControl gainControl = (FloatControl)
                    music.getControl(FloatControl.Type.MASTER_GAIN);

            gainControl.setValue(-10.0f);
            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();

        } catch(Exception e) {
            System.out.println("No se pudo cargar la musica");
        }
    }

    private void stopMusic() {
        if(music != null) {
            music.stop();
            music.close();
        }
    }
}