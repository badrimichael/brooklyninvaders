import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/*
An enemy in a space invaders game attempts to destroy the player with an attack or by colliding with the player model.
When the player is destroyed the game is over but enemies are easily destroyed, so there are always multiple enemies
on screen. When a single enemy is destroyed, the player gets a predetermined amount of points.
When all enemies are destroyed, the level is completed and the player moves on to the next one.
*/

public class Enemy extends Entity implements ActionListener {
    int xVelocity = 5;
    boolean alive = true;
    Timer timer = new Timer(15, this);
    Game game;
    BufferedImage[] imageArray = new BufferedImage[3];
    BufferedImage enemyImage;
    Attack enemyAttack;

    //Enemies move immediately when game starts and the enemy
    //images are randomly assigned.
    public Enemy(Game game, int xPos, int yPos) {
        this.game = game;
        x = xPos;
        y = yPos;
        try {
            imageArray[0] = ImageIO.read(getClass().getResourceAsStream
                    ("/res/admin.png"));
            imageArray[1] = ImageIO.read(getClass().getResourceAsStream
                    ("/res/trustee.png"));
            imageArray[2] = ImageIO.read(getClass().getResourceAsStream
                    ("/res/dean.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        enemyImage = imageArray[randomEnemy()];
    }

    //When the timer starts, the enemy moves a certain number
    //of pixels per timer delay. If the enemy hits the side of
    //the screen, it appears on the beginning of the next row.
    public void actionPerformed(ActionEvent event) {
        if (game.level.equals("Level 1")) {
            if (x < 0 || x > 949) {
                xVelocity = -xVelocity;
                y = y + 40;
            }
            if (x == 875 && xVelocity > 0) {
                x = 900;
                y = y + 40;
            }
            if (x == 75 && xVelocity < 0) {
                x = 50;
                y = y + 40;
            }
            x = x + xVelocity;
        } else if (game.level.equals("Level 2")) {
            if (x < 0 || x > 949) {
                xVelocity = -xVelocity;
            }
            x = x + xVelocity;
        } else if (game.level.equals("Level 3")) {
            if (x < 0 || x > 950)
                xVelocity = -xVelocity;

            if (y != 100)
                if (x == 405 || x == 545)
                    xVelocity = -xVelocity;

            x = x + xVelocity;
        }
    }

    //Painting an enemy is the same as displaying it on screen.
    //Each enemy gets its x and y position,, as
    //well as random image from the constructor.
    public void paint(Graphics2D graphic) {
        graphic.drawImage(enemyImage, x, y, null);
        if (!enemyAttack.timer.isRunning() && game.pause == false) {
            enemyAttack.x = x;
            enemyAttack.y = y;
        }
    }

    //The random image for the enemy is selected
    //by a secure random number generator and is
    //returned.
    public int randomEnemy() {
        int length = imageArray.length;
        Random random = new SecureRandom();
        return random.nextInt(length);
    }

    //Enemies die by being pushed off of the visible window.
    //Score is added when enemy is destroyed.
    public void destroy() {
        alive = false;
        timer.stop();
        y = 1000;
        game.score += 100;
    }

    //Attacks are displayed when the timer starts.
    public void attack(Graphics2D graphic) {
        attack = true;
        enemyAttack.timer.start();
        enemyAttack.paint(graphic);
    }
}
