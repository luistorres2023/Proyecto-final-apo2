package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class SlowDownTowerTest {

    @Test
    void torreLentaExiste(){

        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        SlowDownTower tower=new SlowDownTower(1,1,img);

        assertNotNull(tower.getSkillName());
    }
}