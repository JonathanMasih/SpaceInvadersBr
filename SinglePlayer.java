import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.sound.sampled.*;

/**
 * This GUI will be set up to show one player attemoting to shoot aliends and
 * other ships
 * while avoiding their lasers as well.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class SinglePlayer extends Thread implements KeyListener , ActionListener {
    protected final static int GAME_PANEL_WIDTH = 800;
    protected final static int GAME_PANEL_HEIGHT = 850;
    // amount to the move player on each key press
    protected static final int MOVE_BY = 15;
    // for repaint thread
    private static final int DELAY_TIME = 33;
    private JFrame frame;
    private Image backgroundImage;
    private Clip clip;
    protected static JPanel gamePanel;
    private Player player;
    private ArrayList<PlayerBullet> bulletList;
    private ArrayList<Shield> shieldList;
    private ArrayList<Alien> alienList;
    private ArrayList<EnemyPlayer> enemyList;

    //current point of the player
    private static int playerPoints = 0;
    //Jlabel for points
    private  JLabel playerPointLabel;
    //Counter to slow down the shooting and stop spaming of player
    private int shootCounter = 5;
    //Keeps track of if the game is over or not.
    private boolean gameOver;
    //button to start and restart the game
    private JButton currentButton;
    private boolean gameStarted;

    private JPanel scorePanel;

    private boolean keyPress_A = false;
    private boolean keyPress_D = false;

    public SinglePlayer(JFrame frame, Image img, Clip clip) {
        this.frame = frame;
        this.backgroundImage = img;
        this.clip = clip;
        this.gameOver = false;
        // Creates a player when the game started
        this.player = new Player(new Point(350, Player.PLAYERYPOS));
        // creates the ArrayList<Bullet>
        this.bulletList = new ArrayList<PlayerBullet>();
        // Makes the Arraylist shields
        this.shieldList = new ArrayList<Shield>();
        // Making array list of aliens
        this.alienList = new ArrayList<Alien>();
        // Creating array list of enemy players
        this.enemyList = new ArrayList<EnemyPlayer>();
        this.gameStarted = false;
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

        // making sheilds
        Shield sheild1 = new Shield(new Point(130, Shield.SHIELDPOS));
        Shield sheild2 = new Shield(new Point(540, Shield.SHIELDPOS));
        shieldList.add(sheild1);
        shieldList.add(sheild2);

        // Making aliens
        alienList.add(new Alien1(new Point(150, Alien.ALIENYPOS1)));
        alienList.add(new Alien1(new Point(350, Alien.ALIENYPOS1)));
        alienList.add (new Alien1(new Point(550, Alien.ALIENYPOS1)));

        alienList.add(new Alien2(new Point(150, Alien.ALIENYPOS2)));
        alienList.add(new Alien2(new Point(350, Alien.ALIENYPOS2)));
        alienList.add(new Alien2(new Point(550, Alien.ALIENYPOS2)));
       
        alienList.add(new Alien3(new Point(150, Alien.ALIENYPOS3)));
        alienList.add(new Alien3(new Point(350, Alien.ALIENYPOS3)));
        alienList.add(new Alien3(new Point(550, Alien.ALIENYPOS3)));


        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                // draws the border for the game
                g.drawRect(0, 0,  GAME_PANEL_WIDTH -2 , GAME_PANEL_HEIGHT);
                // draw the player
                player.paint(g);
                
             // if(gameOver == false && gameStarted == true){
                    //meaning player is dead
                if(player.getPlayerLives() == 0){
                      gameOver = true;
                    }
                // collision between the player and enemybullet
                int l = 0;
                while (l < EnemyPlayer.enemiesBulletsList.size()) {
                    EnemyBullet bullet = EnemyPlayer.enemiesBulletsList.get(l);
                    Point upperLeftBullet = bullet.getUpperLeft();
                    Point upperLeftPlayer = player.getPlayerUpperLeft();
                    if (Collision.bulletOverlapsObject(upperLeftBullet.x, upperLeftBullet.y,
                            Bullet.bulletWidth, Bullet.bulletHeight,
                            upperLeftPlayer.x, upperLeftPlayer.y,
                            Player.PLAYERSIZE, Player.PLAYERSIZE)) {
                        player.hitPlayer();
                        bullet.bulletHit();
                        EnemyPlayer.enemiesBulletsList.remove(l);
                    } else {
                        bullet.paint(g);
                        l++;
                    }
                }
                // draws the shields and checks the collsion
                int i = 0;
                while (i < shieldList.size()) {
                    Shield s = shieldList.get(i);
                    for (int  o = 0;  o  < EnemyPlayer.enemiesBulletsList.size();  o++) {
                        Point upperLeftEnemyBullet = EnemyPlayer.enemiesBulletsList.get(o).getUpperLeft();
                        Point upperLeftShield = s.getShieldUpperLeft();
                        if (Collision.bulletOverlapsObject(upperLeftEnemyBullet.x, upperLeftEnemyBullet.y,
                                Bullet.bulletWidth, Bullet.bulletHeight,
                                upperLeftShield.x, upperLeftShield.y,
                                Shield.SHIELDSIZEW, Shield.SHIELDSIZEL)) {
                            s.hitSheild();
                            EnemyPlayer.enemiesBulletsList.get(o).bulletHit();
                            EnemyPlayer.enemiesBulletsList.remove(o);
                        }
                    }
                    if (s.isSheildBroken()) {
                        shieldList.remove(i);
                    } else {
                        s.paint(g);
                        i++;
                    }
                }

                // draws the aliens
                int j = 0;
                while (j < alienList.size()) {
                    Alien a = alienList.get(j);
                    for (int m = 0; m < bulletList.size(); m++) {
                        Point upperLeftBullet = bulletList.get(m).getUpperLeft();
                        Point upperLeftAlien = alienList.get(j).getUpperLeft();
                        if (Collision.bulletOverlapsObject(upperLeftBullet.x, upperLeftBullet.y,
                                Bullet.bulletWidth, Bullet.bulletHeight,
                                upperLeftAlien.x, upperLeftAlien.y,
                                Alien.ALIENSIZE, Alien.ALIENSIZE)) {
                            alienList.get(j).hitAlien();
                            bulletList.get(m).bulletHit();
                            bulletList.remove(m);
                            playerPoints += Alien.point;
                            playerPointLabel.setText("Player Point: " + playerPoints);
                        }
                    }
                    if (a.isAlienHit()) {
                        alienList.remove(j);
                    } else {
                        a.paint(g);
                        j++;
                    }
                }

                // draws the EnemyShip and collion
                int p = 0;
                while (p < enemyList.size()) {
                    EnemyPlayer e = enemyList.get(p);
                    for (int z = 0; z < bulletList.size(); z++) {
                        Point upperLeftBullet = bulletList.get(z).getUpperLeft();
                        Point upperLeftEnemy = enemyList.get(p).getUpperLeft();
                        if (Collision.bulletOverlapsObject(upperLeftBullet.x, upperLeftBullet.y,
                                Bullet.bulletWidth, Bullet.bulletHeight,
                                upperLeftEnemy.x, upperLeftEnemy.y,
                                Player.PLAYERSIZE, Player.PLAYERSIZE)) {
                            enemyList.get(p).hitEnemy();
                            bulletList.get(z).bulletHit();
                            bulletList.remove(z);
                            playerPoints += EnemyPlayer.point;
                            playerPointLabel.setText("Player Point: " + playerPoints);
                        }
                    }
                    if (e.getEnemyHitCount() == EnemyPlayer.ENEMEYHEALTH) {
                        enemyList.remove(p);
                    } else {
                        e.paint(g);
                        p++;
                    }
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
                int q = 0;
                while (q < EnemyPlayer.enemiesBulletsList.size()) {
                    Bullet b = EnemyPlayer.enemiesBulletsList.get(q);
                    if (b.isOffPanel() || b.isHit()) {
                        EnemyPlayer.enemiesBulletsList.remove(q);
                    } else {
                        b.paint(g);
                        q++;
                    }
                 }
               // }
            }
        };
        // Making an enemies
        EnemyPlayer enemy = new EnemyPlayer(gamePanel, new Point(100, EnemyPlayer.ENEMYPLAYERYPOS));
        enemy.start();
        enemyList.add(enemy);

        // construct and start a thread that will live as long as the program remains
        // active to call gamePanel.repaint() about 30 times per second, so individual
        // game objects do not need to do so.
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e) {
                    }
                    if(enemyList.size() == 0 && alienList.size() == 0 ){

                    }
                    //decrement so the player can shoot
                    if(shootCounter > 0 ){
                        shootCounter--;
                    }
                    if (keyPress_A)
                        player.translate(-MOVE_BY);
                    if (keyPress_D)
                        player.translate(MOVE_BY);

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
        playerPointLabel = new JLabel("Player Point: " + playerPoints);
        playerPointLabel.setForeground(Color.WHITE);
        centerPanelForScoreboardPanel.add(playerPointLabel);
        centerPanelForScoreboardPanel.setOpaque(false);
        //button to start the game and restart game
        currentButton = new JButton("Start Game");
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
    
    /**
     * Method called when the player has killed all the aliens and
     * enemey ships to move the player into the next live if the player 
     * is still Alive 
     * 
     */
    public void nextLevel(){


    }
    /**
     *Restarts the game.
     * 
     */
    public void restartGame(){

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Point currentPosPlayer1 = player.getPlayerCenter();

        // Moves the player depending on which button is pressed
        if (e.getKeyCode() == KeyEvent.VK_A) {
            keyPress_A = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keyPress_D = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if( shootCounter == 0){
            PlayerBullet bullet = new PlayerBullet(gamePanel, currentPosPlayer1);
            bullet.start();
            bulletList.add(bullet);
            shootCounter = 5;
            }
            return;
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
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton temp = (JButton) e.getSource();
        if(temp.getText().equals("Start Game")){
            gameStarted = true;
            gamePanel.requestFocus();
            currentButton.setText("Restart Game");
       }else if(temp.getText().equals("Restart Game")){
       
       }
        
    }

}