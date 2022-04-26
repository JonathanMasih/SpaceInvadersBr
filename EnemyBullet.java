import java.awt.Point;
import java.awt.*;

/**
 * Checking to see if an enemy bullet attacks a players and lands a hit
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class EnemyBullet extends Bullet {    

    public EnemyBullet(Component panel, Point currentPos) {
        super(panel, currentPos);
    }

     /**
     * Run method to define the life of this FallingSnow, which consists of
     * falling down to the bottom of the component, then sitting there for
     * a little while before melting.
     */
    @Override
    public void run() {
        while ( SinglePlayer.GAME_PANEL_HEIGHT < super.upperLeft.y  ) {
            // every 30 ms or so, we move the coordinates of bullet
            try {
                sleep(DELAY_TIME);
            } catch (InterruptedException e) {
            }
            upperLeft.translate(0, bulletSPEED );
        }
        offPanel = true;
        panel.repaint();
    }

}