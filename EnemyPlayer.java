import java.awt.Point;
import java.awt.*;
import java.util.*;

public class EnemyPlayer extends Player {

    protected int hits;
    protected final int DELAY_TIME = 30;
    protected final int ENEMYYPOS = 200;

    public EnemyPlayer(Point startPos) {
        super(startPos);
        this.hits = 5;
        // TODO Auto-generated constructor stub
    }

    /**
     * Set the Image to be used by the player Object
     */
    // @Override
    public static void loadPlayerPic() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Player.playerImage = toolkit.getImage("alienshipPlayerTwo.png");
    }

    @Override
    public void run() {
        Random rand = new Random();
        int movementSpeedx = 0;
         movementSpeedx += rand.nextInt(11) - 10;
        while (hits != 0) {
            try {
                sleep(DELAY_TIME);
            } catch (InterruptedException e) {
            }
            if(super.upperLeftOfPLayer.x +  super.PLAYERSIZE < SinglePlayer.GAME_PANEL_WIDTH){
                movementSpeedx += rand.nextInt(11) - 10;
            super.translate(movementSpeedx);
            }
            else if(super.upperLeftOfPLayer.x +  super.PLAYERSIZE > SinglePlayer.GAME_PANEL_WIDTH){
            }
        }
    }

}
