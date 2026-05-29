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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
    
    final int WIDTH = 960;
    final int HEIGHT = 720;
    final int TILE_SIZE = 64;
    final int ROWS = HEIGHT / TILE_SIZE;
    final int COLS = WIDTH / TILE_SIZE;
    public static GamePanel instance;
    int[][] map = new int[ROWS][COLS];

    BufferedImage grassImg;
    BufferedImage pathImg;
    BufferedImage LavaImg;
    BufferedImage tower1Img;
    BufferedImage tower2Img;
    BufferedImage tower3Img;
    BufferedImage tower4Img;
    BufferedImage basicIcon;
    BufferedImage slowIcon;
    BufferedImage magicIcon;
    BufferedImage sniperIcon;
    BufferedImage moneyIcon;
    BufferedImage heartIcon;
    BufferedImage waveIcon;
    BufferedImage normalenemyImg;
    BufferedImage fastEnemyImg;
    BufferedImage tankEnemyImg;
    BufferedImage witchImg;
    BufferedImage bulletImg;
    BufferedImage NIGROMANTEImg;

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

    long lastSpawn = 0;

    int enemiesSpawned = 0;
    int enemiesPerWave = 7;
    int selectedTower = 1;
    int mouseGridX;
    int mouseGridY;

    Rectangle resumeButton = new Rectangle(WIDTH / 2 - 100, 220, 200, 50);
    Rectangle restartButton = new Rectangle(WIDTH / 2 - 100, 300, 200, 50);
    Rectangle exitButton = new Rectangle(WIDTH / 2 - 100, 380, 200, 50);
    

    public static ArrayList<Point> pathPoints =
            new ArrayList<>();
    public GamePanel(int maxWave) {
        instance = this;
        this.maxWave = maxWave;
        music.playMusic("/assets/music/background.wav");
    setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setPreferredSize(
 new Dimension(WIDTH, HEIGHT));

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        createMap();
        createPath();
        createLavaBlocks();
        loadImages();;
        

        gameThread = new Thread(this);
        gameThread.start();
    }
    public void createLavaBlocks() {

    int totalBlocks = (int)(Math.random() * 11) + 10;
    int count = 0;

    while(count < totalBlocks) {

        int row = (int)(Math.random() * ROWS);
        int col = (int)(Math.random() * COLS);

        if(map[row][col] == 0) {

            map[row][col] = 2;
            count++;
        }
    }
}

    public void createPath() {

        pathPoints.clear();
        pathPoints.add(new Point(0, 4));
        pathPoints.add(new Point(0, 1));
        pathPoints.add(new Point(10, 1));
        pathPoints.add(new Point(10, 7));
        pathPoints.add(new Point(14, 7));
    }

    public void createMap() {

        for (int row = 1; row <= 4; row++) {
            map[row][0] = 1;
        }
        for (int col = 0; col <= 10; col++) {
            map[1][col] = 1;
        }
        for (int row = 1; row <= 7; row++) {
            map[row][10] = 1;
        }
        for (int col = 10; col <= 14; col++) {
            map[7][col] = 1;
        }
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

    } catch(Exception e) {

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

    public void updateGame() {

        spawnEnemies();

        for (Enemy enemy : enemies) {

            enemy.update();

            if (enemy.finished) {

    enemy.alive = false;

    if (enemy instanceof TankEnemy ) {

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

        for (Tower tower : towers) {

            tower.update(enemies, bullets);
        }

        for (Bullet bullet : bullets) {

            bullet.update();
        }

        for (Bullet bullet : bullets) {

            if (!bullet.active)
                continue;

            for (Enemy enemy : enemies) {

                if (!enemy.alive)
                    continue;

                double dx = bullet.x - enemy.x;
                double dy = bullet.y - enemy.y;
                double distance =
                        Math.sqrt(dx * dx + dy * dy);
                if (distance < 20) {

                    enemy.hp -= bullet.damage;

                    bullet.active = false;

                    if (enemy.hp <= 0) {

    enemy.alive = false;

    if (enemy instanceof TankEnemy) {
            money += 15;
    }else if (enemy instanceof Witch) {
        money += 30;

    } else if (enemy instanceof NIGROMANTE) {
        money += 250;
    } else {

        money += 5;
    }
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


        bullets.addAll(bulletsToAdd);
        bulletsToAdd.clear();

        if(enemiesSpawned >= enemiesPerWave && enemies.isEmpty()) {

        wave++;

        if(maxWave != -1 && wave > maxWave) {

        JOptionPane.showMessageDialog(this,"VICTORY!");
        new MainMenu();
        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
        return;
    }

        enemiesPerWave += 4;
        enemiesSpawned = 0;
        }

        if(lives <= 0) {

        JOptionPane.showMessageDialog(this,"GAME OVER");
        System.exit(0);
        }

        if (lives <= 0) {

            JOptionPane.showMessageDialog(this, "GAME OVER");

            System.exit(0);
        }
    }

    public void spawnEnemies() {
    long currentTime = System.currentTimeMillis();

    if(enemiesSpawned >= enemiesPerWave) return;

    if(currentTime - lastSpawn > 1000) {

        boolean spawnWitch = false;
        boolean spawnNigromante = false;
        int witchesPerWave = 0;

        if(wave % 2 == 0)
            witchesPerWave = wave / 2;

        if(witchesPerWave > 0) {

            if(enemiesSpawned == 0) {
                spawnWitch = true;
            }
            else if(witchesPerWave >= 2 && enemiesSpawned == enemiesPerWave - 1) {
                spawnWitch = true;
            }
            else if(witchesPerWave > 2) {
                int extraWitches = witchesPerWave - 2;
                double chance = (double)extraWitches / (enemiesPerWave - 2);

                if(Math.random() < chance)
                    spawnWitch = true;
            }
        }

        if(wave % 10 == 0 && enemiesSpawned == 1)
            spawnNigromante = true;

        if(spawnWitch) {
            enemiesToAdd.add(new Witch(0,4 * TILE_SIZE,witchImg));
        }
        else if(spawnNigromante) {
            enemiesToAdd.add(new NIGROMANTE(0,4 * TILE_SIZE,NIGROMANTEImg));
        }
        else if(wave % 3 == 0) {
            enemiesToAdd.add(new TankEnemy(0,4 * TILE_SIZE,tankEnemyImg));
        }
        else {
            enemiesToAdd.add(new FastEnemy(0,4 * TILE_SIZE,fastEnemyImg));

            if(Math.random() < 0.5) {
                enemiesToAdd.add(new NormalEnemy(0,4 * TILE_SIZE,normalenemyImg));
            }
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
    }
}

    public void drawMap(Graphics2D g2) {

    for(int row = 0; row < ROWS; row++) {

        for(int col = 0; col < COLS; col++) {

            int x = col * TILE_SIZE;
            int y = row * TILE_SIZE;

            if(map[row][col] == 0) {

                g2.drawImage(grassImg,x,y,TILE_SIZE,TILE_SIZE,null);

            } else if(map[row][col] == 1) {

                g2.drawImage(pathImg,x,y,TILE_SIZE,TILE_SIZE,null);

            } else {

                g2.drawImage(LavaImg,x,y,TILE_SIZE,TILE_SIZE,null);
            }
        }
    }g2.setColor(Color.WHITE);
}

    public void drawUI(Graphics2D g2) {

    g2.setFont(new Font("Arial", Font.BOLD, 22));
    g2.setColor(Color.WHITE);
    g2.drawImage(moneyIcon, WIDTH - 280, 25, 32, 32, null);
    g2.drawString("" + money, WIDTH - 235, 50);
    g2.drawImage(heartIcon, WIDTH - 180, 25, 32, 32, null);
    g2.drawString("" + lives, WIDTH - 135, 50);
    g2.drawImage(waveIcon, WIDTH - 90, 25, 32, 32, null);
    g2.drawString("" + wave, WIDTH - 45, 50);
    g2.setColor(new Color(40,40,40));
    g2.fillRect(0, HEIGHT - 100, WIDTH, 100);
    g2.setColor(Color.WHITE);
    g2.drawString("SELEC. TORRE",20,HEIGHT - 60);
    g2.drawImage(basicIcon,200,HEIGHT - 90,64,64,null);
    g2.drawImage(slowIcon,320,HEIGHT - 90,64,64,null);
    g2.drawImage(magicIcon,440,HEIGHT - 90,64,64,null);
    g2.drawImage(sniperIcon,560,HEIGHT - 90,64,64,null);
    g2.setColor(Color.YELLOW);

    if(selectedTower == 1) {
        g2.drawRect(200,HEIGHT - 90,64,64);
        g2.drawRect(199,HEIGHT - 91,66,66);
    }
    if(selectedTower == 2) {
        g2.drawRect(320,HEIGHT - 90,64,64);
        g2.drawRect(319,HEIGHT - 91,66,66);
    }
    if(selectedTower == 3) {
        g2.drawRect(440,HEIGHT - 90,64,64);
        g2.drawRect(439,HEIGHT - 91,66,66);
    }

    if(selectedTower == 4) {
        g2.drawRect(560,HEIGHT - 90,64,64);
        g2.drawRect(559,HEIGHT - 91,66,66);
    }

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial",Font.BOLD,16));
    g2.drawString("$50",210,HEIGHT - 10);
    g2.drawString("$60",330,HEIGHT - 10);
    g2.drawString("$75",450,HEIGHT - 10);
    g2.drawString("$100",565,HEIGHT - 10);
}

    public void drawPauseMenu(Graphics2D g2) {

        g2.setColor(new Color(0,0,0,180));
        g2.fillRect(0,0,WIDTH,HEIGHT);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.setColor(Color.WHITE);
        g2.drawString("PAUSED", WIDTH / 2 - 100, 150);
        drawButton(g2, resumeButton, "Resume");
        drawButton(g2, restartButton, "Restart");
        drawButton(g2, exitButton, "Exit");
    }

    public void drawButton( Graphics2D g2, Rectangle rect, String text) {
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
    createLavaBlocks();

    money = 200;
    lives = 10;
    wave = 1;
    enemiesSpawned = 0;
    enemiesPerWave = 7;
    paused = false;
}

    public boolean canBuild(int gridX, int gridY) {

        if (gridX < 0 || gridY < 0 || gridX >= COLS || gridY >= ROWS)
            return false;
        if(map[gridY][gridX] == 1 || map[gridY][gridX] == 2)
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
            resetGame();
        }

        if (exitButton.contains(p)) {
            new MainMenu();
                javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
        }

        return;
    }

    int mouseX = e.getX();
    int mouseY = e.getY();


    if(mouseX >= 200 && mouseX <= 264 &&
       mouseY >= HEIGHT - 90 && mouseY <= HEIGHT - 26) {

        selectedTower = 1;
        return;
    }

    if(mouseX >= 320 && mouseX <= 384 &&
       mouseY >= HEIGHT - 90 && mouseY <= HEIGHT - 26) {

        selectedTower = 2;
        return;
    }

    if(mouseX >= 440 && mouseX <= 504 &&
       mouseY >= HEIGHT - 90 && mouseY <= HEIGHT - 26) {

        selectedTower = 3;
        return;
    }

    if(mouseX >= 560 && mouseX <= 624 &&
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
            paused = !paused;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mouseGridX = e.getX() / TILE_SIZE;
        mouseGridY = e.getY() / TILE_SIZE;
    }

    @Override public void mouseDragged(MouseEvent e){}
    @Override public void mouseClicked(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override public void keyReleased(KeyEvent e){}
    @Override public void keyTyped(KeyEvent e){}
}