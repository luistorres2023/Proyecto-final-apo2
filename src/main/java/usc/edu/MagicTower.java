package usc.edu;
import java.awt.image.BufferedImage;
    public class MagicTower extends Tower{
        public MagicTower(int gridX,int gridY,BufferedImage sprite){
        super(gridX,gridY,250,35,750,sprite);
        bulletSprite="/assets/bullet.png";
        bulletSize=35;
        this.hp = 175;
        this.maxHp = 175;
        scoreMultiplier = 1.5;
    }
}