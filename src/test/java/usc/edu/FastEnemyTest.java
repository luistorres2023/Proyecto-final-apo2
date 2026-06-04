package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class FastEnemyTest {

    @Test
    void enemigoRapido(){
        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        FastEnemy e=new FastEnemy(0,0,img);

        assertTrue(e.speed>0);
    }
}