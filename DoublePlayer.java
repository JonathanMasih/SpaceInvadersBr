import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
/**
 * This setup is for two players to shoot at each other
 * Unlike Single Player, these players move similar to the game Astroids
 *
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio, Tyler Streithorst
 * @version Spring 2022
 */
public class DoublePlayer extends Thread implements KeyListener, ActionListener  {
    protected final static int GAME_PANEL_WIDTH = 800;
    protected final static int GAME_PANEL_HEIGHT = 750;
    protected static boolean debugMode = true;
    // amount to the move player on each key press
    protected static final int MOVE_BY = 5;
    // for repaint thread
    private static final int DELAY_TIME = 33;
    private JFrame frame;
    private Image backgroundImage;
    private Clip clip;
    protected static JPanel gamePanel;
    protected JLabel scoresLabel;
    private MultiPlayer1 player1;
    private MultiPlayer2 player2;
    private ArrayList<PlayerBullet> bulletList;
    private ArrayList<Shield> shieldList;
    private ArrayList<Alien> alienList;

    private boolean keyPress_W = false;
    private boolean keyPress_A = false;
    private boolean keyPress_S = false;
    private boolean keyPress_D = false;
    private boolean keyPress_UP = false;
    private boolean keyPress_DOWN = false;
    private boolean keyPress_LEFT = false;
    private boolean keyPress_RIGHT = false;

