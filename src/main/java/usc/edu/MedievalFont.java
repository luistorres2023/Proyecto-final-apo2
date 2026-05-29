package usc.edu;
import java.awt.Font;
import java.io.InputStream;

public class MedievalFont{
private static Font font;
static{
try{
InputStream is=MedievalFont.class.getResourceAsStream("/assets/fonts/UnifrakturCook-Bold.ttf");
font=Font.createFont(Font.TRUETYPE_FONT,is);
}catch(Exception e){
font=new Font("Serif",Font.BOLD,24);
}
}
public static Font getFont(float size){
return font.deriveFont(size);
}
}