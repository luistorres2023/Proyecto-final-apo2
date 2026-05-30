package usc.edu;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SlowDownTower extends Tower{

    public SlowDownTower(int gridX,int gridY,BufferedImage sprite){
    super(gridX,gridY,245,0,1200,sprite);
    bulletSprite="/assets/SlowDownbullet.png";
    bulletSize=30;
    this.hp = 150;
    this.maxHp = 150;
    scoreMultiplier = 1.2;
}

@Override
public void update(ArrayList<Enemy> enemies,ArrayList<Bullet> bullets){

    long currentTime=System.currentTimeMillis();

    if(currentTime-lastShot<fireRate)
    return;

    Enemy targetEnemy=null;

    double closestDistance=Double.MAX_VALUE;

   for(Enemy enemy:enemies){

   if(!enemy.alive || enemy.slowed)
   continue;
   double dx=enemy.x-x;
   double dy=enemy.y-y;
   double distance=Math.sqrt(dx*dx+dy*dy);

   if(distance<range&&distance<closestDistance){

    closestDistance=distance;
    targetEnemy=enemy;
    }
}

    if(targetEnemy!=null){
    Enemy target=targetEnemy;
    target.speed = target.baseSpeed * 0.2;
    target.slowed = true;

    new Thread(() -> {

    try{
        Thread.sleep(5000);
    }catch(Exception e){}

    target.speed = target.baseSpeed;
    target.slowed = false;

}).start();

    GamePanel.bulletsToAdd.add(new Bullet(x+32, y+32, target, damage, bulletSprite, bulletSize,this));
    lastShot=currentTime;
        }
    }
}