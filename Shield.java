import java.awt.*;
/** 
 * This class will draw a shield. That will protect a player 
 * from bullets.
 * 
*/
public class Shield {
    
    private Point upperLeftOfShield;
    private Point centerOfShield;
    protected boolean sheildBroken;
    protected int sheildHitsTaken;
    public static final int SHIELDSIZEW = 100;
    public static final int SHIELDSIZEL = 30;
    public static final int PLAYERYPOS = 600;

    public Shield(Point upperLeft) {
        this.upperLeftOfShield = upperLeft;
        this.centerOfShield = new Point(upperLeft.x+(SHIELDSIZEW /2 ),
                                        PLAYERYPOS +(SHIELDSIZEL /2 )
                                        );
        this.sheildBroken = false;
    }

   
    /**
     * paint this Sheild onto the given Graphics area
     * 
     * @param g the Graphics object where the shape should be drawn
     */
    public void paint(Graphics g) {
       if(!sheildBroken){
         g.setColor(Color.BLUE);
         g.fillRect( upperLeftOfShield.x,upperLeftOfShield.y, SHIELDSIZEW ,SHIELDSIZEL);
       }
    }
    /**
     * Returns the center of the  Sheild at any given call.
     * 
     */
    public Point getPlayerCenter() {
        return centerOfShield;
    }

    /**
     * Returns if the shield is broken or not
     * 
     */
    public boolean isSheildBroken () {
        return sheildBroken;
    }


}
