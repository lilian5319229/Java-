package qqfly;

import QQfly2.ObjA;
import QQfly2.ObjB;
import QQfly3.Animation;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import qqfly.Bullets.BULLETWAY;

public class Enemy extends GameObj implements ObjB {

    public static enum DIRECTION {
        LEFT,
        RIGHT;
    }

    Random r = new Random();
    private QQFly game;
    private GameController c;
    private DIRECTION d;
    private int range = 100;
    private int speed = 5;

    Animation anim;

    BufferedImage[] image = new BufferedImage[3];

    public Enemy(double x, double y, QQFly game, GameController c, DIRECTION d) {
        super(x, y);
        GetImages ss = new GetImages(game.getEnemy());
        this.c = c;
        this.game = game;
        this.d = d;

        for (int i = 0; i < 3; i++) {
            image[i] = ss.grabImage(1, 1, 40, 40);
        }

        anim = new Animation(5, image[0], image[1], image[2]);
    }

    public void tick() {
        speed = r.nextInt(5) + 2;
        int num = r.nextInt(100);
        if (d == DIRECTION.LEFT) {
            x += speed;
            if (x >= 510 - 40) {
                x = 470;
                y = 400 + r.nextInt(100);
                d = DIRECTION.RIGHT;
                x += 3;              
            }
            if (x <= 0) {
                x = 0;
                y = 400 - r.nextInt(100);
                x -= 3;
                d = DIRECTION.LEFT;
            }
        } else {
            x -= speed;
            if (x <= 0) {               
                x = 0;
                y = 400 - r.nextInt(100);
                x += 4;                
                d = DIRECTION.LEFT;
            }
            if (x >= 470) {
                x = 470;
                y = 400 + r.nextInt(100);
                x -= 2;
                d = DIRECTION.RIGHT;
            }

        }

for (int i = 0; i < game.ea.size(); i++) {
            ObjA tempEnt = game.ea.get(i);

            if (Physics.Collision(this, tempEnt)) {
                BULLETWAY bw;
                bw = tempEnt.getBulletWay();
                c.removeEntity(tempEnt);
                c.removeEntity(this);
                if (bw == BULLETWAY.UP) {
                    game.setEnemy_killed(game.getEnemy_killed() + 1);
                    int temp = game.getPlayer1_killed_enemy();
                    game.setPlayer1_killed_enemy(++temp);
                } else {
                    game.setEnemy_killed(game.getEnemy_killed() + 1);
                    int temp = game.getPlayer2_killed_enemy();
                    game.setPlayer2_killed_enemy(++temp);
                }
            }
        }
        anim.runAnimation();
    }

    public void render(Graphics g) {
        anim.drawAnimation(g, x, y, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 40, 40);
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