    public DoublePlayer(JFrame frame, Image img, Clip clip){
        this.frame = frame;
        this.backgroundImage = img;
        this.clip = clip;
        // Creates the players when the game starts
        this.player1 = new MultiPlayer1(new Point(350, Player.PLAYERYPOS));
        this.player2 = new MultiPlayer2(new Point(350, MultiPlayer2.PLAYER2YPOS));
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
                //g = (Graphics2D)g;
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                // draws the border for the game
                g.drawRect(0, 0, 700, GAME_PANEL_HEIGHT);
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
                // The calculation uses BulletSpeed because that will calculate an encompassing square around the polygon
                for (int j = 0; j < MultiPlayer1.player1BulletsList.size(); j++) {
                    Point upperLeftBullet = MultiPlayer1.player1BulletsList.get(j).getUpperLeft();
                    Point P2UpperLeft =  player2.getUpperLeft();
                    
                    if (Collision.bulletOverlapsObject(upperLeftBullet.x, upperLeftBullet.y, 10, 10,
                    P2UpperLeft.x, P2UpperLeft.y, Player.PLAYERSIZE, Player.PLAYERSIZE)) {
                        player2.hitPlayer();
                        MultiPlayer1.player1BulletsList.get(j).bulletHit();
                        MultiPlayer1.player1BulletsList.remove(j);
                        scoresLabel.setText("Blue: " + player1.getPlayer1Lives() + "  Red: " + player2.getPlayer2Lives());
                    }
                }
                if (player2.getPlayer2Lives() > 0) {
                    player2.paint(g);
                }

                for (int k = 0; k < MultiPlayer2.player2BulletsList.size(); k++) {
                    Point upperLeftBullet = MultiPlayer2.player2BulletsList.get(k).getUpperLeft();
                    Point P1UpperLeft = player1.getUpperLeft();
                    
                    if (Collision.bulletOverlapsObject(upperLeftBullet.x, upperLeftBullet.y, 10, 10,
                    P1UpperLeft.x, P1UpperLeft.y, Player.PLAYERSIZE, Player.PLAYERSIZE)) {
                        player1.hitPlayer() ;
                        MultiPlayer2.player2BulletsList.get(k).bulletHit();
                        MultiPlayer2.player2BulletsList.remove(k);
                        scoresLabel.setText("Blue: " + player1.getPlayer1Lives() + "  Red: " + player2.getPlayer2Lives());
                    }
                }
                if (player1.getPlayer1Lives() > 0) {
                    player1.paint(g);
                }
                
                // draws the bullets
                int z = 0;
                while (z < MultiPlayer1.player1BulletsList.size()) {
                    MultiPlayerBullet b = MultiPlayer1.player1BulletsList.get(z);
                    if (b.isOffPanel()) {
                        MultiPlayer1.player1BulletsList.remove(z);
                    } else {
                        b.paint(g);
                        z++;
                    }
                }
                z = 0;
                while (z < MultiPlayer2.player2BulletsList.size()) {
                    MultiPlayerBullet b = MultiPlayer2.player2BulletsList.get(z);
                    if (b.isOffPanel()) {
                        MultiPlayer2.player2BulletsList.remove(z);
                    } else {
                        b.paint(g);
                        z++;
                    }
                }

                //if(debugMode) {
                    //g.setColor(Color.WHITE);
                    //g.drawString("Blue Bullets: " + MultiPlayer1.player1BulletsList.size() + "\nRed Bullets: " + MultiPlayer2.player2BulletsList.size(), 600, 760);
                //}
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
                    // Rotate Player 1
                    if(keyPress_A)
                        player1.rotate(false); //false for counterclockwise
                    if(keyPress_D)
                        player1.rotate(true); //true for clockwise
                    // Move Player 1
                    if(keyPress_W)
                        player1.modifySpeed(0.1);
                    else if (player1.getSpeed() > 0.1)
                        player1.modifySpeed(-0.05);
                    if(keyPress_S)
                        player1.modifySpeed(-0.1);
                    else if (player1.getSpeed() < -0.1)
                        player1.modifySpeed(0.05);
                    player1.translate(MOVE_BY);

                    // Rotate Player 2
                    if(keyPress_LEFT)
                        player2.rotate(false); //false for counterclockwise
                    if(keyPress_RIGHT)
                        player2.rotate(true); //true for clockwise
                    // Move Player 2
                    if(keyPress_UP)
                        player2.modifySpeed(0.1);
                    else if (!keyPress_UP && player2.getSpeed() > 0.1)
                        player2.modifySpeed(-0.05);
                    if(keyPress_DOWN)
                        player2.modifySpeed(-0.1);
                    else if (!keyPress_DOWN && player2.getSpeed() < -0.1)
                        player2.modifySpeed(0.05);
                    player2.translate(MOVE_BY);

                    // Finally, Repaint
                    gamePanel.repaint();
                }
            }
        }.start();
        // sets the size of the game panel
        gamePanel.setPreferredSize(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT));
        gamePanel.setOpaque(false);


        //scoreboards panel 

        JPanel scoreboardPanel= new JPanel(new BorderLayout());
        //scoreboardPanel.setLayout(new  BoxLayout( scoreboardPanel ,BoxLayout.Y_AXIS));
        JPanel centerPanelForScoreboardPanel = new JPanel();
        centerPanelForScoreboardPanel.setLayout(new  BoxLayout( centerPanelForScoreboardPanel ,BoxLayout.Y_AXIS));
        
        scoreboardPanel.setLayout(new BorderLayout());
        scoreboardPanel.setOpaque(false);
        scoreboardPanel.setPreferredSize(new Dimension(StartGame.FRAMEWIDTH - 
        GAME_PANEL_WIDTH -50, GAME_PANEL_HEIGHT));
        scoresLabel = new JLabel("Blue: " + player1.getPlayer1Lives() + "  Red: " + player2.getPlayer2Lives());
        scoresLabel.setForeground(Color.WHITE);
        centerPanelForScoreboardPanel.add(scoresLabel);
        JLabel controlsLabel = new JLabel("<html><br>Controls<br>Blue<br>W, A, S, D to move<br>SPACE to shoot<br><br>Red<br>Arrow Keys to move<br>ENTER to shoot</html>");
        controlsLabel.setForeground(Color.WHITE);
        centerPanelForScoreboardPanel.add(controlsLabel);
        centerPanelForScoreboardPanel.setOpaque(false);
        //button to start the game and restart game
        JButton currentButton = new JButton("Start Game");
        currentButton.addActionListener(this);
        centerPanelForScoreboardPanel.add( currentButton);
        scoreboardPanel.add( centerPanelForScoreboardPanel , BorderLayout.EAST);


        
        backGroundPanel.setLayout(new BorderLayout());
        backGroundPanel.add(gamePanel , BorderLayout.WEST);
        backGroundPanel.add(scoreboardPanel, BorderLayout.EAST);

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
        // Sets the booleans to true when a move key is pressed
        // Fires when the appropriate key is pressed
        if (e.getKeyCode() == KeyEvent.VK_W) {
            keyPress_W = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            keyPress_A = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            keyPress_S = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keyPress_D = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player1.fireBullet(gamePanel);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            keyPress_UP = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keyPress_DOWN = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keyPress_LEFT = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyPress_RIGHT = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            player2.fireBullet(gamePanel);
        } else {
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            keyPress_W = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            keyPress_A = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            keyPress_S = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keyPress_D = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            keyPress_UP = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keyPress_DOWN = false;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ill do this later
    }
}