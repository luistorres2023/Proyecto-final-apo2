package usc.edu;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

public class Witch extends Enemy {
    private long curseEffectUntil = 0;
    private int healAmount = 35;     
    private long healCooldown = 3000; 
    private long lastHealTime;
    private long curseCooldown = 3000;
    private long lastCurseTime;
    private long healEffectUntil = 0;
    public Witch(double x, double y, BufferedImage sprite) {
        super(x, y, 180, 1.2, 50, sprite);
        points = 50;
        lastHealTime = System.currentTimeMillis();
        lastCurseTime = System.currentTimeMillis();
    }

   @Override
public void update() {

    super.update();

    if (!alive)
        return;

    long currentTime = System.currentTimeMillis();

    if (currentTime - lastHealTime >= healCooldown) {

        healNearbyEnemies();

        healEffectUntil =
            System.currentTimeMillis() + 800;

        lastHealTime = currentTime;
    }

    if (currentTime - lastCurseTime >= curseCooldown) {

        curseNearbyTowers();

        curseEffectUntil =
            System.currentTimeMillis() + 800;

        lastCurseTime = currentTime;
    }
}

    private void healNearbyEnemies(){

    for(Enemy enemy : GamePanel.instance.enemies){

        if(!enemy.alive)
            continue;

        double dx = enemy.x - x;
        double dy = enemy.y - y;

        double dist = Math.sqrt(dx*dx + dy*dy);

        if(dist <= 150){

            enemy.hp += healAmount;

            if(enemy.hp > enemy.maxHp)
                enemy.hp = enemy.maxHp;
        }
    }
}
private void curseNearbyTowers(){

    if(Math.random() > 0.30)
        return;

    for(Tower tower : GamePanel.instance.towers){

        if(!tower.alive)
            continue;

        double dx = tower.x - x;
        double dy = tower.y - y;

        double dist = Math.sqrt(dx*dx + dy*dy);

        if(dist <= 300){
            tower.slowUntil =System.currentTimeMillis() + 6000;
        }
    }
}
@Override
public void draw(Graphics2D g2){

    if(System.currentTimeMillis() < healEffectUntil){

        g2.setColor(new Color(0,255,0,40));
        g2.fillOval((int)x - 125,(int)y - 125,300,300);

        g2.setColor(new Color(0,255,0,180));
        g2.drawOval((int)x - 125,(int)y - 125,300,300);
    }
    if(System.currentTimeMillis() < curseEffectUntil){

    g2.setColor(new Color(180,0,255,40));
    g2.fillOval((int)x - 150,(int)y - 150,360,360);

    g2.setColor(new Color(180,0,255,180));
    g2.drawOval((int)x - 150,(int)y - 150,360,360);
}

    super.draw(g2);
}
}