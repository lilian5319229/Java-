/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QQFly_Socket;

/**
 *
 * @author user
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import qqfly.Bullets;
import qqfly.GameController;
import qqfly.Player;
import qqfly.QQFly;

public class p2_server {

    public static final int PORT = 1235;   //port號碼 client與server 需相同

    private double x = 0;
    private boolean ifBullet = false;
    private boolean ifStart = false;
    private GameController c = null;
    Socket client = null;
    private QQFly game;
    private double phone_x = 0;

    public double getPhone_x() {
        return phone_x;
    }

    public void setPhone_x(double phone_x) {
        this.phone_x = phone_x;
    }

    public boolean isIfBullet() {
        return ifBullet;
    }

    public void setIfBullet(boolean ifBullet) {
        this.ifBullet = ifBullet;
    }
    ServerSocket serverSocket = null;
    Player player;

    public p2_server(Player player1, QQFly game) {
        try {
            System.out.println("server is on");
            serverSocket = new ServerSocket(PORT);    //建立serversocket
            player = player1;
            this.game = game;
        } catch (IOException ex) {
            Logger.getLogger(p2_server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() throws IOException {
        new HandlerThread(client);
    }

    private class HandlerThread implements Runnable {

        private Socket socket;
        private DataInputStream input = null;
        private DataOutputStream output = null;

        public HandlerThread(Socket client) {
            new Thread(this).start();
        }

        public void run() {
            try {
                while (true) {                  
                    Socket clntSock = serverSocket.accept();
                    InputStreamReader in = new InputStreamReader(clntSock.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String message = br.readLine();
                    
                    if (message != null) {
                        String[] tokens = message.split(",");
                        int count = 1;
                        for (String token : tokens) {
                            if (count == 1) {
                                player.setPhone_x(Double.valueOf(token));
                            } else if (count == 2) {
                                if (token.equals("1")) {
                                    QQFly.p2Start = true;
                                }
                            } else if (count == 3) {
                                if (token.equals("1")) {
                                    QQFly.c.addEntity(new Bullets(player.getX() + 7, 0, game, QQFly.c, Bullets.BULLETWAY.DOWN));
                                }
                            }
                            count++;
                        }
                        
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(p2_server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public double getX() {
        return x;
    }

    public Boolean getIfBullet() {
        return ifBullet;
    }
}
