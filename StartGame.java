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
    private JPanel panel;
    private JLabel selection;

    /**
     * The run method to set up the graphical user interface
     */

    @Override
    public void run() {

        // set up the GUI "look and feel" which should match
        // the OS on which we are running
        JFrame.setDefaultLookAndFeelDecorated(true);

        // create a JFrame in which we will build our very
        // tiny GUI, and give the window a name
        JFrame frame = new JFrame("Welcome to Space Invaders!");
        frame.setPreferredSize(new Dimension(800, 800));

        // tell the JFrame that when someone closes the
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel with a paintComponent method
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                FontMetrics fm = g.getFontMetrics();

                // String toDisplay = "Mouse Around and See!";
                int stringWidth = fm.stringWidth(toDisplay);
                int stringAscent = fm.getAscent();

                int xStart = getWidth() / 2 - stringWidth / 2;
                int yStart = getHeight() / 2 + stringAscent / 2;

                g.drawString(toDisplay, xStart, yStart);
            }
        };

        selection = new JLabel("Please select a game mode.");
        onePlayer = new JButton("Single Player");
        onePlayer.setPreferredSize(new Dimension(50, 50));
        twoPlayer = new JButton("Multiplayer");
        twoPlayer.setPreferredSize(new Dimension(50, 50));

        onePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);

        panel.add(selection);
        panel.add(onePlayer);
        panel.add(twoPlayer);
        frame.add(panel);

        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == onePlayer) {

        }
        if (e.getSource() == twoPlayer) {

        }

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new StartGame());
    }

}
