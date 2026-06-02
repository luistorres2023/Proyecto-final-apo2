package usc.edu;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class SniperTower extends Tower {
    int megaShots = 0;
    public SniperTower(int gridX, int gridY, BufferedImage sprite) {
        super(gridX,gridY,350,60,2500, sprite,100);
        bulletSprite="/assets/sniperbullet.png";
        bulletSize=25;
        this.hp = 250;
        this.maxHp = 250;
        scoreMultiplier = 1.8;
        skillCost = 250;
    }
   @Override
public String getSkillName(){
    return "Apocalipsis";
}

@Override
public void useSkill(ArrayList<Enemy> enemies){

    megaShots = 4;
}

}