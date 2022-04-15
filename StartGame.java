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
        panel.setLayout(new BoxLayout( panel, BoxLayout.PAGE_AXIS));
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        //to center buttons and selection label, make panel layout manager null
        //and position them in center of the panel with font metrics
        JLabel selection = new JLabel("Please select a game mode.");
        selection.setPreferredSize(new Dimension(300, 50));
        selection.setFont(new Font("Verdana", Font.PLAIN, 20));
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
        JPanel oneplayerButtonPanel = new JPanel();
        oneplayerButtonPanel.setPreferredSize(new Dimension(200, 50));
        oneplayerButtonPanel.add(onePlayer);
        JPanel twoPlayerButtonPanel = new JPanel();
        twoPlayerButtonPanel.setPreferredSize(new Dimension(200, 50));
        twoPlayerButtonPanel.add(twoPlayer);


        panel.add(selectionLabelPanel);
        panel.add(oneplayerButtonPanel);
        panel.add(twoPlayerButtonPanel);
        centerPanel.add(panel , BorderLayout.CENTER);
        
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
        //"javax.swing.plaf.nimbus.NimbusLookAndFeel")
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception e){
        }
        javax.swing.SwingUtilities.invokeLater(new StartGame());
    }

}
