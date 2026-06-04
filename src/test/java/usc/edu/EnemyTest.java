package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class EnemyTest {

    @Test
    void enemigoSeCrea(){
        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        Enemy e=new Enemy(0,0,100,2,10,img);

        assertEquals(100,e.hp);
    }

    @Test
    void enemigoRecibeDaño(){
        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        Enemy e=new Enemy(0,0,100,2,10,img);

        e.takeDamage(30,null);

        assertEquals(70,e.hp);
    }

    @Test
    void enemigoMuere(){
        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        Enemy e=new Enemy(0,0,50,2,10,img);

        e.takeDamage(100,null);

        assertFalse(e.alive);
    }
}