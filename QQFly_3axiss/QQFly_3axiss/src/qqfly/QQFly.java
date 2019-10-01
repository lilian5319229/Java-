package qqfly;

import QQFly_Socket.p2_server;
import QQfly2.ObjA;
import QQfly2.ObjB;
import QQfly_Socket.p1_server;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import qqfly.Bullets.BULLETWAY;
import qqfly.Player.PLAYER;

public class QQFly extends Canvas implements Runnable {

    public static final int width = 350; //final代表不能被改值
    public static final int height = 450;
    public static final int scale = 2;
    public final String title = "Final Project";

    private boolean running = false;
    private Thread thread;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private BufferedImage player1Img = null;
    private BufferedImage player2Img = null;
    private BufferedImage bulletImg = null;
    private BufferedImage background = null;
    private BufferedImage enemyImg = null;

    public static boolean p1_is_shooting = false;

    private p1_server p1_Server = null;
    private p2_server p2_Server = null;


    private int enemy_count = 5;
    private int enemy_killed = 0;
    private int player1_killed_enemy = 0;
    private int player2_killed_enemy = 0;

    public int getPlayer2_killed_enemy() {
        return player2_killed_enemy;
    }

    public void setPlayer2_killed_enemy(int player2_killed_enemy) {
        this.player2_killed_enemy = player2_killed_enemy;
    }
    private static String Connect_IP = new String();
    private boolean Need_Input_IP = true;
    private int count = 0;

    private int sec = 60;
    private int time = 0;

    public static boolean p1Start = false;
    public static boolean p2Start = false;

    public static boolean isP1Start() {
        return p1Start;
    }

    public static void setP1Start(boolean p1Start) {
        QQFly.p1Start = p1Start;
    }

    public static boolean isP2Start() {
        return p2Start;
    }

    public static void setP2Start(boolean p2Start) {
        QQFly.p2Start = p2Start;
    }

    public int getPlayer1_killed_enemy() {
        return player1_killed_enemy;
    }

    public void setPlayer1_killed_enemy(int player1_killed_enemy) {
        this.player1_killed_enemy = player1_killed_enemy;
    }

    private Player player1;
    private Player player2;

    public static GameController c;
    private Menu menu;

    public LinkedList<ObjA> ea;
    public LinkedList<ObjB> eb;

    public static int Player1_Health = 100 * 2;
    public static int Player2_Health = 100 * 2;

    public static enum STATE {
        MENU,
        GAME
    };

    public static STATE state = STATE.MENU;

    public void init() throws IOException {

        requestFocus();
        LoadPictures loader = new LoadPictures();
        player1Img = loader.loadImage("player1.png");
        player2Img = loader.loadImage("player2.png");
        bulletImg = loader.loadImage("bullets.png");
        enemyImg = loader.loadImage("enemy.png");
        background = loader.loadImage("background.jpg");

        //addKeyListener(new KeyInput(this));

        c = new GameController(this);
        player1 = new Player(235, 870, this, c, PLAYER.P1);   //設定Player1 位置  
        player2 = new Player(235, 0, this, c, PLAYER.P2);
        p1_Server = new p1_server(player1, this);
        p2_Server = new p2_server(player2, this);

        menu = new Menu();

        ea = c.getEa();
        eb = c.getEb();

        //this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput());
       
        p2_Server.run();
        p1_Server.run();
        c.createEnemy(enemy_count);

    }

    public BufferedImage getPlayer1Img() {
        return player1Img;
    }

    public void setPlayer1Img(BufferedImage player1Img) {
        this.player1Img = player1Img;
    }

    public BufferedImage getPlayer2Img() {
        return player2Img;
    }

