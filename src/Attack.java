import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/*
An attack object in a space invaders game is the main way the player interacts with the enemies. Both the player
and the enemies are capable of attacking with a projectile. A player is always capable of attacking whereas the enemy
may or may not be capable of attacking. The player's attack corresponds to a key press and the enemy's attack is
timer based. When the projectile hits an enemy or a player, they are destroyed.
*/

public class Attack implements ActionListener {
    int x = 0, y = 0;
    Timer timer = new Timer(5, this);
    Entity entity;
    String entityType;
    Game game;
    BufferedImage[] enemyImageArray = new BufferedImage[2];
    BufferedImage playerProjectile;
    BufferedImage enemyProjectile;

    //Attack starts at player's x position and y position.
    //The attack is instantiated with one of three images.
    //Different entities get different graphics.
    public Attack(Game game, Entity entity, String entityType) {
        this.entity = entity;
        this.entityType = entityType;
        this.game = game;
        x = entity.x;
        y = entity.y;

        if (entityType.equals("Enemy"))
            try {
                enemyImageArray[0] = ImageIO.read(getClass().getResourceAsStream(
                        "/res/fac_projectile_pin.png"));
                enemyImageArray[1] = ImageIO.read(getClass().getResourceAsStream
                        ("/res/fac_projectile_clip.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        enemyProjectile = enemyImageArray[randomProjectile()];

        if (entityType.equals("Player")) {
            try {
                playerProjectile = ImageIO.read(getClass().getResourceAsStream
                        ("/res/stu_projectile_pencil.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //When the timer is started (in paint method), the attack moves a
    //certain number of pixels per timer delay. If the attack is on
    //screen, it moves slowly. The attack is destroyed if it
    //is off screen. Enemy attacks go down towards the player,
    //while player attacks go upwards towards the enemies.
    public void actionPerformed(ActionEvent event) {
        if (entityType.equals("Player"))
            if (y > -100) {
                y = y - 5;
            } else {
                destroy();
            }
        else if (entityType.equals("Enemy")) {
            if (y < 800) {
                y = y + 5;
            } else
                destroy();
        }
    }

    //Using a secure random number generator, a random element of the
    //imageArray is chosen and returned.
    public int randomProjectile() {
        int length = enemyImageArray.length;
        Random random = new SecureRandom();
        return random.nextInt(length);
    }

    //Painting the attack is the same as displaying it on screen. Starting the
    //timer is the same as giving the attack an initial velocity.
    public void paint(Graphics2D graphic) {
        //  timer.start();
        if (entityType.equals("Player"))
            graphic.drawImage(playerProjectile, x + 38, y + 50, null);
        else
            graphic.drawImage(enemyProjectile, x, y, null);
    }

    //The attack must be destroyed or reset when it
    //hits an enemy or player. This is done by stopping
    //the timer and resetting its position.
    public void destroy() {
        if (entityType.equals("Enemy"))
            enemyProjectile = enemyImageArray[randomProjectile()];
        entity.attack = false;
        timer.stop();
        x = entity.x;
        y = entity.y;
    }
}
