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
    int cost;
    int hp;
    int maxHp;
    boolean alive = true;
    double animTime = Math.random() * 10;
    double scoreMultiplier = 1.0;
    double shootAnim = 0;
    long slowUntil = 0;
    public long necroCurseUntil = 0;
    public boolean necroCursed = false;
    public int skillCost;
    public double attackMultiplier = 1.0;
    public boolean cursed = false;
    public long cursedUntil = 0;
    long lastSkillUse = 0;
    long skillCooldown = 20000;
    boolean rapidFire = false;
    long rapidFireUntil = 0;

    public Tower(int gridX,int gridY,int range,int damage,long fireRate,BufferedImage sprite,int cost) {

    this.gridX = gridX;
    this.gridY = gridY;
    x = gridX * 64;
    y = gridY * 64;
    this.range = range;
    this.damage = damage;
    this.fireRate = fireRate;
    this.sprite = sprite;
    this.cost = cost;

    hp = 100;
    maxHp = 100;
}

    public void update(ArrayList<Enemy> enemies,ArrayList<Bullet> bullets) {

        if(System.currentTimeMillis() < necroCurseUntil){

            necroCursed = true;
        } else {

            necroCursed = false;
        }
        animTime += 0.07;
        if(shootAnim > 0)
    shootAnim -= 0.5;

        long currentTime =System.currentTimeMillis();
        long currentFireRate = fireRate;

if(rapidFire){
    currentFireRate /= 3;
}
if(rapidFire &&
   System.currentTimeMillis() > rapidFireUntil){

    rapidFire = false;
}


if(System.currentTimeMillis() < slowUntil){

    currentFireRate *= 1.4;
}

if(currentTime - lastShot < currentFireRate)
    return;;

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

           Bullet bullet =new Bullet(x,y,target,damage,bulletSprite,bulletSize,this);

if(this instanceof SniperTower sniper &&
   sniper.megaShots > 0){

    bullet.damage *= 4;
    bullet.explosive = true;

    sniper.megaShots--;
}

GamePanel.bulletsToAdd.add(bullet);
            shootAnim = 8;
            lastShot = currentTime;
        }

    }

   public void draw(Graphics2D g2) {
    if(!alive)
        return;
    int size = 64 + (int)(Math.sin(animTime) * 5) + (int)shootAnim;
    int offset = (64 - size) / 2;
    if(necroCursed){

    g2.setColor(new Color(180,0,255,90));
    g2.fillOval(gridX * 64 - 8,gridY * 64 - 8,80,80);
    g2.setColor(new Color(220,120,255,200));
    g2.drawOval(gridX * 64 - 8,gridY * 64 - 8,80,80);
}
if(System.currentTimeMillis() < cursedUntil){

    g2.setColor(new Color(150,0,255,90));
    g2.fillOval(x - 10,y - 10,84,84);

    g2.setColor(new Color(220,120,255,220));
    g2.drawOval(x - 10,y - 10,84,84);
}

g2.drawImage(sprite,x + offset,y + offset,size,size,null);
if(System.currentTimeMillis() < slowUntil){

    g2.setColor(new Color(180,0,255,60));
    g2.fillOval(x - 15,y - 15,94,94);
    g2.setColor(new Color(220,100,255,180));
    g2.drawOval(x - 15,y - 15,94,94);
}
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
public boolean canUseSkill(){

    return System.currentTimeMillis() - lastSkillUse >= skillCooldown;
}

public String getSkillName(){

    return "Arrow rain";
}

public void useSkill(ArrayList<Enemy> enemies){

}

}