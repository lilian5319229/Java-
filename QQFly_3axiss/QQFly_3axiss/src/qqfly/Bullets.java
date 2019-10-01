
package qqfly;

import QQfly2.ObjA;
import QQfly2.ObjB;
import QQfly3.Animation;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Bullets extends GameObj implements ObjA{
    BufferedImage[] image = new BufferedImage[3];

    
    public static enum BULLETWAY {
        UP,
        DOWN;
    }
    
    private QQFly game;
    private BULLETWAY bw;
    Animation anim;
    private GameController c;
    
    public Bullets(double x, double y, QQFly game, GameController c,BULLETWAY bw){  // Bullet Class
        super(x,y);
        this.game= game;
        this.bw = bw;
        this.c = c;
        GetImages ss = new GetImages(game.getSpriteSheet2());
        
        for(int i=0;i<3;i++)
             image[i] = ss.grabImage(1, 1, 14, 40);
        
        anim = new Animation(5,image[0],image[1],image[2]);
    }
    
    public void tick(){
        
        if (bw == BULLETWAY.UP) {
            y -= 10;
        } else {
            y += 10;
        }  
        
        for(int i = 0; i < game.ea.size(); i++){
            ObjA tempEnt = game.ea.get(i);
            if(!(game.ea.get(i).equals(this)))
              if(Physics.Collision(this, tempEnt)){
                  c.removeEntity(tempEnt);
                  c.removeEntity(this);
              }
        }
        
        anim.runAnimation();
    }
    public BULLETWAY getBulletWay(){
        return this.bw;
    }
    
     public Rectangle getBounds(){
         
         Rectangle obj = new Rectangle((int)x,(int)y,14,40);
         
        return obj;
    }
    
    public void render(Graphics g){
        anim.drawAnimation(g, x, y, 0);
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}