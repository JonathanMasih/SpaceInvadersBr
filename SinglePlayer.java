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
public class SinglePlayer extends Thread  implements Runnable {
    private JFrame frame;
    private JPanel backGroundPanel; 

    public SinglePlayer(JFrame frame , JPanel backGroundPanel){
     this.frame = frame;
     this.backGroundPanel = backGroundPanel;
    }
    
     /**
     * The run method to set up the graphical user interface
     */
    @Override
    public void run(){
        //Clears the frame from the Main menu and buttons so we can 
        //Implement single player mode components
        frame.getContentPane().removeAll();
        frame.setTitle("Welcome to Single Player!");
        frame.setPreferredSize(new Dimension(800, 800));
        //sets the background image for the frame.
        frame.setContentPane(backGroundPanel);
        // tell the JFrame that when someone closes the
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
     
        // display the window we've created
        frame.pack();
        frame.setVisible(true);
      
    }
}