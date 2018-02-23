import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
The player in a space invaders game is a human controlled graphic that is capable of attack and movement.
For attack specification see Attack.java. Movement of the player is controlled by two different keypresses.
The player can move left and right. When the player is destroyed the game is over.
The conditions of player destruction are specified in BrooklynInvaders.java. The image of the player is
determined by the player input (Male or Female character).
*/

public class Player extends Entity implements ActionListener {
    Game game;
    BufferedImage playerImage;
    Timer move = new Timer(10, this);
    boolean moveLeft, moveRight;
    long coolDown;
    int lives = 3;

    //The player spawns in the middle of the screen
    //with an image representing a student.
    public Player(Game game) {
        this.x = 462;
        this.y = 700;
        this.game = game;
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream
                    ("/res/student.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Player has to die when certain conditions are met.
    //Each time the player is destroyed, a life point is deducted.
    public void destroy() {
        lives--;
        System.out.println("Died due to attack.");
    }

    //Painting the Player is the same as displaying
    //it on screen. Image is chosen before the game starts.
    //The y-coordinate needs to take the width of the image
    //into consideration so it spawns in the center.
    public void paint(Graphics2D graphic) {
        graphic.drawImage(playerImage, x, y - 38, null);
    }

    //To paint the player attack, the
    //attack boolean must be true.
    public void attack() {
        attack = true;
        game.playerAttack.timer.start();
        game.playerAttack.x = game.player1.x;
    }

    //Two booleans are used to allow actionPerformed
    //to have two different functions.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (moveRight == true) {
            if (x > 916)
                x = 916;
            x = x + 8;
        } else if (moveLeft == true) {
            if (x < 8)
                x = 8;
            x = x - 8;
        }
    }

    //The input of the keyboard determines the movement of the player.
    //The left and right velocities must be the same for consistent
    //movement. The escape key pauses the game.
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
            moveRight = true;
            move.start();

        } else if (keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            moveLeft = true;
            move.start();

        } else if (keyEvent.getKeyCode() == keyEvent.VK_SPACE) {

            if (System.currentTimeMillis() > coolDown + 1000) {
                attack();
                coolDown = System.currentTimeMillis();
            }
        }/* else if (keyEvent.getKeyCode() == keyEvent.VK_ESCAPE) {
            if (!game.pause) {
                game.pause = true;
                game.timer.stop();
                move.stop();
                for(Enemy enemy: game.enemies)
                {
                    enemy.timer.stop();
                    enemy.enemyAttack.timer.stop();
                }
                game.playerAttack.timer.stop();

            } else if (game.pause) {
                game.pause = false;
                game.timer.start();
                move.start();
                for(Enemy enemy: game.enemies)
                {
                    if(enemy.alive = true)
                    enemy.timer.start();
                  //  if(enemy.attack == true)
                    enemy.enemyAttack.timer.start();
                }
                if(attack == true)
                game.playerAttack.timer.start();
            }
        }*/
    }

    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
            moveRight = false;
            move.stop();

        } else if (keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            moveLeft = false;
            move.stop();

        }
    }

    //Unused abstract method.
    public void keyTyped(KeyEvent keyEvent) {
    }
}

