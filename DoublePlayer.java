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
public class DoublePlayer extends Thread  {
    private JFrame frame;
    private JPanel backGroundPanel; 

    public DoublePlayer(JFrame frame , JPanel backGroundPanel){
     this.frame = frame;
     this.backGroundPanel = backGroundPanel;
    }
    
     /**
     * The run method to set up the graphical user interface
     */
    @Override
    public void run(){
        //Clears the frame from the Main menu and buttons so we can 
        //Implement double player mode components
        frame.getContentPane().removeAll();
        frame.setTitle("Welcome to Double Player!");
        frame.setPreferredSize(new Dimension(800, 800));
        //sets the background image for the frame.
        frame.setContentPane(backGroundPanel);
        // tell the JFrame that when someone closes the
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Plays the background music 
        File audiofile = new File("spaceInvadersMusic.wav");
        try {
            AudioInputStream audioStream =  AudioSystem.getAudioInputStream(audiofile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
       
     
        // display the window we've created
        frame.pack();
        frame.setVisible(true);
      
    }
}