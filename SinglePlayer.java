import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.sound.sampled.*;

/**
 * This GUI will be set up to show one player attemoting to shoot aliends and
 * other ships
 * while avoiding their lasers as well.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class SinglePlayer extends Thread implements KeyListener {
    private JFrame frame;
    private Image backgroundImage;
    protected final static int GAME_PANEL_WIDTH = 800;
    protected final static int GAME_PANEL_HEIGHT = 850;
    // amount to the move player on each key press
    public static final int MOVE_BY = 10;
    private Clip clip;
    private JPanel gamePanel;
    private Player player;
    private ArrayList<Bullet> bulletList;
    private ArrayList<Shield> shieldList;
    private ArrayList<Alien> alienList;

    public SinglePlayer(JFrame frame, Image img, Clip clip) {
        this.frame = frame;
        this.backgroundImage = img;
        this.clip = clip;
        // Creates a player when the game started
        this.player = new Player(new Point(350,Player.PLAYERYPOS));
        //creates the ArrayList<Bullet>
        this.bulletList = new ArrayList<Bullet>();
        // Makes the Arraylist shields
        this.shieldList = new ArrayList<Shield>();
        // Make
        this.alienList = new ArrayList<Alien>();
   
    }

    /**
     * The run method to set up the graphical user interface
     */
    @Override
    public void run() {
        // Clears the frame from the Main menu and buttons so we can
        // Implement single player mode components
        frame.getContentPane().removeAll();
        frame.setTitle("Welcome to Single Player!");
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
        
        //making sheilds 
        Shield sheild1 = new Shield(new Point(100,Shield.SHIELDPOS ));
        Shield sheild2 = new Shield(new Point(500,Shield.SHIELDPOS));
        shieldList.add(sheild1 );
        shieldList.add(sheild2 );

        //Making aliens
        Alien alien1 = new Alien(new Point(100,Alien.ALIENYPOS1));
        Alien alien12 = new Alien(new Point(200,Alien.ALIENYPOS1));
        Alien alien2 = new Alien(new Point(300,Alien.ALIENYPOS1));
        Alien alien3 = new Alien(new Point(400,Alien.ALIENYPOS1));
        Alien alien4 = new Alien(new Point(500,Alien.ALIENYPOS1));
        alienList.add( alien1);
        alienList.add(  alien12 );
        alienList.add(alien2);
        alienList.add( alien3 );
        alienList.add( alien4);
        alienList.add(alien4);

        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                // draws the border for the game
                g.drawRect(0, 0, 700, GAME_PANEL_HEIGHT);
                // draw the player
                player.paint(g);
              //draws the shields
              int i = 0; 
              while (i < shieldList.size()) {
                Shield s =  shieldList.get(i);
                if (s.isSheildBroken()) {
                    bulletList.remove(i );
                } else {
                    s.paint(g);
                    i++;
                }}
               //draws the aliens
               int j = 0;
               while (j <  alienList.size()) {
               Alien a = alienList.get(j);
                if (a.isAlienHit()) {
                    alienList.remove(j);
                } else {
                    a.paint(g);
                    j++; }}
                  //draws the bullets 
                int k= 0;
                while ( k < bulletList.size()) {
                    Bullet b = bulletList.get(k);
                    if (b.isOffPanel()) {
                        bulletList.remove(k);
                    } else {
                        b.paint(g);
                        k++;
                    }
                }

            }
        };
        // sets the size of the game panel
        gamePanel.setPreferredSize(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT));
        gamePanel.setOpaque(false);
        backGroundPanel.add(gamePanel);

        frame.add(backGroundPanel);
        // Checks if any key is pressed
        frame.addKeyListener(this);
        //Sets the keyboard focus on this frame
        frame.setFocusable(true);
        frame.requestFocus();  
        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Point currentPosPlayer1 = player.getPlayerCenter();

        // Moves the player depending on which button is pressed
        if (e.getKeyCode() == KeyEvent.VK_A && 
            currentPosPlayer1.x > (5 + Player.PLAYERSIZE / 2)) {
            player.translate(-MOVE_BY);
            currentPosPlayer1 = player.getPlayerCenter();
            // If the player is moving and wants to shoot
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                Bullet bullet = new Bullet(gamePanel, currentPosPlayer1);
                bullet.start();
                bulletList.add(bullet);
            } 
        } else if (e.getKeyCode() == KeyEvent.VK_D && 
                  currentPosPlayer1.x < GAME_PANEL_WIDTH - (2 * Player.PLAYERSIZE)) {
            player.translate(MOVE_BY);
            currentPosPlayer1 = player.getPlayerCenter();
            // If the player is moving and wants to shoot
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                Bullet bullet = new Bullet(gamePanel, currentPosPlayer1);
                bullet.start();
                bulletList.add(bullet);
            } 
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Bullet bullet = new Bullet(gamePanel, currentPosPlayer1);
            bullet.start();
            bulletList.add(bullet);
        } else {
            // any other key pressed. ignores it.
            return;
        }
        // trigger paint so we can see the player in its new location
        gamePanel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}