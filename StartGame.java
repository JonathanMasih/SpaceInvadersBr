import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This GUI will display two buttons with options for playing the single player
 * mode
 * or the double player mode.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class StartGame implements ActionListener, Runnable {
    private JButton onePlayer;
    private JButton twoPlayer;
    private static Image backgroundImage;
    private JFrame frame;
    private JPanel backGroundPanel;

    /**
     * The run method to set up the graphical user interface
     */
    @Override
    public void run() {
        // create a JFrame in which we will build our very
        // tiny GUI, and give the window a name

        frame = new JFrame("Welcome to Space Invaders!");
        frame.setPreferredSize(new Dimension(800, 800));

        // tell the JFrame that when someone closes the
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel for the buttons to pick the game mode
        JPanel gameModePanel = new JPanel();
        gameModePanel.setLayout(new BoxLayout(gameModePanel, BoxLayout.PAGE_AXIS));
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Setting the background of the frame
        backGroundPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);
                // draw the snowflake
                g.drawImage(backgroundImage, 0, 0, frame.getWidth(), frame.getHeight(), this);
            }
        };

        // to center buttons and selection label, make panel layout manager null
        // and position them in center of the panel with font metrics
        JLabel selection = new JLabel("Please select a game mode.");
        selection.setPreferredSize(new Dimension(300, 50));
        selection.setFont(new Font("Verdana", Font.PLAIN, 20));
        selection.setForeground(Color.BLUE);
        onePlayer = new JButton("Single Player");
        onePlayer.setFont(new Font("Verdana", Font.PLAIN, 20));
        onePlayer.setPreferredSize(new Dimension(200, 50));
        twoPlayer = new JButton("Multiplayer");
        twoPlayer.setFont(new Font("Verdana", Font.PLAIN, 20));
        twoPlayer.setPreferredSize(new Dimension(200, 50));
        onePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);

        JPanel selectionLabelPanel = new JPanel();
        selectionLabelPanel.add(selection);
        selectionLabelPanel.setPreferredSize(new Dimension(300, 50));
        selectionLabelPanel.setOpaque(false);
        JPanel oneplayerButtonPanel = new JPanel();
        oneplayerButtonPanel.setPreferredSize(new Dimension(200, 50));
        oneplayerButtonPanel.add(onePlayer);
        oneplayerButtonPanel.setOpaque(false);
        JPanel twoPlayerButtonPanel = new JPanel();
        twoPlayerButtonPanel.setPreferredSize(new Dimension(200, 50));
        twoPlayerButtonPanel.add(twoPlayer);
        twoPlayerButtonPanel.setOpaque(false);
        gameModePanel.add(selectionLabelPanel);
        gameModePanel.add(oneplayerButtonPanel);
        gameModePanel.add(twoPlayerButtonPanel);

        // makes the gameModePanel transparent
        gameModePanel.setOpaque(false);
        centerPanel.setOpaque(false);
        centerPanel.add(gameModePanel, BorderLayout.CENTER);

        onePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);
        // Sets the background of the frame to space image
        frame.setContentPane(backGroundPanel);
        frame.add(centerPanel);
        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Checks which action is performed and does something
     * depending on the action.
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == onePlayer) {
             //makes a new singleplayer objects and starts it
           SinglePlayer game = new SinglePlayer(frame,backGroundPanel );
           game.start();
        }
        if (e.getSource() == twoPlayer) {
            //makes a new doubleplayer objects and starts it
            DoublePlayer game = new DoublePlayer(frame,backGroundPanel );
           game.start();
        }

    }

    public static void main(String[] args) {
        // "javax.swing.plaf.nimbus.NimbusLookAndFeel")
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        backgroundImage = toolkit.getImage("background.gif");
        javax.swing.SwingUtilities.invokeLater(new StartGame());
    }

}
