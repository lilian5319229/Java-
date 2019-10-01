package qqfly;

import QQfly2.ObjA;
import QQfly2.ObjB;
import QQfly2.ObjC;
import QQfly3.Animation;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObj implements ObjC {

    public static enum PLAYER {
        P1,
        P2;
    }

    private double velX = 0;
    private double velY = 0;
    private int kill_enemy = 0;
    Animation anim;
    private PLAYER p;
    private double phone_x;

    public double getPhone_x() {
        return phone_x;
    }

    public void setPhone_x(double phone_x) {
        this.phone_x = phone_x;
    }

    public PLAYER getP() {
        return p;
    }

    public void setP(PLAYER p) {
        this.p = p;
    }
    private GetImages ss = null;

    QQFly game;
    GameController c;

    private BufferedImage[] player = new BufferedImage[3];

    public Player(double x, double y, QQFly game, GameController c, PLAYER p) {
        super(x, y);
        if (p == PLAYER.P1) {
            ss = new GetImages(game.getPlayer1Img());
        } else {
            ss = new GetImages(game.getPlayer2Img());
        }
        this.game = game;
        this.c = c;
        this.p = p;

        for (int i = 0; i < 3; i++) {
            player[i] = ss.grabImage(1, 1, 40, 40);
        }
        anim = new Animation(5, player[0], player[1], player[2]);
    }

    public void tick() {
        if(phone_x >= 2)
            x -= 5;
        else if(phone_x <= -2)
            x += 5;
        
        //x += velX;
        //y += velY;

        //設定不超過邊界    //width =  800 , height =  1400
        if (x <= 0) {
            x = 0;
        }
        if (x >= 510 - 40) {
            x = 510 - 40;
        }

        for (int i = 0; i < game.ea.size(); i++) {
            ObjA tempEnt = game.ea.get(i);
            if (Physics.Collision(this, tempEnt)) {
                if (tempEnt.getY() >= 820) {
                    c.removeEntity(tempEnt);
                    QQFly.Player1_Health -= 10;;
                } else if (tempEnt.getY() <= 50) {
                    c.removeEntity(tempEnt);
                    QQFly.Player2_Health -= 10;;
                }
            }
        }

        anim.runAnimation();
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 40, 40);
    }

    public void render(Graphics g) {
        anim.drawAnimation(g, x, y, 0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if (p == PLAYER.P1) {
            this.x = x;
        } else {
            if (235 - x >= 0) {
                this.x = x + 2 * (235 - x);
            } else {
                this.x = x - 2 * (x - 235);
            }
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }
}
