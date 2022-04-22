import java.awt.*;
import java.awt.event.*;

/**
 * This is a bullet object that is shot from a player or
 * an enemey ship.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class Bullet extends Thread {

   // the Component where the bullet is being fired
   private Component panel;
   private Point upperLeft;
   private boolean  offPanel;
   private static final int bulletHeight = 10;
  private static final int bulletWidth = 6;

   public Bullet(Component panel, Point currentPos) {
    this.panel = panel;
    this.offPanel = false; 
    this.upperLeft = new Point(currentPos.x-bulletWidth ,currentPos.y - bulletHeight ) ;
   }

   /**
     * Run method to define the life of this FallingSnow, which consists of
     * falling down to the bottom of the component, then sitting there for
     * a little while before melting.
     */
    @Override
    public void run() {

        while (upperLeft.y < panel.getHeight() -bulletHeight) {

            // every 30 ms or so, we move the coordinates of the flake down
            // according to its falling speed
            upperLeft.translate(0, -1);
            //Repaint
            panel.repaint();
        }
        offPanel = true;
        panel.repaint();
    }

    /**
     * Paint this FallingSnow object at its current position, if not melted
     * 
     * @param g the Graphics object in which to paint
     */
    public void paint(Graphics g) {
        if (!offPanel) {
            g.fillRect(upperLeft.x, upperLeft.y, bulletWidth,  bulletHeight);
        }
    }

}