    public void setPlayer2Img(BufferedImage player2Img) {
        this.player2Img = player2Img;
    }

    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.exit(0);

    }

    public void run() {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(QQFly.class.getName()).log(Level.SEVERE, null, ex);
        }
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                try {
                    tick();
                } catch (IOException ex) {
                    Logger.getLogger(QQFly.class.getName()).log(Level.SEVERE, null, ex);
                }
                updates++;
                delta--;
                time++;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }
            count++;
        }
        stop();
    }

    private void tick() throws IOException {

        if (p1Start == p2Start && p1Start == true) 
            QQFly.state = QQFly.STATE.GAME;  
        if (state == STATE.GAME) {
            if (Player1_Health <= 0) {
                JOptionPane.showMessageDialog(this, "Game Over !", "Oops", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(this, "Player2 is the winner!  ", "Game over ", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            if (Player2_Health <= 0) {
                JOptionPane.showMessageDialog(this, "Game Over !", "Oops", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(this, "Player1 is the winner!  ", "Game over ", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            player1.tick(); //player
            player2.tick();

            c.tick(); // gamecontroller

            if (sec <= 0) {
                JOptionPane.showMessageDialog(this, "Time's up!! ", "Oops", JOptionPane.INFORMATION_MESSAGE);
                if (player1_killed_enemy > player2_killed_enemy) {
                    JOptionPane.showMessageDialog(this, "Player1 is the winner!  ", "Game over ", JOptionPane.INFORMATION_MESSAGE);
                } else if (player1_killed_enemy < player2_killed_enemy) {
                    JOptionPane.showMessageDialog(this, "Player2 is the winner!  ", "Game over ", JOptionPane.INFORMATION_MESSAGE);
                }
                System.exit(0);
            }
        }
        if (enemy_killed >= enemy_count) {
            enemy_count += 2;
            enemy_killed = 0;
            c.createEnemy(enemy_count);
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        g.drawImage(background, 0, 0, null);

        if (state == STATE.GAME) {
            player1.render(g);
            player2.render(g);
            c.render(g);

            g.setColor(Color.gray); //設定生命條部分
            g.fillRect(510, 860, 200, 50);
            g.fillRect(510, 0, 200, 50);

            g.setColor(Color.green);//設定生命條部分
            g.fillRect(510, 860, Player1_Health, 50);
            g.fillRect(510, 0, Player2_Health, 50);

            g.setColor(Color.white);//設定生命條部分
            g.drawRect(510, 860, 200, 50);
            g.drawRect(510, 0, 200, 50);

            Font fn1 = new Font("arial", Font.BOLD, 30);
            g.setColor(Color.RED);
            g.setFont(fn1);//字幕
            g.drawString("Player1 HP", 540, 850);//字幕
            g.drawString("Player2 HP", 540, 80);

            String player1_killed_enemys = Integer.toString(player1_killed_enemy); //取得射殺的個數
            g.drawString("Killed : " + player1_killed_enemys, 550, 810); //印出射殺個數
            String player2_killed_enemys = Integer.toString(player2_killed_enemy);
            g.drawString("Killed: " + player2_killed_enemys, 550, 120);

            String NowSec = Integer.toString(sec);
            Font fn2 = new Font("arial", Font.CENTER_BASELINE, 40);
            Font fn3 = new Font("arial", Font.CENTER_BASELINE, 50);
            g.setFont(fn2);
            if (sec >= 0) {
                if (time / 60 >= 1) {
                    time = 0;
                    sec--;
                }
                g.drawString("Time ", 570, 450);
                g.setFont(fn3);
                if (sec >= 10) {
                    g.drawString(NowSec, 590, 500);
                } else {
                    g.drawString(NowSec, 600, 500);
                }

            }

        } else if (state == STATE.MENU) {
            menu.render(g);

        }
        g.dispose();
        bs.show();

    }

    /*public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (state == STATE.GAME) {
            if (key == KeyEvent.VK_RIGHT) {
                player1.setVelX(5);
            } else if (key == KeyEvent.VK_LEFT) {
                player1.setVelX(-5);
            } else if (key == KeyEvent.VK_SPACE && !p1_is_shooting) {
                p1_is_shooting = true;
                c.addEntity(new Bullets(player1.getX(), player1.getY(), this, c, BULLETWAY.UP));

            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT) {
            player1.setVelX(0);
        } else if (key == KeyEvent.VK_LEFT) {
            player1.setVelX(0);
        } else if (key == KeyEvent.VK_SPACE) {

            p1_is_shooting = false;
        }
    }*/

    public static void main(String[] args) {

        QQFly game = new QQFly();

        game.setPreferredSize(new Dimension(width * scale, height * scale)); //800 , 1400
        game.setMaximumSize(new Dimension(width * scale, height * scale));//800 , 1400
        game.setMinimumSize(new Dimension(width * scale, height * scale));//800 ,1400

        JFrame frame = new JFrame(game.title);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public BufferedImage getSpirteSheet() {
        return player1Img;
    }

    public BufferedImage getSpriteSheet2() {
        return bulletImg;
    }

    public BufferedImage getEnemy() {
        return enemyImg;
    }

    public int getEnemy_count() {
        return enemy_count;
    }

    public void setEnemy_count(int enemy_count) {
        this.enemy_count = enemy_count;
    }

    public int getEnemy_killed() {
        return enemy_killed;
    }

    public void setEnemy_killed(int enemy_killed) {
        this.enemy_killed = enemy_killed;
    }

    public String getConnect_IP() {
        return Connect_IP;
    }

    public void setConnect_IP(String Connect_IP) {
        this.Connect_IP = Connect_IP;
    }
}
