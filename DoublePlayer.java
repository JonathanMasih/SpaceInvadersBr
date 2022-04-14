import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
 * This GUI will be designed for the two player game mode with
 * both players on the keyboard trying to shoot each other.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class DoublePlayer implements Runnable {

    @Override
    public void run(){

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
        panel = new JPanel();

        onePlayer = new JButton("One Player");
        twoPlayer = new JButton("Two Players");
        
        onePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);
       

        panel.add(onePlayer);
        panel.add(twoPlayer);
        frame.add(panel);

        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }
}
