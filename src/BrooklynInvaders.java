import javax.swing.*;
import java.util.Stack;

/*
The main method starts the Brooklyn Invaders main menu. For main menu specification see Menu.java.
Brooklyn Invaders is a space invaders game with school themed players, enemies, and levels written
in the Java programming language with the Swing GUI (Graphical User Interface) toolkit. The player must
eliminate all of the enemies in a given level to proceed to the next one. There are 3 levels total and
as the player progresses, the levels increase in difficulty. Player death is a game over and the game must be restarted.
Player death occurs when all three of the player lives are lost or when an enemy reaches
the player. A player loses a life when hit with an enemy attack. All of the game logic is handled in Game.java.
This main method simply displays the window the game will run in, the splash screen, and the main menu.
*/

public class BrooklynInvaders {
    public static void main(String[] args) {
        //JFrame title constructor.
        JFrame window = new JFrame("Brooklyn Invaders");
        JLabel splashScreen = new JLabel(new ImageIcon(BrooklynInvaders.class.getResource
                ("res/splash_screen.jpg")));

        //Stack containing levels is populated.
        Stack<Game> levels = new Stack<>();
        Game level1 = new Game("Level 1");
        Game level2 = new Game("Level 2");
        Game level3 = new Game("Level 3");

        levels.push(level3);
        levels.push(level2);
        levels.push(level1);

        //Window details are below.
        //Window must NOT be resizable because
        //the player attack projectile
        //is always visible in any
        //window size greater than
        //1024x768.
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1024, 768);

        //Thread wait to show splash screen.
        //The splash screen is displayed for
        //however long the thread sleeps.
        window.add(splashScreen);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        //Remove splash screen, add main menu,
        //and reset JFrame so player sees it.
        //The stack containing all 3 levels are
        //passed to the main menu.
        Menu mainMenu = new Menu(window, "Main", levels);
        window.remove(splashScreen);
        window.add(mainMenu);
        window.revalidate();
        window.repaint();
    }
}
