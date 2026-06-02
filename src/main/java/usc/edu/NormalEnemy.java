package usc.edu;
import java.awt.*;
import java.awt.image.BufferedImage;

public class NormalEnemy extends Enemy {
    private boolean enraged = false;
    public NormalEnemy(double x, double y, BufferedImage sprite) {
        super(x, y, 130, 1.5,15, sprite);
    points = 15;
    }
    @Override
public void update(){

    super.update();

    if(!alive)
        return;

    if(!enraged && hp <= maxHp * 0.30){

        speed = baseSpeed * 1.5;
        enraged = true;
    }
}
}