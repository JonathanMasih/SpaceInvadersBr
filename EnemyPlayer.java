import java.awt.Point;
import java.awt.*;

public class EnemyPlayer extends Player{


    public EnemyPlayer(Point startPos) {
        super(startPos);
        //TODO Auto-generated constructor stub
    }

       /**
     * Set the Image to be used by the player Object 
     */
    //@Override
    public static void loadPlayerPic() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Player.playerImage = toolkit.getImage("alienshipPlayerTwo.png");
    }

    @Override
    public void run(){
        
    }
    
}
