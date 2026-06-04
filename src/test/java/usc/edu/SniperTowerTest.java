package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class SniperTowerTest {

    @Test
    void sniperExiste(){

        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        SniperTower tower=new SniperTower(1,1,img);

        assertNotNull(tower.getSkillName());
    }
}