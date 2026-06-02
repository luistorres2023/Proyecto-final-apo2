package usc.edu;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BasicTower extends Tower {
    public BasicTower(int gridX, int gridY, BufferedImage sprite) {
        super(gridX, gridY,180,15,500, sprite,50);
        bulletSprite="/assets/basicbullet.png";
        bulletSize=30;
        this.hp = 100;
        this.maxHp = 100;
        scoreMultiplier = 1.0;
        skillCost = 75;
    }
    @Override
public void useSkill(ArrayList<Enemy> enemies){

    rapidFire = true;
    rapidFireUntil = System.currentTimeMillis() + 7000;
}
    
}