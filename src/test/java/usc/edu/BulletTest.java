package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class BulletTest {

    @Test
    void balaExiste(){

        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        Enemy e=new Enemy(0,0,100,2,10,img);
        Tower t=new BasicTower(1,1,img);

        Bullet b=new Bullet(0,0,e,20,"/assets/bullet.png",10,t);

        assertNotNull(b);
    }
}