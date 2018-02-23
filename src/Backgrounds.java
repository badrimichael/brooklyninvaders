import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
The background of a 2D video game sits behind the moving graphics on the screen. It can be, and usually is,
a static image. Backgrounds are important because they can set the tone for the level. For example,
if the player arrives at the final level of a 2D game, the background usually is far more complex than
the background of the first level to indicate progression. In this game, depending on the context
of the screen, the background will be a different image file.
*/

public class Backgrounds extends JPanel {
    Image backgroundImage;

    //Depending on the context, the background
    //image will be different.
    public Backgrounds(String context) {
        try {
            if (context.equals("Menu"))
                backgroundImage = ImageIO.read(getClass().getResourceAsStream
                        ("/res/background2.png"));

            else if (context.equals("Level 1"))
                backgroundImage = ImageIO.read(getClass().getResourceAsStream
                        ("/res/classroom_bg_light.png"));

            else if (context.equals("Level 2"))
                backgroundImage = ImageIO.read(getClass().getResourceAsStream
                        ("/res/classroom_bg_light.png"));

            else if (context.equals("Level 3"))
                backgroundImage = ImageIO.read(getClass().getResourceAsStream
                        ("/res/classroom_bg_light.png"));

            else if (context.equals("Score Screen"))
                backgroundImage = ImageIO.read(getClass().getResourceAsStream
                        ("/res/result_bg.png"));

            else if (context.equals("How To Play Screen"))
                backgroundImage = ImageIO.read(getClass().getResourceAsStream
                        ("/res/howtoplay.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //To display the background image,
    //it must be painted like any other JPanel.
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }
}
