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
public class SinglePlayer extends Thread {
    private JFrame frame;
    private Image backgroundImage;
    private final static int GAME_PANEL_WIDTH = 800;
    private final static int GAME_PANEL_HEIGHT = 850;
    private Clip clip;

    public SinglePlayer(JFrame frame , Image img , Clip clip) {
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
       
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image playerOneImg = toolkit.getImage("PlayerOne.png");

        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.drawRect(0, 0, 700,GAME_PANEL_HEIGHT);
                g.drawImage(playerOneImg ,350, GAME_PANEL_HEIGHT -150,70, 75,this);
            }
        };
        gamePanel.setOpaque(false);
        backGroundPanel.add(gamePanel);

        frame.add(backGroundPanel);
        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }
}