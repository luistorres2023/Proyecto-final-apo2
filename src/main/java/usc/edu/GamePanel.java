package usc.edu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import usc.edu.Main;
import usc.edu.WaveMenu;
import usc.edu.StartMenu;

public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {

    private BufferedImage mapImage;

    final int WIDTH = 1600;
    final int HEIGHT = 900;
    public final int TILE_SIZE = 64;
    final int ROWS = HEIGHT / TILE_SIZE;
    final int COLS = WIDTH / TILE_SIZE;
    public static GamePanel instance;
    int[][] map = new int[ROWS][COLS];
    int[] infScore = new int[4];
    int[] infWave = new int[4];
    int[] infTime = new int[4];
    double[] infLives = new double[4];

    public BufferedImage grassImg;
    public BufferedImage pathImg;
    public BufferedImage LavaImg;
    public BufferedImage tower1Img;
    public BufferedImage tower2Img;
    public BufferedImage tower3Img;
    public BufferedImage tower4Img;
    public BufferedImage basicIcon;
    public BufferedImage slowIcon;
    public BufferedImage magicIcon;
    public BufferedImage sniperIcon;
    public BufferedImage moneyIcon;
    public BufferedImage heartIcon;
    public BufferedImage waveIcon;
    public BufferedImage normalenemyImg;
    public BufferedImage fastEnemyImg;
    public BufferedImage tankEnemyImg;
    public BufferedImage witchImg;
    public BufferedImage bulletImg;
    public BufferedImage NIGROMANTEImg;

    Thread gameThread;

    SoundManager music = new SoundManager();
    boolean running = true;
    boolean paused = false;

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Enemy> enemiesToAdd = new ArrayList<>();
    ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    public static ArrayList<Bullet> bulletsToAdd = new ArrayList<>();

    int money = 200;
    double lives = 10;
    int maxWave = -1;
    int wave = 1;
    int score = 0;
    int record10 = 0;
    int record20 = 0;
    int record30 = 0;
    int record40 = 0;
    int time10 = 0;
    int time20 = 0;
    int time30 = 0;
    int time40 = 0;
    double lives10 = 0;
    double lives20 = 0;
    double lives30 = 0;
    double lives40 = 0;
    long startTime;
    int elapsedSeconds = 0;
    long pausedAccumulatedTime = 0;
    long pauseStartTime = 0;
    double currentMultiplier = 1.0;

    long lastSpawn = 0;
    long pausedTime = 0;
    long pauseStart = 0;
    int enemiesSpawned = 0;
    int enemiesPerWave = 7;
    int selectedTower = 1;
    int mouseGridX;
    int mouseGridY;

    Rectangle resumeButton = new Rectangle(WIDTH / 2 - 100, 220, 200, 50);
    Rectangle restartButton = new Rectangle(WIDTH / 2 - 100, 300, 200, 50);
    Rectangle exitButton = new Rectangle(WIDTH / 2 - 100, 380, 200, 50);

    public static ArrayList<Point> pathPoints = new ArrayList<>();

    public int getCurrentRecord() {

        if (maxWave == 10)
            return record10;
        if (maxWave == 20)
            return record20;
        if (maxWave == 30)
            return record30;
        if (maxWave == 40)
            return record40;

        return infScore[0];
    }

    public GamePanel(int maxWave, boolean cargarGuardado) {
        instance = this;
        this.maxWave = maxWave;
        startTime = System.currentTimeMillis();
        loadRecord();
        loadInfiniteRecord();
        music.playMusic("/assets/music/background.wav");

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);

        createMap();
        createPath();
        loadImages();

        try {

            mapImage = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/mapa.png"));

        } catch (Exception e) {

            e.printStackTrace();
        }
        if (cargarGuardado && GameSave.existe()) {
            GameSave.cargar(this);
        }

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void updateInfiniteRanking() {

        for (int i = 0; i < 4; i++) {

            if (infScore[i] == score &&
                    infWave[i] == wave) {
                return;
            }
        }

        int pos = -1;

        for (int i = 0; i < 4; i++) {

            if (score > infScore[i]) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            return;
        }

        for (int j = 3; j > pos; j--) {

            infScore[j] = infScore[j - 1];
            infWave[j] = infWave[j - 1];
            infTime[j] = infTime[j - 1];
            infLives[j] = infLives[j - 1];
        }

        infScore[pos] = score;
        infWave[pos] = wave;
        infTime[pos] = elapsedSeconds;
        infLives[pos] = lives;

    }

    public void loadRecord() {

        try {
            java.io.File file = new java.io.File("record.txt");

            if (!file.exists())
                return;

            java.util.Scanner sc = new java.util.Scanner(file);
            sc.useLocale(java.util.Locale.US);

            if (sc.hasNextInt())
                record10 = sc.nextInt();
            if (sc.hasNextInt())
                time10 = sc.nextInt();
            if (sc.hasNextDouble())
                lives10 = sc.nextDouble();

            if (sc.hasNextInt())
                record20 = sc.nextInt();
            if (sc.hasNextInt())
                time20 = sc.nextInt();
            if (sc.hasNextDouble())
                lives20 = sc.nextDouble();

            if (sc.hasNextInt())
                record30 = sc.nextInt();
            if (sc.hasNextInt())
                time30 = sc.nextInt();
            if (sc.hasNextDouble())
                lives30 = sc.nextDouble();

            if (sc.hasNextInt())
                record40 = sc.nextInt();
            if (sc.hasNextInt())
                time40 = sc.nextInt();
            if (sc.hasNextDouble())
                lives40 = sc.nextDouble();

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRecord() {

        try {

            java.io.PrintWriter pw = new java.io.PrintWriter("record.txt");

            pw.println(record10);
            pw.println(time10);
            pw.println(lives10);

            pw.println(record20);
            pw.println(time20);
            pw.println(lives20);

            pw.println(record30);
            pw.println(time30);
            pw.println(lives30);

            pw.println(record40);
            pw.println(time40);
            pw.println(lives40);

            pw.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void createPath() {

        pathPoints.clear();

        pathPoints.add(new Point(0, 4));

        pathPoints.add(new Point(5, 4));

        pathPoints.add(new Point(5, 8));

        pathPoints.add(new Point(10, 8));

        pathPoints.add(new Point(10, 3));

        pathPoints.add(new Point(14, 3));

        pathPoints.add(new Point(14, 8));

        pathPoints.add(new Point(20, 8));

        pathPoints.add(new Point(20, 5));

        pathPoints.add(new Point(24, 5));

    }

    public int getRecord(int waves) {

        if (waves == 10)
            return record10;
        if (waves == 20)
            return record20;
        if (waves == 30)
            return record30;
        if (waves == 40)
            return record40;

        return 0;
    }

    public String getTime(int waves) {

        int tiempo = 0;

        if (waves == 10)
            tiempo = time10;
        if (waves == 20)
            tiempo = time20;
        if (waves == 30)
            tiempo = time30;
        if (waves == 40)
            tiempo = time40;

        int min = tiempo / 60;
        int seg = tiempo % 60;

        return String.format("%d:%02d", min, seg);
    }

    public double getLives(int waves) {

        if (waves == 10)
            return lives10;
        if (waves == 20)
            return lives20;
        if (waves == 30)
            return lives30;
        if (waves == 40)
            return lives40;

        return 0;
    }

    public void createMap() {

        map = new int[ROWS][COLS];

        for (int col = 0; col <= 5; col++)
            map[4][col] = 1;

        for (int row = 4; row <= 8; row++)
            map[row][5] = 1;

        for (int col = 5; col <= 10; col++)
            map[8][col] = 1;

        for (int row = 3; row <= 8; row++)
            map[row][10] = 1;

        for (int col = 10; col <= 15; col++)
            map[3][col] = 1;

        for (int row = 3; row <= 8; row++)
            map[row][15] = 1;

        for (int col = 15; col <= 20; col++)
            map[8][col] = 1;

        for (int row = 5; row <= 8; row++)
            map[row][20] = 1;

        for (int col = 20; col <= 24; col++)
            map[5][col] = 1;
    }

    public void loadImages() {

        try {

            grassImg = ImageIO.read(getClass().getResourceAsStream("/assets/grass.png"));
            pathImg = ImageIO.read(getClass().getResourceAsStream("/assets/path.png"));
            LavaImg = ImageIO.read(getClass().getResourceAsStream("/assets/lava.png"));

            tower1Img = ImageIO.read(getClass().getResourceAsStream("/assets/tower.png"));
            tower2Img = ImageIO.read(getClass().getResourceAsStream("/assets/tower2.png"));
            tower3Img = ImageIO.read(getClass().getResourceAsStream("/assets/tower3.png"));
            tower4Img = ImageIO.read(getClass().getResourceAsStream("/assets/tower4.png"));

            normalenemyImg = ImageIO.read(getClass().getResourceAsStream("/assets/normalenemy.png"));
            fastEnemyImg = ImageIO.read(getClass().getResourceAsStream("/assets/enemy_fast.png"));
            tankEnemyImg = ImageIO.read(getClass().getResourceAsStream("/assets/enemy_tank.png"));
            witchImg = ImageIO.read(getClass().getResourceAsStream("/assets/witch.png"));
            bulletImg = ImageIO.read(getClass().getResourceAsStream("/assets/bullet.png"));
            NIGROMANTEImg = ImageIO.read(getClass().getResourceAsStream("/assets/nigromante.png"));

            basicIcon = ImageIO.read(getClass().getResourceAsStream("/assets/tower.png"));
            slowIcon = ImageIO.read(getClass().getResourceAsStream("/assets/tower4.png"));
            magicIcon = ImageIO.read(getClass().getResourceAsStream("/assets/tower3.png"));
            sniperIcon = ImageIO.read(getClass().getResourceAsStream("/assets/tower2.png"));

            moneyIcon = ImageIO.read(getClass().getResourceAsStream("/assets/money.png"));
            heartIcon = ImageIO.read(getClass().getResourceAsStream("/assets/heart.png"));
            waveIcon = ImageIO.read(getClass().getResourceAsStream("/assets/wave.png"));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (running) {

            if (!paused) {
                updateGame();
            }
            repaint();
            try {
                Thread.sleep(16);
            } catch (Exception e) {

                System.out.println("ERROR DE AUDIO:");
                e.printStackTrace();
            }
        }
    }

    public void updateRecord() {

        if (maxWave == 10 && score > record10) {
            record10 = score;
            time10 = elapsedSeconds;
            lives10 = lives;
        }

        else if (maxWave == 20 && score > record20) {
            record20 = score;
            time20 = elapsedSeconds;
            lives20 = lives;
        }

        else if (maxWave == 30 && score > record30) {
            record30 = score;
            time30 = elapsedSeconds;
            lives30 = lives;
        }

        else if (maxWave == 40 && score > record40) {
            record40 = score;
            time40 = elapsedSeconds;
            lives40 = lives;
        }
    }

    public void updateGame() {

        spawnEnemies();
        if (!paused) {
            long now = System.currentTimeMillis();
            elapsedSeconds = (int) ((now - startTime - pausedAccumulatedTime) / 1000);
        }

        for (Enemy enemy : new ArrayList<>(enemies)) {

            enemy.update();

            if (enemy.finished && enemy.alive) {

                enemy.alive = false;

                if (enemy instanceof TankEnemy) {

                    lives -= 2.5;
                } else if (enemy instanceof Witch) {
                    lives -= 3;
                } else if (enemy instanceof NIGROMANTE) {
                    lives -= 10;

                } else {

                    lives--;
                }

            }
        }

        for (Tower tower : new ArrayList<>(towers)) {

            tower.update(enemies, bullets);
        }

        for (Bullet bullet : new ArrayList<>(bullets)) {

            bullet.update();
        }

        for (Bullet bullet : new ArrayList<>(bullets)) {

            if (!bullet.active)
                continue;

            for (Enemy enemy : new ArrayList<>(enemies)) {

                if (!enemy.alive)
                    continue;

                double dx = bullet.x - enemy.x;
                double dy = bullet.y - enemy.y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < 20) {

                    enemy.hp -= bullet.damage;

                    bullet.active = false;

                    if (enemy.hp <= 0) {

                enemy.alive = false;

                        if (enemy instanceof TankEnemy) {
                            money += 15;
                        } else if (enemy instanceof Witch) {
                            money += 30;
                        } else if (enemy instanceof NIGROMANTE) {
                            money += 250;
                        } else {
                            money += 5;
                        }

                        currentMultiplier = bullet.owner.scoreMultiplier;

                        int waveBonus = wave;

                        score += (int) (enemy.points * currentMultiplier * waveBonus);

                    }
                }
            }
        }

        enemies.addAll(enemiesToAdd);
        enemies.removeAll(enemiesToRemove);
        enemiesToAdd.clear();
        enemiesToRemove.clear();
        enemies.removeIf(enemy -> !enemy.alive);
        towers.removeIf(tower -> !tower.alive);
        bullets.removeIf(bullet -> !bullet.active);
        bullets.addAll(bulletsToAdd);
        bulletsToAdd.clear();

        if (enemiesSpawned >= enemiesPerWave && enemies.isEmpty() && enemiesToAdd.isEmpty()) {

            wave++;
            score += wave * 20;

            if (maxWave != -1 && wave > maxWave) {

                if (lives == 10) {
                    score += 500;
                }

                GameSave.borrar();
                saveAllData();
                running = false;
                JOptionPane.showMessageDialog(this, "VICTORY!");

                music.stopMusic();
                new MainMenu();
                javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
                return;
            }

            enemiesPerWave += 4;
            enemiesSpawned = 0;
        }

        if (lives <= 0) {

            lives = 0;
            GameSave.borrar();
            saveAllData();

            running = false;
            JOptionPane.showMessageDialog(this, "GAME OVER!");

            music.stopMusic();
            new WaveMenu();

            javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
            return;
        }
    }

    private void saveAllData() {

        if (maxWave == -1) {

            updateInfiniteRanking();
            saveInfiniteRecord();

        } else {

            updateRecord();
            saveRecord();
        }
    }

    public void saveInfiniteRecord() {

        try {

            PrintWriter pw = new PrintWriter("infinite.txt");

            for (int i = 0; i < 4; i++) {

                pw.println(infScore[i]);
                pw.println(infWave[i]);
                pw.println(infTime[i]);
                pw.println(infLives[i]);
            }

            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawnEnemies() {
        long currentTime = System.currentTimeMillis();

        if (enemiesSpawned >= enemiesPerWave)
            return;

        if (currentTime - lastSpawn > 1000) {

            boolean spawnWitch = false;
            boolean spawnNigromante = false;
            int witchesPerWave = 0;

            if (wave % 2 == 0)
                witchesPerWave = wave / 2;

            if (witchesPerWave > 0) {

                if (enemiesSpawned == 0) {
                    spawnWitch = true;
                } else if (witchesPerWave >= 2 && enemiesSpawned == enemiesPerWave - 1) {
                    spawnWitch = true;
                } else if (witchesPerWave > 2) {
                    int extraWitches = witchesPerWave - 2;
                    double chance = (double) extraWitches / (enemiesPerWave - 2);

                    if (Math.random() < chance)
                        spawnWitch = true;
                }
            }

            if (wave % 5 == 0 && enemiesSpawned == 1)
                spawnNigromante = true;

            if (spawnWitch) {
                enemiesToAdd.add(new Witch(0, 4 * TILE_SIZE, witchImg));
            } else if (spawnNigromante) {
                enemiesToAdd.add(new NIGROMANTE(0, 4 * TILE_SIZE, NIGROMANTEImg));
            } else if (wave % 3 == 0) {
                enemiesToAdd.add(new TankEnemy(0, 4 * TILE_SIZE, tankEnemyImg));
            } else {
                if (Math.random() < 0.5)
                    enemiesToAdd.add(new NormalEnemy(0, 4 * TILE_SIZE, normalenemyImg));
                else
                    enemiesToAdd.add(new FastEnemy(0, 4 * TILE_SIZE, fastEnemyImg));
            }

            enemiesSpawned++;
            lastSpawn = currentTime;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        drawMap(g2);

        drawBuildPreview(g2);

        for (Tower tower : new ArrayList<>(towers)) {
            tower.draw(g2);
        }

        for (Enemy enemy : new ArrayList<>(enemies)) {
            enemy.draw(g2);
        }

        for (Bullet bullet : new ArrayList<>(bullets)) {
            bullet.draw(g2);
        }

        drawUI(g2);

        if (paused) {
            drawPauseMenu(g2);
            Toolkit.getDefaultToolkit().sync();
        }
    }

    public void drawMap(Graphics2D g2) {

        if (mapImage != null) {

            g2.drawImage(
                    mapImage,
                    0,
                    0,
                    WIDTH,
                    HEIGHT,
                    null);
        }
    }

    public void drawUI(Graphics2D g2) {

        g2.setColor(new Color(40, 40, 40, 220));

        g2.fillRoundRect(
                WIDTH - 350,
                20,
                310,
                130,
                20,
                20);

        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.setColor(Color.WHITE);
        g2.drawImage(moneyIcon, WIDTH - 335, 25, 32, 32, null);
        g2.drawString("" + money, WIDTH - 290, 50);
        g2.drawImage(heartIcon, WIDTH - 235, 25, 32, 32, null);
        g2.drawString("" + lives, WIDTH - 190, 50);
        g2.drawImage(waveIcon, WIDTH - 145, 25, 32, 32, null);
        g2.drawString("" + wave, WIDTH - 100, 50);
        g2.setFont(MedievalFont.getFont(22f));

        int min = elapsedSeconds / 60;
        int sec = elapsedSeconds % 60;
        String tiempo = String.format("%02d:%02d", min, sec);

        g2.drawString(tiempo, WIDTH - 280, 135);
        g2.drawString("RECORD: " + getCurrentRecord(), WIDTH - 280, 85);
        g2.drawString("SCORE: " + score, WIDTH - 280, 110);
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, HEIGHT - 100, WIDTH, 140);
        g2.setColor(Color.WHITE);
        g2.drawString("SELEC. TORRE", 20, HEIGHT - 60);
        g2.drawImage(basicIcon, 200, HEIGHT - 90, 64, 64, null);
        g2.drawImage(slowIcon, 320, HEIGHT - 90, 64, 64, null);
        g2.drawImage(magicIcon, 440, HEIGHT - 90, 64, 64, null);
        g2.drawImage(sniperIcon, 560, HEIGHT - 90, 64, 64, null);
        g2.setColor(Color.YELLOW);

        if (selectedTower == 1) {
            g2.drawRect(200, HEIGHT - 90, 64, 64);
            g2.drawRect(199, HEIGHT - 91, 66, 66);
        }
        if (selectedTower == 2) {
            g2.drawRect(320, HEIGHT - 90, 64, 64);
            g2.drawRect(319, HEIGHT - 91, 66, 66);
        }
        if (selectedTower == 3) {
            g2.drawRect(440, HEIGHT - 90, 64, 64);
            g2.drawRect(439, HEIGHT - 91, 66, 66);
        }

        if (selectedTower == 4) {
            g2.drawRect(560, HEIGHT - 90, 64, 64);
            g2.drawRect(559, HEIGHT - 91, 66, 66);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("$50", 210, HEIGHT - 10);
        g2.drawString("$60", 330, HEIGHT - 10);
        g2.drawString("$75", 450, HEIGHT - 10);
        g2.drawString("$100", 565, HEIGHT - 10);
    }

    public void drawBuildPreview(Graphics2D g2) {

        int x = mouseGridX * TILE_SIZE;
        int y = mouseGridY * TILE_SIZE;

        if (y >= HEIGHT - 100)
            return;

        BufferedImage previewImg = null;

        if (selectedTower == 1)
            previewImg = tower1Img;

        if (selectedTower == 2)
            previewImg = tower4Img;

        if (selectedTower == 3)
            previewImg = tower3Img;

        if (selectedTower == 4)
            previewImg = tower2Img;

        if (previewImg == null)
            return;

        boolean valid = canBuild(mouseGridX, mouseGridY);

        if (valid)
            g2.setColor(new Color(0, 255, 0, 80));
        else
            g2.setColor(new Color(255, 0, 0, 80));

        g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        int range = getPreviewRange();

        int centerX = x + TILE_SIZE / 2;
        int centerY = y + TILE_SIZE / 2;

        if (valid)
            g2.setColor(new Color(0, 255, 0, 30));
        else
            g2.setColor(new Color(255, 0, 0, 30));

        g2.fillOval(
                centerX - range,
                centerY - range,
                range * 2,
                range * 2);

        if (valid)
            g2.setColor(new Color(0, 255, 0, 120));
        else
            g2.setColor(new Color(255, 0, 0, 120));

        g2.drawOval(
                centerX - range,
                centerY - range,
                range * 2,
                range * 2);

        g2.setComposite(
                java.awt.AlphaComposite.getInstance(
                        java.awt.AlphaComposite.SRC_OVER,
                        0.5f));

        g2.drawImage(
                previewImg,
                x,
                y,
                TILE_SIZE,
                TILE_SIZE,
                null);

        g2.setComposite(
                java.awt.AlphaComposite.getInstance(
                        java.awt.AlphaComposite.SRC_OVER,
                        1.0f));
    }

    private int getPreviewRange() {

        switch (selectedTower) {

            case 1:
                return 180;

            case 2:
                return 245;

            case 3:
                return 250;

            case 4:
                return 350;
        }

        return 150;
    }

    public void drawPauseMenu(Graphics2D g2) {

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.setColor(Color.WHITE);
        g2.drawString("PAUSED", WIDTH / 2 - 100, 150);
        drawButton(g2, resumeButton, "Resume");
        drawButton(g2, restartButton, "Restart");
        drawButton(g2, exitButton, "Exit");
    }

    public void drawButton(Graphics2D g2, Rectangle rect, String text) {
        g2.setColor(Color.GRAY);
        g2.fillRect(rect.x, rect.y, rect.width, rect.height);
        g2.setColor(Color.WHITE);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);
        g2.drawString(text, rect.x + 40, rect.y + 33);
    }

    public void resetGame() {

        enemies.clear();
        towers.clear();
        bullets.clear();
        enemiesToAdd.clear();
        enemiesToRemove.clear();
        bulletsToAdd.clear();

        map = new int[ROWS][COLS];

        createMap();
        createPath();

        money = 200;
        lives = 10;
        wave = 1;
        enemiesSpawned = 0;
        enemiesPerWave = 7;
        score = 0;
        startTime = System.currentTimeMillis();
        elapsedSeconds = 0;
        paused = false;
        pausedAccumulatedTime = 0;
        pauseStartTime = 0;
    }

    public boolean canBuild(int gridX, int gridY) {

        if (gridX < 0 || gridY < 0 ||
                gridX >= COLS || gridY >= ROWS)
            return false;

        if (gridY >= 3 && gridY <= 5 &&
                gridX <= 6)
            return false;

        if (gridX >= 4 && gridX <= 6 &&
                gridY >= 4 && gridY <= 9)
            return false;

        if (gridY >= 7 && gridY <= 9 &&
                gridX >= 4 && gridX <= 11)
            return false;

        if (gridX >= 9 && gridX <= 11 &&
                gridY >= 3 && gridY <= 9)
            return false;

        if (gridY >= 2 && gridY <= 4 &&
                gridX >= 9 && gridX <= 15)
            return false;

        if (gridX >= 13 && gridX <= 15 &&
                gridY >= 2 && gridY <= 9)
            return false;

        if (gridY >= 7 && gridY <= 9 &&
                gridX >= 13 && gridX <= 21)
            return false;

        if (gridX >= 19 && gridX <= 21 &&
                gridY >= 4 && gridY <= 9)
            return false;

        if (gridY >= 4 && gridY <= 6 &&
                gridX >= 19)
            return false;

        if (gridX <= 3 && gridY <= 3)
            return false;

        if (gridX >= 21 && gridY <= 3)
            return false;

        if (gridX <= 3 && gridY >= 10)
            return false;

        if (gridX >= 21 && gridY >= 10)
            return false;

        for (Tower tower : towers) {

            if (tower.gridX == gridX &&
                    tower.gridY == gridY)
                return false;
        }

        return true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (paused) {

            Point p = e.getPoint();

            if (resumeButton.contains(p)) {
                paused = false;
            }

            if (restartButton.contains(p)) {
                saveAllData();

                resetGame();
            }
            if (exitButton.contains(p)) {

                GameSave.guardar(this);
                saveAllData();
                running = false;
                music.stopMusic();
                new WaveMenu();
                music.playMusic("/assets/music/magodeoz.wav");
                javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
                System.out.println("Guardando en: " + new java.io.File("savegame.dat").getAbsolutePath());
                GameSave.guardar(this);
            }

            return;
        }

        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseX >= 200 && mouseX <= 264 &&
                mouseY >= HEIGHT - 90 && mouseY <= HEIGHT - 26) {
            selectedTower = 1;
            return;
        }

        if (mouseX >= 320 && mouseX <= 384 &&
                mouseY >= HEIGHT - 90 && mouseY <= HEIGHT - 26) {
            selectedTower = 2;
            return;
        }

        if (mouseX >= 440 && mouseX <= 504 &&
                mouseY >= HEIGHT - 90 && mouseY <= HEIGHT - 26) {
            selectedTower = 3;
            return;
        }

        if (mouseX >= 560 && mouseX <= 624 &&
                mouseY >= HEIGHT - 90 && mouseY <= HEIGHT - 26) {
            selectedTower = 4;
            return;
        }

        int gridX = e.getX() / TILE_SIZE;
        int gridY = e.getY() / TILE_SIZE;

        if (!canBuild(gridX, gridY))
            return;

        if (selectedTower == 1 && money >= 50) {
            towers.add(new BasicTower(gridX, gridY, tower1Img));
            money -= 50;
        }

        if (selectedTower == 2 && money >= 60) {
            towers.add(new SlowDownTower(gridX, gridY, tower4Img));
            money -= 60;
        }

        if (selectedTower == 3 && money >= 75) {
            towers.add(new MagicTower(gridX, gridY, tower3Img));
            money -= 75;
        }

        if (selectedTower == 4 && money >= 100) {
            towers.add(new SniperTower(gridX, gridY, tower2Img));
            money -= 100;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_1) {
            selectedTower = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            selectedTower = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_3) {
            selectedTower = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_4) {
            selectedTower = 4;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (!paused) {
                paused = true;
                pauseStartTime = System.currentTimeMillis();
            } else {
                paused = false;
                pausedAccumulatedTime += System.currentTimeMillis() - pauseStartTime;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mouseGridX = e.getX() / TILE_SIZE;
        mouseGridY = e.getY() / TILE_SIZE;
    }

    public void loadInfiniteRecord() {

        try {
            java.io.File file = new java.io.File("infinite.txt");

            if (!file.exists()) {
                for (int i = 0; i < 4; i++) {
                    infScore[i] = 0;
                    infWave[i] = 0;
                    infTime[i] = 0;
                    infLives[i] = 0;
                }
                return;
            }

            java.util.Scanner sc = new java.util.Scanner(file);
            sc.useLocale(java.util.Locale.US);
            for (int i = 0; i < 4; i++) {
                if (sc.hasNextInt())
                    infScore[i] = sc.nextInt();
                if (sc.hasNextInt())
                    infWave[i] = sc.nextInt();
                if (sc.hasNextInt())
                    infTime[i] = sc.nextInt();
                if (sc.hasNextDouble())
                    infLives[i] = sc.nextDouble();
            }
            sc.useLocale(java.util.Locale.US);

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}