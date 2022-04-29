import java.awt.Point;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class MultiPlayer2 {
    protected Point upperLeftOfPlayer2;
    protected  Point centerOfPlayer2;
    protected static BufferedImage enemyPlayerImage;
    protected static BufferedImage originalEnemyPlayerImage;
    protected final static int PLAYER2YPOS = 50;
    protected int lives;
    //protected static ArrayList<EnemyBullet> EmnemyBulletsList = new ArrayList<>();
    protected boolean rotatingClockwise = false;
    protected int rotation = 180;
    protected double speed;

    public MultiPlayer2(Point startPos) {
        this.lives = 5;
        this.upperLeftOfPlayer2 = new Point(startPos.x-32, startPos.y-34);
        this.centerOfPlayer2 = new Point(startPos.x + ((Player.PLAYERSIZE+32) / 2),
                upperLeftOfPlayer2.y + ((Player.PLAYERSIZE+34) / 2));
    }

    /**
     * Set the Image to be used by the Enemy player Object
     */
    public static void loadEnemyPic() {
        try {
            enemyPlayerImage = ImageIO.read(new File("MultiPlayerTwo.png"));
            originalEnemyPlayerImage = enemyPlayerImage;
        } catch (IOException e) {}
    }

    /**
     * paint this object onto the given Graphics area
     * 
     * @param g the Graphics object where the shape should be drawn
     */
    public void paint(Graphics g) {
        //Graphics2D g2d = (Graphics2D)g;
        g.drawImage(enemyPlayerImage, upperLeftOfPlayer2.x, upperLeftOfPlayer2.y, Player.PLAYERSIZE+32,
                Player.PLAYERSIZE+34, null);
    }

    public void rotate(boolean clockwise) {
        // Get Dimensions of image
        int width = enemyPlayerImage.getWidth();
        int height = enemyPlayerImage.getHeight();
        // Create a new buffered image with the same dimentions and type
        BufferedImage newImage = new BufferedImage(
            enemyPlayerImage.getWidth(), enemyPlayerImage.getHeight(), enemyPlayerImage.getType());
        // create Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();
        if(clockwise) {
            rotation += 3;
            if(rotation >= 360)
                rotation -= 360;
        }
        else {
            rotation -= 3;
            if(rotation < 0)
                rotation += 360;
        }
        g2.rotate(Math.toRadians(rotation-180), width / 2, height / 2);
        g2.drawImage(originalEnemyPlayerImage, 0, 0, null);
        // Return rotated buffer image
        enemyPlayerImage = newImage;
        return;
    }
    
    public double getSpeed(){
        return speed;
    }

    public void modifySpeed(double mod) {
        if(speed > -3 && speed < 3)
            speed += mod;
        if(speed >= 3)
            speed = 2.9;
        if(speed <= -3)
            speed = -2.9;
        if(speed < 0.1 && speed > -0.1)
            speed = 0;
    }

    /**
     * A relative move of this object.
     * 
     * @param dx amount to translate in x
     */
    public void translate(int dx) {
        upperLeftOfPlayer2.translate((int)(speed*dx*Math.cos(Math.toRadians(rotation-90))), (int)(speed*dx*Math.sin(Math.toRadians(rotation-90))));
        if (upperLeftOfPlayer2.x < 0) {
            upperLeftOfPlayer2.x = 0;
        }
        else if (upperLeftOfPlayer2.x > SinglePlayer.GAME_PANEL_WIDTH - 5 * ((Player.PLAYERSIZE + 6) / 2)) {
            upperLeftOfPlayer2.x = SinglePlayer.GAME_PANEL_WIDTH - 5 * ((Player.PLAYERSIZE + 6) / 2);
        }
        if (upperLeftOfPlayer2.y < 0)
            upperLeftOfPlayer2.y = 0;
        else if(upperLeftOfPlayer2.y > SinglePlayer.GAME_PANEL_HEIGHT-Player.PLAYERSIZE-32)
            upperLeftOfPlayer2.y = SinglePlayer.GAME_PANEL_HEIGHT-Player.PLAYERSIZE-32;
        centerOfPlayer2 = new Point(upperLeftOfPlayer2.x + ((Player.PLAYERSIZE+32) / 2),
                upperLeftOfPlayer2.y + ((Player.PLAYERSIZE+34) / 2));
    }

    /**
     * Returns the center of the Enemy at any given call.
     * 
     */
    public Point getPlayer2Center() {
        return centerOfPlayer2;
    }


     /**
     * Returns the upperleft of the Enemy at any given call.
     * 
     */
    public Point getUpperLeft(){
        return  upperLeftOfPlayer2;
    }

     /**
     * Returns the if the enemy is hit;
     * 
     */
    public int getPlayer2Lives() {
        return lives;
    }
    
    
     /**
     * Increamts hits by 1 each time the enemy is hit
     * 
     */
    public void hitPlayer(){
        lives--;
    }
        /**
     * Run method to define the life of this bullet.
     */
    public void fireBullet(Component c){
        MultiPlayerBullet bullet = new MultiPlayerBullet(c ,
        new Point (centerOfPlayer2.x + (int)(50*Math.cos(Math.toRadians(rotation-90)))
            , centerOfPlayer2.y + (int)(50*Math.sin(Math.toRadians(rotation-90)))), rotation);
        bullet.start();
        MultiPlayer1.playerBulletsList.add(bullet);
    }

}
