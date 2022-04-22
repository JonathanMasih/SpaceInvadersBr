import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This class makes an player with an image that can move around.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class Player {
    private Point upperLeftOfPLayer;
    private Point centerOfPlayer;
    private static Image playerImage;

    public Player(Point startPos) {
        this.upperLeftOfPLayer = startPos;
    }

    /**
     * A relative move of this object.
     * 
     * @param dx amount to translate in x
     * @param dy amount to translate in y
     */
    public void translate(int dx) {
        upperLeftOfPLayer.translate(dx, upperLeftOfPLayer.y);
        centerOfPlayer = new Point(upperLeftOfPLayer.x + 35, upperLeftOfPLayer.y + 35);
    }

    /**
     * paint this object onto the given Graphics area
     * 
     * @param g the Graphics object where the shape should be drawn
     */
    public void paint(Graphics g) {
        g.drawImage(playerImage, upperLeftOfPLayer.x, upperLeftOfPLayer.y, 70, 70,null);
        this.centerOfPlayer = new Point(upperLeftOfPLayer.x + 35, upperLeftOfPLayer.y - 35);
    }

    /**
     * Returns the center of the player at any given call.
     * 
     */
    public Point getPlayerCenter() {
        return centerOfPlayer;
    }

      /**
     * Set the Image to be used by the player Object 
     */
    public static void loadSnowPic() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Player.playerImage = toolkit.getImage("PlayerOne.png");
    }
}
