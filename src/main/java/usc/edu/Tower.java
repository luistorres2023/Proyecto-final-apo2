package usc.edu;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tower {
    int gridX;
    int gridY;
    int x;
    int y;
    int range;
    int damage;
    String bulletSprite="/assets/bullet.png";
    int bulletSize=20;
    long fireRate;
    long lastShot = 0;
    BufferedImage sprite;
    int hp;
    int maxHp;
    boolean alive = true;
    double animTime = Math.random() * 10;
    double scoreMultiplier = 1.0;
    double shootAnim = 0;

    public Tower(int gridX,int gridY,int range,int damage,long fireRate,BufferedImage sprite) {

        this.gridX = gridX;
        this.gridY = gridY;
        x = gridX * 64;
        y = gridY * 64;
        this.range = range;
        this.damage = damage;
        this.fireRate = fireRate;
        this.sprite = sprite;
        this.hp = 100;
        this.maxHp = 100;
    }

    public void update(ArrayList<Enemy> enemies,ArrayList<Bullet> bullets) {

        animTime += 0.07;
        if(shootAnim > 0)
    shootAnim -= 0.5;

        long currentTime =System.currentTimeMillis();

        if (currentTime - lastShot < fireRate)
            return;

        Enemy target = null;

        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : enemies) {

            if (!enemy.alive)
                continue;

            double dx = enemy.x - x;
            double dy = enemy.y - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < range && distance < closestDistance) {

                closestDistance = distance;

                target = enemy;
            }

        }

        if (target != null) {

            GamePanel.bulletsToAdd.add(new Bullet(x,y,target,damage,bulletSprite,bulletSize,this));
        
            shootAnim = 8;
            lastShot = currentTime;
        }

    }

   public void draw(Graphics2D g2) {
    if(!alive)
        return;
    int size = 64
         + (int)(Math.sin(animTime) * 5)
        + (int)shootAnim;

int offset = (64 - size) / 2;

g2.drawImage(
    sprite,
    x + offset,
    y + offset,
    size,
    size,
    null
);
    g2.setColor(Color.RED);
    g2.fillRect(x + 12,y - 10,40,5);
    g2.setColor(Color.GREEN);
    int hpWidth = (int)((hp/(double)maxHp)*40);
    g2.fillRect(x + 12,y - 10,hpWidth,5);
}
public double getScoreMultiplier() {
    return scoreMultiplier;
}

public void setScoreMultiplier(double scoreMultiplier) {
    this.scoreMultiplier = scoreMultiplier;
}

}