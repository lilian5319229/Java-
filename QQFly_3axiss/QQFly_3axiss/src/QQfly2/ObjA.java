
package QQfly2;

import java.awt.Graphics;
import java.awt.Rectangle;
import qqfly.Bullets.BULLETWAY;


public interface ObjA {
    
    public void tick();
    public void render(Graphics g);
    public Rectangle getBounds();
    public BULLETWAY getBulletWay();
    
    public double getX();
    public double getY();
    
}
