import java.awt.Point;
import java.awt.*;
import java.util.*;

public class EnemyPlayer extends Thread {
    private Component panel;
    protected Point upperLeftOfEnemyPlayer;
    private Point centerOfEnemyPlayer;
    protected static Image enemyPlayerImage;
    public final static int ENEMYPLAYERYPOS = 50;
    private int hits;
    protected static ArrayList<EnemyBullet> enemiesBulletsList;

    public EnemyPlayer(Component panel,Point startPos) {
        this.panel = panel;
        this.hits= 0;
        this.upperLeftOfEnemyPlayer = startPos;
        this.centerOfEnemyPlayer = new Point(startPos.x + (Player.PLAYERSIZE / 2),
        upperLeftOfEnemyPlayer.y + (Player.PLAYERSIZE/ 2));
    }

    /**
     * Set the Image to be used by the Enemy player Object
     */
    public static void loadEnemyPic() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        enemyPlayerImage = toolkit.getImage("alienshipPlayerTwo.png");
    }
    
    /**
     * paint this object onto the given Graphics area
     * 
     * @param g the Graphics object where the shape should be drawn
     */
    public void paint(Graphics g) {
        g.drawImage(enemyPlayerImage, upperLeftOfEnemyPlayer.x,upperLeftOfEnemyPlayer.y ,Player.PLAYERSIZE,Player.PLAYERSIZE,null);
        centerOfEnemyPlayer = new Point( upperLeftOfEnemyPlayer.x + (Player.PLAYERSIZE/2),  upperLeftOfEnemyPlayer.y +(Player.PLAYERSIZE/2));
    }

    
    /**
     * A relative move of this object.
     * 
     * @param dx amount to translate in x
     */
    public void translate(int dx) {
        upperLeftOfEnemyPlayer.translate(dx, 0);
        centerOfEnemyPlayer = new Point( upperLeftOfEnemyPlayer.x + (Player.PLAYERSIZE/2),  
        upperLeftOfEnemyPlayer.y +(Player.PLAYERSIZE/2));
    }

    @Override
    public void run() {
        Random rand = new Random();
        int movementSpeedx = 0;
        movementSpeedx += rand.nextInt(11) - 10;
        while (hits != 5) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
            }

           // Check for the right bound of the game panel
            if(upperLeftOfEnemyPlayer.x  < 10  ){
                movementSpeedx += rand.nextInt(11) ;
            }else if ( upperLeftOfEnemyPlayer.x + Player.PLAYERSIZE  + 10 > SinglePlayer.GAME_PANEL_WIDTH){
                movementSpeedx -= rand.nextInt(11) ;
            }else {
                movementSpeedx += rand.nextInt(21) - 10;
            }
            translate(movementSpeedx);
            
            // EnemyBullet newBullet = new EnemyBullet(SinglePlayer.gamePanel,
            //         new Point(upperLeftOfEnemyPlayer.x + (Player.PLAYERSIZE / 2), upperLeftOfEnemyPlayer.y +Player.PLAYERSIZE + 5));
            // newBullet.start();
            // enemiesBulletsList.add(newBullet);
     
        }
    }

}
