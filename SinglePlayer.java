import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
 * This GUI will be set up to show one player attemoting to shoot aliends and
 * other ships
 * while avoiding their lasers as well.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class SinglePlayer implements Runnable {
    private Image background;

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
        Toolkit tools = Toolkit.getDefaultToolkit();
        background = tools.getImage("Background for space invaders.jpeg");

        JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

				// first, we should call the paintComponent method we are
				// overriding in JPanel
				super.paintComponent(g);

				// draw a big, blue background
				//g.setColor(Color.BLUE);
				//g.fillRect(0, 0, 500, 500);

				
				//g.drawImage(background, 100, 100, this);

			}
		};

        JPanel gamePanel = new JPanel();
   
         frame.add(gamePanel);
         frame.add(panel);
        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }
}