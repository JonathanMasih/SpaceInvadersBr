import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
/**
 * This GUI will be set up to show one player attemoting to shoot aliends and
 * other ships
 * while avoiding their lasers as well.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class DoublePlayer extends Thread implements KeyListener  {
    protected final static int GAME_PANEL_WIDTH = 800;
    protected final static int GAME_PANEL_HEIGHT = 850;
    // amount to the move player on each key press
    protected static final int MOVE_BY = 5;
    // for repaint thread
    private static final int DELAY_TIME = 33;
    private JFrame frame;
    private Image backgroundImage;
    private Clip clip;
    protected static JPanel gamePanel;
    private Player player1;
    private Player2 player2;
    private ArrayList<PlayerBullet> bulletList;
    private ArrayList<Shield> shieldList;
    private ArrayList<Alien> alienList;

    private boolean keyPress_A = false;
    private boolean keyPress_D = false;
    private boolean keyPress_LEFT = false;
    private boolean keyPress_RIGHT = false;

    Point currentPosPlayer1;

    public DoublePlayer(JFrame frame, Image img, Clip clip){
        this.frame = frame;
        this.backgroundImage = img;
        this.clip = clip;
        // Creates the players when the game starts
        this.player1 = new Player(new Point(350, Player.PLAYERYPOS));
        this.player2 = new Player2(new Point(350, Player2.PLAYER2YPOS));
        // creates the ArrayList<Bullet>
        this.bulletList = new ArrayList<PlayerBullet>();
        // Makes the Arraylist shields
        this.shieldList = new ArrayList<Shield>();
    }
    
     /**
     * The run method to set up the graphical user interface
     */
    @Override
    public void run(){
        // Clears the frame from the Main menu and buttons so we can
        // Implement single player mode components
        frame.getContentPane().removeAll();
        frame.setTitle("Welcome to Multiplayer!");
        frame.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setting the background of the frame
        JPanel backGroundPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);
                // draw the background
                g.drawImage(backgroundImage, 0, 0, frame.getWidth(), frame.getHeight(), this);
            }
        };
        backGroundPanel.setLayout(new BorderLayout());

        

        // Plays the background music
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        // making sheilds
        shieldList.add(new Shield(new Point(100, Shield.SHIELDPOS)));
        shieldList.add(new Shield(new Point(500, Shield.SHIELDPOS)));
        shieldList.add(new Shield(new Point(100, 150)));
        shieldList.add(new Shield(new Point(500, 150)));

        gamePanel = new JPanel() {
            @Override
            public void paintComponent (Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                // draws the border for the game
                g.drawRect(0, 0, 700, GAME_PANEL_HEIGHT);
                // draw the player
                player1.paint(g);
                player2.paint(g);
                // draws the shields
                int i = 0;
                while (i < shieldList.size()) {
                    Shield s = shieldList.get(i);
                    if (s.isSheildBroken()) {
                        bulletList.remove(i);
                    } else {
                        s.paint(g);
                        i++;
                    }
                }
 
                //draws Player 2 after checking to see if it was hit
                // This should be replicated for player 1
                for (int z = 0; z < bulletList.size(); z++) {
                    Point upperLeftBullet = bulletList.get(z).getUpperLeft();
                    Point upperLeftEnemy =  player2.getUpperLeft();
                    if (Collision.bulletOverlapsObject(upperLeftBullet.x, upperLeftBullet.y, Bullet.bulletWidth, Bullet.bulletHeight,
                    upperLeftEnemy.x, upperLeftEnemy.y, Player.PLAYERSIZE, Player.PLAYERSIZE)) {
                        player2.hitPlayer() ;
                        bulletList.get(z).bulletHit();
                        bulletList.remove(z);
                    }
                }
                if (player2.getPlayer2Lives() > 0) {
                    player2.paint(g);
                }
                
                // draws the bullets
                int k = 0;
                while (k < bulletList.size()) {
                    Bullet b = bulletList.get(k);
                    if (b.isOffPanel()) {
                        bulletList.remove(k);
                    } else {
                        b.paint(g);
                        k++;
                    }
                }

                // draws enemy the bullets
                int l = 0;
                while (l < EnemyPlayer.enemiesBulletsList.size()) {
                    Bullet b = EnemyPlayer.enemiesBulletsList.get(l);
                    if (b.isOffPanel()) {
                        EnemyPlayer.enemiesBulletsList.remove(l);
                    } else {
                        b.paint(g);
                        l++;
                    }
                }

            }
        };

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e) {
                    }
                    if(keyPress_A)
                        player1.translate(-MOVE_BY);
                    if(keyPress_D)
                        player1.translate(MOVE_BY);
                    if(keyPress_LEFT)
                        player2.translate(-MOVE_BY);
                    if(keyPress_RIGHT)
                        player2.translate(MOVE_BY);
                        //player2.rotate(gamePanel, true);
                    currentPosPlayer1 = player1.getPlayerCenter();
                    gamePanel.repaint();
                }
            }
        }.start();
        // sets the size of the game panel
        gamePanel.setPreferredSize(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT));
        gamePanel.setOpaque(false);
        backGroundPanel.add(gamePanel);

        frame.add(backGroundPanel);
        // Checks if any key is pressed
        frame.addKeyListener(this);
        // Sets the keyboard focus on this frame
        frame.setFocusable(true);
        frame.requestFocus();
        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentPosPlayer1 = player1.getPlayerCenter();
        Point currentPosPlayer2 = player2.getPlayer2Center();

        // Moves the player depending on which button is pressed
        if (e.getKeyCode() == KeyEvent.VK_A) {
            keyPress_A = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keyPress_D = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            PlayerBullet bullet = new PlayerBullet(gamePanel, currentPosPlayer1);
            bullet.start();
            bulletList.add(bullet);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keyPress_LEFT = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //keyPress_RIGHT = true;
            player2.rotate(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            player2.fireBullet(gamePanel);
        } else {
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            keyPress_A = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keyPress_D = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keyPress_LEFT = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyPress_RIGHT = false;
        } else {
            return;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}