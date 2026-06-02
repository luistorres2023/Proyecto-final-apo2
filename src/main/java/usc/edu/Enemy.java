package usc.edu;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy {

    boolean slowed = false;
    double x;
    double y;
    double baseSpeed;
    int hp;
    int maxHp;
    double speed;
    boolean alive = true;
    boolean finished = false;
    BufferedImage sprite;
    int pathIndex = 0;
    int towerDamage;
    int points = 10;
    int lastGridX = -1;
    int lastGridY = -1;
    double animTime = Math.random() * 10;
    double animOffset = 0;

    public Enemy(double x,double y,int hp,double speed,int towerDamage,BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.maxHp = hp;
        this.speed = speed;
        this.baseSpeed = speed;
        this.towerDamage = towerDamage;
        this.sprite = sprite;
    }

    public void update() {

    if (!alive)
        return;

    if (pathIndex >= GamePanel.pathPoints.size()) {

        finished = true; 
        return;
    }

    Point target = GamePanel.pathPoints.get(pathIndex);
    double targetX = target.x * 64;
    double targetY = target.y * 64;
    double dx = targetX - x;
    double dy = targetY - y;
    double distance = Math.sqrt(dx * dx + dy * dy);

    if (distance < 4) {

        pathIndex++;
        return;
    }

    dx /= distance;
    dy /= distance;

    x += dx * speed;
    y += dy * speed;

    damageTowers();
    animTime += 0.15;
}

    public void damageTowers() {

        int enemyGridX = (int)((x + 24) / 64);
        int enemyGridY = (int)((y + 24) / 64);

        if(enemyGridX == lastGridX && enemyGridY == lastGridY)
            return;

        lastGridX = enemyGridX;
        lastGridY = enemyGridY;

        for(Tower tower : GamePanel.instance.towers) {

            if(!tower.alive)
                continue;

            int dx = Math.abs(tower.gridX - enemyGridX);
            int dy = Math.abs(tower.gridY - enemyGridY);

            boolean adjacent =
                    (dx == 2 && dy == 0) ||
                    (dx == 0 && dy == 2);

            if(adjacent) {

                tower.hp -= towerDamage;

                if(tower.hp <= 0) {

                tower.hp = 0;
                tower.alive = false;

                 break;
               }
            }
        }
    }

    public void draw(Graphics2D g2) {

    if (!alive)
        return;

    int drawY = (int)y;

if(this instanceof FastEnemy){

    animOffset = Math.sin(animTime * 2.5) * 4;

}
else if(this instanceof TankEnemy){

    animOffset = Math.sin(animTime * 1.2) * 3;

}
else if(this instanceof Witch){

    animOffset = Math.sin(animTime * 1.5) * 6;

}
else if(this instanceof NIGROMANTE){

    animOffset = Math.sin(animTime) * 8;

}
else{

    animOffset = Math.sin(animTime * 1.8) * 3;
}

drawY += (int)animOffset;

if(this instanceof NIGROMANTE){

    int auraSize = 55 + (int)(Math.sin(animTime * 2) * 5);

    g2.setColor(new Color(0,180,0,90));

    g2.fillOval(
        (int)x - 4,
        drawY - 4,
        auraSize,
        auraSize
    );
}

g2.drawImage(
    sprite,
    (int)x,
    drawY,
    48,
    48,
    null
);

    if (slowed) {

        g2.setColor(new Color(120, 180, 255, 150));
        g2.fillRect((int)x, (int)y, 48, 48);
    }

    g2.setColor(Color.RED);
    g2.fillRect((int)x, (int)y - 10, 40, 5);
    g2.setColor(Color.GREEN);
    int hpWidth = (int)((hp / (double)maxHp) * 40);

    g2.fillRect((int)x, (int)y - 10, hpWidth, 5);
    }
}