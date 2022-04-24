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
  private static final int bulletSPEED = 5;

   public Bullet(Component panel, Point currentPos) {
    this.panel = panel;
    this.offPanel = false; 
    this.upperLeft = new Point(currentPos.x- (bulletWidth / 2),currentPos.y+ bulletHeight) ;
   }

   /**
     * Run method to define the life of this FallingSnow, which consists of
     * falling down to the bottom of the component, then sitting there for
     * a little while before melting.
     */
    @Override
    public void run() {
        while ( 0 + bulletHeight < upperLeft.y  ) {
            // every 30 ms or so, we move the coordinates of bullet
            upperLeft.translate(0, -1);
            //Repaint
            panel.repaint();
        }
        offPanel = true;
        panel.repaint();
    }

    /**
     * Paints the bullet object at its current position, if no collision is made
     * 
     * @param g the Graphics object in which to paint
     */
    public void paint(Graphics g) {
        System.out.println("Hello");
            g.setColor(Color.WHITE);
            g.fillRect(upperLeft.x, upperLeft.y, bulletWidth,  bulletHeight);
    }
    /**
     * returns if the bullet is off the panel or not
     * 
     * @param g the Graphics object in which to paint
     */
    public boolean isOffPanel(){
        return offPanel;
    }

}