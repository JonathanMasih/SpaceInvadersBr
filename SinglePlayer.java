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
    private final static int GAME_PANEL_WIDTH = 800;
    private final static int GAME_PANEL_HEIGHT = 850;
    // amount to the move player on each key press
    public static final int MOVE_BY = 10;
    private Clip clip;
    private Point upperLeftOfPlayer;
    private JPanel gamePanel;
    private Player player;
    ArrayList<Bullet> bulletList;

    public SinglePlayer(JFrame frame, Image img, Clip clip) {
        this.frame = frame;
        this.backgroundImage = img;
        this.clip = clip;
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

        upperLeftOfPlayer = new Point(315, 700);
         
        bulletList = new ArrayList<Bullet>();
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

                for(Bullet b: bulletList){
                    if(!b.isOffPanel()){
                          b.paint(g);
                    }
                }

            }
        };
        // sets the size of the game panel
        gamePanel.setPreferredSize(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT));
        // Creates a player when the game started
        player = new Player(upperLeftOfPlayer);
        gamePanel.setOpaque(false);
        backGroundPanel.add(gamePanel);

        frame.add(backGroundPanel);
        // Checks if any key is pressed
        frame.addKeyListener(this);
        //Sets the keyboard focus on this frame
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
        if (e.getKeyCode() == KeyEvent.VK_A && currentPosPlayer1.x > (5 + Player.PLAYERSIZE / 2)) {
            player.translate(-MOVE_BY);
            // If the player is moving and wants to shoot
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                Bullet bullet = new Bullet(gamePanel, currentPosPlayer1);
                bullet.start();
                bulletList.add(bullet);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D
                && currentPosPlayer1.x < GAME_PANEL_WIDTH - (2 * Player.PLAYERSIZE)) {
            player.translate(MOVE_BY);
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