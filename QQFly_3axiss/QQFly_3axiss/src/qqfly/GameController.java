
package qqfly;

import QQfly2.ObjA;
import QQfly2.ObjB;
import java.awt.Graphics;
import java.sql.Time;
import java.util.LinkedList;
import java.util.Random;
import qqfly.Enemy.DIRECTION;


public class GameController { //Controller Class
    private LinkedList<ObjA> ea = new LinkedList<ObjA>();
    private LinkedList<ObjB> eb = new LinkedList<ObjB>();

    ObjA enta;
    ObjB entb;
    long seed = 100;
    int range =50;
    int num = 0;
    Random random = new Random();
    
  QQFly game;
    
    public GameController(QQFly game){
        this.game = game;
    
    }
    
    public void createEnemy(int enemy_count){
        range = random.nextInt(50);
        num  = random.nextInt(100);
        for(int i=0;i<enemy_count;i++,num++){
           
            if(num%4 == 0){
                addEntity(new Enemy(0,400-random.nextInt(100),game,this, DIRECTION.LEFT));
               // range -=5;
            }
            else if (num % 4 == 1){
                addEntity(new Enemy(0,400+random.nextInt(100),game,this, DIRECTION.LEFT));
                //range -=5;
            }
            else if(num % 4 == 2){
                addEntity(new Enemy(470, 400 -random.nextInt(100), game, this, DIRECTION.RIGHT));
               // range -=5;
            }
            else if(num % 4 == 3){
                addEntity(new Enemy(470, 400 + random.nextInt(100), game, this, DIRECTION.RIGHT));
               // range -=5;
            }
          //  if(range==0)
              //  range =50;
        }
    }
   
    
    public void tick(){
        //A class
        for(int i=0;i<ea.size();i++){
            enta= ea.get(i);
            enta.tick();
        }
        //B class
         for(int i=0;i<eb.size();i++){
            entb= eb.get(i);
            entb.tick();
        }
    }
    
    public void render(Graphics g){
        
       //A class
       for(int i=0;i<ea.size();i++){
            enta = ea.get(i);
            enta.render(g);
        }
       
       //B class
       for(int i=0;i<eb.size();i++){
            entb = eb.get(i);
            entb.render(g);
        }
       
    }
   
    public void addEntity(ObjA block){
        ea.add(block);
    }
    
    public void removeEntity(ObjA block){
        ea.remove(block);
    }
    
     public void addEntity(ObjB block){
        eb.add(block);
    }
    
    public void removeEntity(ObjB block){
        eb.remove(block);
    }
    
    public LinkedList<ObjA> getEa() {
        return ea;
    }

    public void setEa(LinkedList<ObjA> ea) {
        this.ea = ea;
    }

    public LinkedList<ObjB> getEb() {
        return eb;
    }

    public void setEb(LinkedList<ObjB> eb) {
        this.eb = eb;
    }

    
}