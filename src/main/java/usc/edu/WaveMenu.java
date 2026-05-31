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

    private int[] infScore = new int[4];
    private int[] infWave = new int[4];
    private int[] infTime = new int[4];
    private double[] infLives = new double[4];
     {
    for (int i = 0; i < 4; i++) {
        infScore[i] = 0;
        infWave[i] = 0;
        infTime[i] = 0;
        infLives[i] = 0;
    }
}

    private int record10, record20, record30, record40;
    private int time10, time20, time30, time40;
    private double lives10, lives20, lives30, lives40;

    public WaveMenu() {

        loadRecords();
        loadInfinite();
        for (int i = 0; i < 4; i++) {
    System.out.println(
        infScore[i] + " " +
        infWave[i] + " " +
        infTime[i] + " " +
        infLives[i]
    );
}
        try {
            URL url = getClass().getResource("/assets/ui/menu.png");
            if (url != null) {
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
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                if (fondo != null) {
                    g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setFont(MedievalFont.getFont(40f));
                g2.setColor(Color.BLACK);
                g2.drawString("SELECT WAVES", 80, 80);

                drawTable(g2);
                drawInfinite(g2);
            }
        };

        panel.setLayout(null);

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.setBounds(40, 90, 350, 450);

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
    }
   
    private void drawTable(Graphics2D g2) {

    int baseX = 450, baseY = 90;

    int width = 420;
    int height = 240;

    g2.setColor(new Color(0, 0, 0, 120));
    g2.fillRect(baseX - 20, baseY + 20, width, height);

    g2.setFont(MedievalFont.getFont(25f));
    g2.setColor(Color.BLACK);
    g2.drawString("CLASIFICACION", baseX, baseY);

    g2.setFont(MedievalFont.getFont(20f));
    g2.setColor(Color.WHITE);

    g2.drawRect(baseX - 20, baseY + 20, width, height);

    g2.drawLine(baseX + 60, baseY + 20, baseX + 60, baseY + 260);
    g2.drawLine(baseX + 170, baseY + 20, baseX + 170, baseY + 260);
    g2.drawLine(baseX + 290, baseY + 20, baseX + 290, baseY + 260);

    g2.drawLine(baseX - 20, baseY + 60, baseX + 400, baseY + 60);
    g2.drawLine(baseX - 20, baseY + 110, baseX + 400, baseY + 110);
    g2.drawLine(baseX - 20, baseY + 160, baseX + 400, baseY + 160);
    g2.drawLine(baseX - 20, baseY + 210, baseX + 400, baseY + 210);

    g2.drawString("Wave", baseX + 10, baseY + 50);
    g2.drawString("Record", baseX + 80, baseY + 50);
    g2.drawString("Tiempo", baseX + 190, baseY + 50);
    g2.drawString("Vidas", baseX + 320, baseY + 50);

    drawRow(g2, "10", record10, time10, lives10, baseX, baseY + 95);
    drawRow(g2, "20", record20, time20, lives20, baseX, baseY + 145);
    drawRow(g2, "30", record30, time30, lives30, baseX, baseY + 195);
    drawRow(g2, "40", record40, time40, lives40, baseX, baseY + 245);
}
private void drawRow(Graphics2D g2, String wave,
                     int record, int time, double lives,
                     int baseX, int y) {

    g2.setFont(MedievalFont.getFont(20f));
    g2.setColor(Color.WHITE);
    g2.drawString(wave, baseX + 10, y);
    g2.drawString(String.valueOf(record), baseX + 90, y);
    g2.drawString(formatTime(time), baseX + 190, y);
    g2.drawString(String.valueOf(lives), baseX + 320, y);
}
private void sortInfinite() {

    for (int i = 0; i < 4; i++) {
        for (int j = i + 1; j < 4; j++) {

            if (infScore[j] > infScore[i]) {

                int s = infScore[i];
                infScore[i] = infScore[j];
                infScore[j] = s;

                int w = infWave[i];
                infWave[i] = infWave[j];
                infWave[j] = w;

                int t = infTime[i];
                infTime[i] = infTime[j];
                infTime[j] = t;

                double l = infLives[i];
                infLives[i] = infLives[j];
                infLives[j] = l;
            }
        }
    }
}
   private void drawInfinite(Graphics2D g2) {

    sortInfinite();

    int topX = 450, topY = 390;
    int width = 460;   
    int height = 190;

    g2.setColor(new Color(0, 0, 0, 120));
    g2.fillRect(topX - 20, topY + 20, width, height);

    g2.setFont(MedievalFont.getFont(25f));
    g2.setColor(Color.BLACK);
    g2.drawString("TOP 4 INFINITE", topX, topY);

    int colPos   = topX;
    int colScore = topX + 55;
    int colWave  = topX + 155;
    int colTime  = topX + 255;
    int colLives = topX + 355;

    g2.setFont(MedievalFont.getFont(18f));
    g2.setColor(Color.WHITE);

    int headerY = topY + 45;

    g2.drawString("POS", colPos, headerY);
    g2.drawString("SCORE", colScore, headerY);
    g2.drawString("WAVE", colWave, headerY);
    g2.drawString("TIME", colTime, headerY);
    g2.drawString("LIVES", colLives, headerY);

    for (int i = 0; i < 4; i++) {

        int y = topY + 90 + (i * 40);

        g2.drawString((i + 1) + "°", colPos, y);
        g2.drawString(String.valueOf(infScore[i]), colScore, y);
        g2.drawString(String.valueOf(infWave[i]), colWave, y);
        g2.drawString(formatTime(infTime[i]), colTime, y);
        g2.drawString(String.valueOf(infLives[i]), colLives, y);
    }

    g2.setColor(Color.WHITE);
    g2.drawRect(topX - 20, topY + 20, width, height);
    g2.drawLine(colScore - 10, topY + 20, colScore - 10, topY + 210);
    g2.drawLine(colWave - 10, topY + 20, colWave - 10, topY + 210);
    g2.drawLine(colTime - 10, topY + 20, colTime - 10, topY + 210);
    g2.drawLine(colLives - 10, topY + 20, colLives - 10, topY + 210);
    g2.drawLine(topX - 20, topY + 60, topX - 20 + width, topY + 60);
    g2.drawLine(topX - 20, topY + 100, topX - 20 + width, topY + 100);
    g2.drawLine(topX - 20, topY + 140, topX - 20 + width, topY + 140);
    g2.drawLine(topX - 20, topY + 180, topX - 20 + width, topY + 180);
}
    private void loadInfinite() {
    try {
        java.io.File file = new java.io.File("infinite.txt");
        System.out.println(file.getAbsolutePath());

        for (int i = 0; i < 4; i++) {
            infScore[i] = 0;
            infWave[i] = 0;
            infTime[i] = 0;
            infLives[i] = 0;
        }

        if (!file.exists()) return;

java.util.Scanner sc = new java.util.Scanner(file);
sc.useLocale(java.util.Locale.US);
        int i = 0;

        while (sc.hasNext() && i < 4) {

            if (sc.hasNextInt()) infScore[i] = sc.nextInt();
            else break;

            if (sc.hasNextInt()) infWave[i] = sc.nextInt();
            else break;

            if (sc.hasNextInt()) infTime[i] = sc.nextInt();
            else break;

            if (sc.hasNextDouble()) infLives[i] = sc.nextDouble();
            else break;

            i++;
        }

        sc.close();

        sortInfinite();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private void loadRecords() {
        try {
            java.io.File file = new java.io.File("record.txt");
            if (!file.exists()) return;

            java.util.Scanner sc = new java.util.Scanner(file);
            sc.useLocale(java.util.Locale.US);

            record10 = sc.nextInt(); time10 = sc.nextInt(); lives10 = sc.nextDouble();
            record20 = sc.nextInt(); time20 = sc.nextInt(); lives20 = sc.nextDouble();
            record30 = sc.nextInt(); time30 = sc.nextInt(); lives30 = sc.nextDouble();
            record40 = sc.nextInt(); time40 = sc.nextInt(); lives40 = sc.nextDouble();

            
for (int i = 0; i < 4; i++) {

    if (sc.hasNextInt()) {
        infScore[i] = sc.nextInt();
    } else {
        infScore[i] = 0;
    }

    if (sc.hasNextInt()) {
        infWave[i] = sc.nextInt();
    } else {
        infWave[i] = 0;
    }

    if (sc.hasNextInt()) {
        infTime[i] = sc.nextInt();
    } else {
        infTime[i] = 0;
    }

    if (sc.hasNextDouble()) {
        infLives[i] = sc.nextDouble();
    } else {
        infLives[i] = 0;
    }
}

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);

        b.setFont(MedievalFont.getFont(30f));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(20, 20, 20, 220));
        b.setFocusPainted(false);

        b.setMaximumSize(new Dimension(320, 60));

        return b;
    }

    private void addButton(JPanel panel, String text, int waves) {
        JButton b = createButton(text);

        b.addActionListener(e -> {
            stopMusic();

            JFrame gameFrame = new JFrame("Tower Defense");
            GamePanel game = new GamePanel(waves);

            gameFrame.setContentPane(game);
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);

            dispose();
        });

        panel.add(b);
        panel.add(Box.createVerticalStrut(12));
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }

    private void playMusic() {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    getClass().getResource("/assets/music/magodeoz.wav"));

            music = AudioSystem.getClip();
            music.open(audio);
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