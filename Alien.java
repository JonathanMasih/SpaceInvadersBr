import java.awt.*;

/**
 * This is an alien object which a player can shoot and earn points.
 * This alien will also be a a sheild for the enemy ship.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public abstract class Alien {
    protected static Image alienImage;
    protected Point upperLeftOfAlien;
    protected Point centerOfAlien;
    protected boolean alienHit;
    protected static final int ALIENSIZE = 50;
    protected static final int ALIENYPOS1 = 400;
    protected static final int ALIENYPOS2 = 300;
    protected static final int ALIENYPOS3 = 200;

    public Alien(Point upperLeft) {
        this.upperLeftOfAlien = upperLeft;
        this.centerOfAlien = new Point(upperLeft.x + (ALIENSIZE / 2),
                upperLeft.y + (ALIENSIZE / 2));

        this.alienHit = false;
    }
    public void paint(Graphics g) {
        if (!alienHit) {
            g.drawImage(alienImage, upperLeftOfAlien.x, upperLeftOfAlien.y, ALIENSIZE, ALIENSIZE, null);
        }
    }

    /**
     * Returns the center of the alien at any given call.
     * 
     */
    public boolean isAlienHit() {
        return alienHit;
    }

    /**
     * Returns if the alien has been hit or not.
     * 
     */
    public Point getPlayerCenter() {
        return centerOfAlien;
    }

    /**
     *Set the alienHit to true so we stop drawing it
     * 
     */
    public void hitAlien() {
        alienHit = true;
    }

    
    /**
     * Return upperleft of the bullet
     * 
     * @return the upperleft of bullet
     */
    public Point getUpperLeft() {
        return upperLeftOfAlien;
    }
     /**
     * Set the Image to be used by the Alien Object
     */
    public static void loadAlienPic() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        alienImage = toolkit.getImage("Alien.png");
    }

}
