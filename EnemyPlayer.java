import java.awt.Point;
import java.awt.*;
import java.util.*;

public class EnemyPlayer extends Thread {
    private Component panel;
    protected Point upperLeftOfEnemyPlayer;
    private Point centerOfEnemyPlayer;
    protected static Image enemyPlayerImage;
    protected final static int ENEMYPLAYERYPOS = 50;
    private int hits;
    protected static ArrayList<EnemyBullet> enemiesBulletsList
    = new ArrayList<>();

    public EnemyPlayer(Component panel, Point startPos) {
        this.panel = panel;
        this.hits = 0;
        this.upperLeftOfEnemyPlayer = startPos;
        this.centerOfEnemyPlayer = new Point(startPos.x + (Player.PLAYERSIZE / 2),
                upperLeftOfEnemyPlayer.y + (Player.PLAYERSIZE / 2));
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
        g.drawImage(enemyPlayerImage, upperLeftOfEnemyPlayer.x, upperLeftOfEnemyPlayer.y, Player.PLAYERSIZE,
                Player.PLAYERSIZE, null);
        centerOfEnemyPlayer = new Point(upperLeftOfEnemyPlayer.x + (Player.PLAYERSIZE / 2),
                upperLeftOfEnemyPlayer.y + (Player.PLAYERSIZE / 2));
    }

    /**
     * A relative move of this object.
     * 
     * @param dx amount to translate in x
     */
    public void translate(int dx) {
        upperLeftOfEnemyPlayer.translate(dx, 0);
        if (upperLeftOfEnemyPlayer.x < 0) {
            upperLeftOfEnemyPlayer.x = 0;
        }
        if (upperLeftOfEnemyPlayer.x > SinglePlayer.GAME_PANEL_WIDTH - 5 * (Player.PLAYERSIZE / 2)) {
            upperLeftOfEnemyPlayer.x = SinglePlayer.GAME_PANEL_WIDTH - 5 * (Player.PLAYERSIZE / 2);
        }
        centerOfEnemyPlayer = new Point(upperLeftOfEnemyPlayer.x + (Player.PLAYERSIZE / 2),
                upperLeftOfEnemyPlayer.y + (Player.PLAYERSIZE / 2));
    }

    @Override
    public void run() {
        Random rand = new Random();
        int movementSpeedx = 0;
        movementSpeedx += rand.nextInt(21) - 10;
        while (hits != 5) {
            try {
                sleep(150);
            } catch (InterruptedException e) {
            }
            //
            movementSpeedx += rand.nextInt(21) - 10;
            if (upperLeftOfEnemyPlayer.x == 0) {
                movementSpeedx += rand.nextInt(11);
            }
            if (upperLeftOfEnemyPlayer.x == SinglePlayer.GAME_PANEL_WIDTH - 5 * (Player.PLAYERSIZE / 2)) {
                movementSpeedx -= rand.nextInt(11);
            }
            translate(movementSpeedx);

            EnemyBullet newBullet = new EnemyBullet(SinglePlayer.gamePanel,
            new Point(upperLeftOfEnemyPlayer.x + (Player.PLAYERSIZE / 2),
            upperLeftOfEnemyPlayer.y +Player.PLAYERSIZE + 5));
            newBullet.start();
            enemiesBulletsList.add(newBullet);

        }
    }

}
