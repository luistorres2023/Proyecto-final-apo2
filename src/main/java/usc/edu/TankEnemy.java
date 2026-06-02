package usc.edu;
import java.awt.image.BufferedImage;

public class TankEnemy extends Enemy {
    private boolean armored;
    public TankEnemy(double x, double y, BufferedImage sprite) {
        super(x, y, 140, 1.1,25, sprite);
        points = 25;
        armored = Math.random() < 0.30;
    }
   @Override
public void takeDamage(int damage, Tower tower) {

    if(armored){
        damage *= 0.6;
    }

    super.takeDamage(damage, tower);
}
public boolean isArmored(){
    return armored;
}
}