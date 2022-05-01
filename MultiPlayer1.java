import java.awt.Point;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class MultiPlayer1 {
    protected Point upperLeftOfPlayer1;
    protected  Point centerOfPlayer1;
    protected static BufferedImage player1Image;
    protected static BufferedImage originalPlayer1Image;
    protected final static int PLAYER1YPOS = 650;
    protected int lives;
    protected double speed;
    protected static ArrayList<MultiPlayerBullet> player1BulletsList = new ArrayList<>();
    protected boolean rotatingClockwise = false;
    protected int rotation = 0;
    private int shotCooldown;

    public MultiPlayer1(Point startPos) {
        this.lives = 5;
        this.speed = 0;
        this.upperLeftOfPlayer1 = new Point(startPos.x, startPos.y);
        this.centerOfPlayer1 = new Point(startPos.x + (Player.PLAYERSIZE / 2),
                upperLeftOfPlayer1.y + (Player.PLAYERSIZE / 2));
        shotCooldown = 0;
    }

    /**
     * Set the Image to be used by the Enemy player Object
     */
    public static void loadPlayerPic() {
        try {
            player1Image = ImageIO.read(new File("MultiPlayerOne.png"));
            originalPlayer1Image = player1Image;
        } catch (IOException e) {}
    }

    /**
     * paint this object onto the given Graphics area
     * 
     * @param g the Graphics object where the shape should be drawn
     */
    public void paint(Graphics g) {
        g.drawImage(player1Image, upperLeftOfPlayer1.x, upperLeftOfPlayer1.y, Player.PLAYERSIZE,
                Player.PLAYERSIZE, null);
        if(DoublePlayer.debugMode) {
            g.setColor(Color.WHITE);
            g.drawString("X: " + Integer.toString(centerOfPlayer1.x) + ",  Y: " + Integer.toString(centerOfPlayer1.y), 600, 670);
            g.drawString("Rotation: " + Integer.toString(rotation), 600, 685);
            g.drawString("Blue Bullets: " + player1BulletsList.size(), 600, 700);
            g.drawRect(upperLeftOfPlayer1.x, upperLeftOfPlayer1.y, Player.PLAYERSIZE, Player.PLAYERSIZE);
        }
    }

    public void rotate(boolean clockwise) {
        // Get Dimensions of image
        int width = originalPlayer1Image.getWidth();
        int height = originalPlayer1Image.getHeight();
        // Create a new buffered image with the same dimentions and type
        BufferedImage newImage = new BufferedImage(
            player1Image.getWidth(), player1Image.getHeight(), player1Image.getType());
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
        g2.rotate(Math.toRadians(rotation), width / 2, height / 2);
        //player1Image = originalPlayer1Image;
        g2.drawImage(originalPlayer1Image, 0, 0, null);
        // Return rotated buffer image
        player1Image = newImage;
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

    public void reset() {
        speed = 0;
        rotation = 3;
        rotate(false); // resets to 0
        upperLeftOfPlayer1 = new Point(100, PLAYER1YPOS);
        lives = 5;
    }

    /**
     * A relative move of this object.
     * 
     * @param dx amount to translate in x
     */
    public void translate(int dx) {
            upperLeftOfPlayer1.translate((int)(speed*dx*Math.cos(Math.toRadians(rotation-90))), (int)(speed*dx*Math.sin(Math.toRadians(rotation-90))));
            if (upperLeftOfPlayer1.x < 0) {
                upperLeftOfPlayer1.x = 0;
            }
            else if (upperLeftOfPlayer1.x > SinglePlayer.GAME_PANEL_WIDTH - 5 * ((Player.PLAYERSIZE - 1) / 2)) {
                upperLeftOfPlayer1.x = SinglePlayer.GAME_PANEL_WIDTH - 5 * ((Player.PLAYERSIZE - 1) / 2);
            }
            if (upperLeftOfPlayer1.y < 0)
                upperLeftOfPlayer1.y = 0;
            else if(upperLeftOfPlayer1.y > SinglePlayer.GAME_PANEL_HEIGHT-Player.PLAYERSIZE)
                upperLeftOfPlayer1.y = SinglePlayer.GAME_PANEL_HEIGHT-Player.PLAYERSIZE;
            centerOfPlayer1 = new Point(upperLeftOfPlayer1.x + ((Player.PLAYERSIZE) / 2),
                    upperLeftOfPlayer1.y + ((Player.PLAYERSIZE) / 2));
    }

    /**
     * Returns the center of the Enemy at any given call.
     * 
     */
    public Point getPlayer1Center() {
        return centerOfPlayer1;
    }


     /**
     * Returns the upperleft of the Enemy at any given call.
     * 
     */
    public Point getUpperLeft(){
        return  upperLeftOfPlayer1;
    }

     /**
     * Returns the if the enemy is hit;
     * 
     */
    public int getPlayer1Lives() {
        return lives;
    }
    
    
     /**
     * Increamts hits by 1 each time the enemy is hit
     * 
     */
    public void hitPlayer(){
        if(lives > 0)
            lives--;
    }

    public int getShotCooldown() {
        return shotCooldown;
    }
    public void setCooldown(int n) {
        shotCooldown = n;
    }
    public void cooldown() {
        if(shotCooldown > 0)
            shotCooldown--;
    }

        /**
     * Run method to define the life of this bullet.
     */
    public void fireBullet(Component c){
        if(lives > 0) {
            MultiPlayerBullet bullet = new MultiPlayerBullet(c ,
            new Point (centerOfPlayer1.x + (int)(40*Math.cos(Math.toRadians(rotation-90)))
                , centerOfPlayer1.y + (int)(40*Math.sin(Math.toRadians(rotation-90)))), rotation);
            bullet.start();
            player1BulletsList.add(bullet);
        }
    }

}
