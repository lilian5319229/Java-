/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QQfly_Socket;

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

public class p1_server {

    public static final int PORT = 1234;   //port號碼 client與server 需相同

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
    ServerSocket p1_serverSocket = null;
    Player player1;

    public p1_server(Player player1, QQFly game) {
        try {
            System.out.println("server is on");
            p1_serverSocket = new ServerSocket(1234);    //建立serversocket
            this.player1 = player1;
            this.game = game;
        } catch (IOException ex) {
            Logger.getLogger(p1_server.class.getName()).log(Level.SEVERE, null, ex);
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
                    Socket p1_clntSock = p1_serverSocket.accept();
                    InputStreamReader in = new InputStreamReader(p1_clntSock.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String message = br.readLine();
                    
                    if (message != null) {
                        String[] tokens = message.split(",");
                        int count = 1;
                        for (String token : tokens) {
                            if (count == 1) {
                                player1.setPhone_x(Double.valueOf(token));
                            } else if (count == 2) {
                                if (token.equals("1")) {
                                    QQFly.p1Start = true;
                                }
                            } else if (count == 3) {
                                if (token.equals("1")) {
                                    QQFly.c.addEntity(new Bullets(player1.getX() + 7, player1.getY(), game, QQFly.c, Bullets.BULLETWAY.UP));
                                }
                            }
                            count++;
                        }
                    }
                  
                }
            } catch (IOException ex) {
                Logger.getLogger(p1_server.class.getName()).log(Level.SEVERE, null, ex);
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
