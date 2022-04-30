import java.util.ArrayList;
import java.awt.*;

/**
 * This is a bullet object that is shot from either ship in Multi-Player mode
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio, Tyler Streithorst
 * @version Spring 2022
 */
public class MultiPlayerBullet extends Bullet {    
    protected static ArrayList<MultiPlayerBullet> MultiPlayerBullets = new ArrayList<MultiPlayerBullet>();
    private int rotationInt;
    private Point centerPoint;
    public MultiPlayerBullet(Component panel, Point currentPos, int rotationInt) {
        super(panel);
        this.rotationInt = rotationInt;
        centerPoint = new Point(currentPos.x, currentPos.y) ;
    }

    @Override
    public Point getUpperLeft() {
        return new Point(centerPoint.x-6, centerPoint.y-6);
    }

     /**
     * Run method to define the life of this bullet.
     */
    @Override
    public void run() {
        while (centerPoint.x > 0 && centerPoint.x < SinglePlayer.GAME_PANEL_WIDTH-Player.PLAYERSIZE-32 && centerPoint.y < SinglePlayer.GAME_PANEL_HEIGHT  && centerPoint.y > 0) {
            // every 30 ms or so, we move the coordinates of bullet
            sleepWithCatch(DELAY_TIME);
            centerPoint.translate((int)(10*Math.cos(Math.toRadians(rotationInt-90))), (int)(10*Math.sin(Math.toRadians(rotationInt-90))));
        }
        offPanel = true;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        // The offest of one is more a more accurate allignment with the hitbox
        int[] Xs = {(int)(centerPoint.x-6*Math.cos(Math.toRadians(rotationInt-90-30)))-1, (int)(centerPoint.x-6*Math.cos(Math.toRadians(rotationInt-90+30)))-1,
            (int)(centerPoint.x-6*Math.cos(Math.toRadians(rotationInt+90-30)))-1, (int)(centerPoint.x-6*Math.cos(Math.toRadians(rotationInt+90+30)))-1};
        int[] Ys = {(int)(centerPoint.y-6*Math.sin(Math.toRadians(rotationInt-90-30)))-1, (int)(centerPoint.y-6*Math.sin(Math.toRadians(rotationInt-90+30)))-1,
            (int)(centerPoint.y-6*Math.sin(Math.toRadians(rotationInt+90-30)))-1, (int)(centerPoint.y-6*Math.sin(Math.toRadians(rotationInt+90+30)))-1};
        g.fillPolygon(Xs, Ys, 4);
        if(DoublePlayer.debugMode)
            g.drawRect(centerPoint.x-6, centerPoint.y-6, 6, 6);
    }

}