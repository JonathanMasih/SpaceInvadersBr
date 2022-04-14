import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.accessibility.AccessibleTextSequence;
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

        // JPanel for the buttons to pick the game mode
        JPanel panel = new JPanel();
        panel.setLayout(null);
         
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        //to center buttons and selection label, make panel layout manager null
        //and position them in center of the panel with font metrics
        JLabel selection = new JLabel("Please select a game mode.");
        selection.setPreferredSize(new Dimension(250, 100));
        selection.setFont(new Font("Verdana", Font.PLAIN, 20));
        onePlayer = new JButton("Single Player");
        onePlayer.setBounds(400, panel.getHeight(), 100, 20);
        twoPlayer = new JButton("Multiplayer");
        twoPlayer.setPreferredSize(new Dimension(200, 50));
        onePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);
  
        panel.add(selection);
        panel.add(onePlayer);
        panel.add(twoPlayer);
        centerPanel.add(panel,BorderLayout.CENTER);
        
        frame.add(centerPanel);

        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == onePlayer) {
           // javax.swing.SwingUtilities.invokeLater(new SinglePlayer());
        }
        if (e.getSource() == twoPlayer) {
           // javax.swing.SwingUtilities.invokeLater(new DoublePlayer());
        }

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new StartGame());
    }

}
