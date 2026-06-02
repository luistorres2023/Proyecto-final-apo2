package usc.edu;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Bullet{

    double x;
    double y;
    private Image bulletImage;
    Enemy target;
    boolean active=true;
    double speed=7;
    int damage;
    int size;
    Tower owner;
    public Bullet(double x,double y,Enemy target,int damage,String imagePath, int size,Tower owner ){

        this.x=x;
        this.y=y;
        this.target=target;
        this.damage=damage;
        this.size=size;
        this.owner=owner;

        bulletImage=new ImageIcon(getClass().getResource(imagePath)).getImage();
        }

    public void update(){

    if(!active)
    return;

    if(!target.alive){

    active=false;
    return;
}

    double dx=target.x-x;
    double dy=target.y-y;
    double distance=Math.sqrt(dx*dx+dy*dy);

    if(distance < 5){

    target.takeDamage(damage);

    active = false;
    return;
}

    dx/=distance;
    dy/=distance;
    x+=dx*speed;
    y+=dy*speed;
}

    public void draw(Graphics2D g2){
    if(!active)return;
    double dx=target.x-x;
    double dy=target.y-y;
    double angle=Math.atan2(dy,dx);
    AffineTransform old=g2.getTransform();
    g2.translate((int)x,(int)y);
    g2.rotate(angle);
    g2.drawImage(bulletImage,-size/2,-size/2,size,size,null);
    g2.setTransform(old);
    }
}