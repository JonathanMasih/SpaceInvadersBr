import java.awt.*;

/**
 * This class makes an player with an image that can move around.
 * 
 * @author Jonathan Masih, Trevor Collins, Saif Ullah, Seth Coluccio
 * @version Spring 2022
 */
public class Player {
    protected Point upperLeftOfPLayer;
    private Point centerOfPlayer;
    protected static Image playerImage;
    protected final static int PLAYERSIZE = 70;
    protected final static int PLAYERYPOS = 700;
    private int playerLives = 5;

    public Player(Point startPos) {
        this.upperLeftOfPLayer = startPos;
        this.centerOfPlayer = new Point(startPos.x+(PLAYERSIZE /2 ),
        upperLeftOfPLayer.y + (PLAYERSIZE /2 )
                                        );
    }

    /**
     * A relative move of this object.
     * 
     * @param dx amount to translate in x
     */
    public void translate(int dx) {
        upperLeftOfPLayer.translate(dx,0);
        if (upperLeftOfPLayer.x < 0) {
            upperLeftOfPLayer.x = 0;
        }
        if (upperLeftOfPLayer.x  > SinglePlayer.GAME_PANEL_WIDTH - (5 * (Player.PLAYERSIZE / 2))) {
            upperLeftOfPLayer.x = SinglePlayer.GAME_PANEL_WIDTH - (5 * (Player.PLAYERSIZE / 2));
        }
        centerOfPlayer = new Point(upperLeftOfPLayer.x + (PLAYERSIZE/2), upperLeftOfPLayer.y +(PLAYERSIZE/2));
    }

    /**
     * paint this object onto the given Graphics area
     * 
     * @param g the Graphics object where the shape should be drawn
     */
    public void paint(Graphics g) {
        if(playerLives != 0){
        g.drawImage(playerImage, upperLeftOfPLayer.x,PLAYERYPOS, PLAYERSIZE, PLAYERSIZE,null);
        }
    }

    /**
     * Returns the center of the player at any given call.
     * 
     */
    public Point getPlayerUpperLeft() {
        return upperLeftOfPLayer;
    }

    /**
     * Return the number of player lives
     */
    public int getPlayerLives(){
        return playerLives;
    }

    /**
     * Return the number of center of the player
     */
    public Point  getPlayerCenter(){
        return  centerOfPlayer;
    }

     /**
     * Decrements lives by 1 each time the player is hit
     * 
     */
    public void hitPlayer(){
        playerLives --;
    }

      /**
     * Set the Image to be used by the player Object 
     */
     public static void loadPlayerPic() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        playerImage = toolkit.getImage("PlayerOne.png");
    }
}
