import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.SecureRandom;
import java.util.Random;

/*
The logic behind whatever happens on screen is expressed in this class. Every level is a Game object because each Game
object contains the enemies, the player, and the attacks. The layout and image of the enemies is declared in the
constructor. Game logic is handled in the repaint method, such as hit boxes and drawing of components. A hit box detects
the collision of components. A component is something drawn on a JPanel, in this class a component is an enemy, player,
or attack. When these components interact with each other on screen, there must be a result.
See Player.java, Enemy.java, and Attack.java,
*/

public class Game extends JPanel implements ActionListener {
    Player player1 = new Player(this);
    Enemy[] enemies;
    Attack playerAttack = new Attack(this, player1, "Player");
    Timer timer = new Timer(1000, this);
    Backgrounds background;
    int timeLeft = 60;
    int score = 0;
    int maxScore;
    String level;
    volatile Boolean gameLoop = true;
    volatile Boolean pause = false;


    //Default constructor creates enemies, initializes keyboard input,
    //sets focus to the level.
    public Game(String type) {
        this.level = type;
        background = new Backgrounds(type);
        //The enemy array is populated and each enemy
        //location is determined.
        if (level.equals("Level 1")) {
            enemies = new Enemy[10];
            for (int i = 0; i < enemies.length; i++) {
                enemies[i] = new Enemy(this, i * 70, 25);
                enemies[i].enemyAttack = new Attack(this, enemies[i], "Enemy");
            }
        } else if (level.equals("Level 2")) {
            enemies = new Enemy[16];
            for (int i = 0; i < 4; i++) {
                enemies[i] = new Enemy(this, i * 70, 25);
                enemies[i].enemyAttack = new Attack(this, enemies[i], "Enemy");

                enemies[i + 4] = new Enemy(this, i * 70, 175);
                enemies[i + 4].enemyAttack = new Attack(this, enemies[i + 4], "Enemy");

                enemies[i + 8] = new Enemy(this, 949 - (i * 70), 100);
                enemies[i + 8].enemyAttack = new Attack(this, enemies[i + 8], "Enemy");

                enemies[i + 12] = new Enemy(this, 949 - (i * 70), 250);
                enemies[i + 12].enemyAttack = new Attack(this, enemies[i + 12], "Enemy");
            }
        } else if (level.equals("Level 3")) {
            enemies = new Enemy[30];
            for (int i = 0; i < 5; i++) {
                enemies[i] = new Enemy(this, i * 70, 25);
                enemies[i].enemyAttack = new Attack(this, enemies[i], "Enemy");

                enemies[i + 5] = new Enemy(this, 410 - (i * 70), 100);
                enemies[i + 5].enemyAttack = new Attack(this, enemies[i + 5], "Enemy");

                enemies[i + 10] = new Enemy(this, i * 70, 175);
                enemies[i + 10].enemyAttack = new Attack(this, enemies[i + 10], "Enemy");

                enemies[i + 15] = new Enemy(this, 950 - (i * 70), 25);
                enemies[i + 15].enemyAttack = new Attack(this, enemies[i + 15], "Enemy");

                enemies[i + 20] = new Enemy(this, 480 + (i * 70), 100);
                enemies[i + 20].enemyAttack = new Attack(this, enemies[i + 20], "Enemy");

                enemies[i + 25] = new Enemy(this, 950 - (i * 70), 175);
                enemies[i + 25].enemyAttack = new Attack(this, enemies[i + 25], "Enemy");
            }

        }
        maxScore = enemies.length * 100;
        addKeyListener(new KeyListener() {
            //Unused abstract method.
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent event) {
                player1.keyReleased(event);
            }

            @Override
            public void keyPressed(KeyEvent event) {
                player1.keyPressed(event);
            }
        });
        setFocusable(true);
    }

    //Starts enemy movement.
    //TODO: Add more?
    public void start() {
        for (Enemy enemy : enemies) {
            enemy.timer.start();
        }
    }

    //The paint method displays all of the game
    //entities on the screen.
    @Override
    public void paint(Graphics graphic) {
        super.paint(graphic);
        Graphics2D graphic2d = (Graphics2D) graphic;

        //Displays background image.
        background.paint(graphic2d);

        //Player is displayed.
        player1.paint(graphic2d);

        //Each enemy is displayed.
        for (Enemy enemy : enemies) {
            enemy.paint(graphic2d);
        }

        //When the max score is reached, level complete and
        //move onto the next level.
        if (score == maxScore || player1.lives <= 0) {
            gameLoop = false;
        }

        //Player attack is always displayed,
        //but most of the time it is offscreen.
        playerAttack.paint(graphic2d);

        //The enemy's attacks are only
        //painted when their attack boolean
        //is true.
        for (Enemy enemy : enemies) {
            if (enemy.attack == true)
                enemy.enemyAttack.paint(graphic2d);
        }

        //Enemy attack patterns are based on
        //the amount of time left to score
        //extra points.
        if (timeLeft % 3 == 0 && timeLeft < 60) {
            int element = randomElement();
            if (element % 3 == 0)
                enemies[element].attack(graphic2d);
        }

        //Life and score counter.
        graphic.drawString("Lives: " + player1.lives, 20, 20);
        graphic.drawString("Score: " + score, 70, 20);
        graphic.drawString("Time: " + timeLeft, 140, 20);

        //Enhanced for loop handles hitboxes.
        for (Enemy enemy : enemies) {
            if (playerAttack.x > enemy.x - 60 &&
                    playerAttack.x < enemy.x + 20 && playerAttack.y == enemy.y) {
                enemy.destroy();
                playerAttack.destroy();
            }
            if (enemy.enemyAttack.x > player1.x - 40 &&
                    enemy.enemyAttack.x < player1.x + 40 && enemy.enemyAttack.y == player1.y) {
                player1.destroy();
            }
        }

        //If a single enemy makes it
        //on to the player's x-axis
        //the player is completely
        //destroyed. Game over.
        for (Enemy enemy : enemies) {
            if (enemy.y > 600 && enemy.y < 1000) {
                System.out.println("Died due to enemy range.");
                player1.destroy();
                player1.destroy();
                player1.destroy();
            }
        }
    }

    //Decreases timer by 1 per second,
    //if the timer is 0, it stays at 0.
    public void actionPerformed(ActionEvent e) {
        if (timeLeft > 0)
            timeLeft--;
        else
            timeLeft = 0;
    }

    //Random element for enemies array
    //is chosen by a secure random number
    //generator.
    public int randomElement() {
        int length = enemies.length;
        Random random = new SecureRandom();
        return random.nextInt(length);
    }
}



