import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/*
A menu is not exclusive to a Space Invaders game, any video game can have a number of menus that have different
functions. A menu has button components that control different aspects of the game. For example, there is a button
to start the game when the player is ready to play and a button to exit the game when the player is finished playing.
The menu constructor specifies what kind of menu is to be displayed on the screen. Buttons that are used by multiple
menus are declared outside of the constructor. The main menu appears when the game is first started or when the player
chooses to return to it. The next level menu appears when the player completes a level. The game over menu appears when
the player loses all three lives. The game completed menu appears when all levels are completed.
*/

public class Menu extends JPanel {
    JButton mainMenu = new JButton("Main Menu");
    JButton exit = new JButton("Exit");
    JButton credits = new JButton("Credits");
    JButton submitScore = new JButton("Submit High Score");
    JButton howToPlay = new JButton("How To Play!");
    JFrame gameWindow;
    Stack<Game> levels;
    Backgrounds background = new Backgrounds("Menu");
    static ArrayDeque<Integer> scores = new ArrayDeque<>();
    static ArrayList<HighScore> scoreList;

    //One constructor for many different menus. Each menu has different buttons
    //and are created when different conditions are met.
    public Menu(JFrame window, String type, Stack<Game> levels) {
        this.levels = levels;
        this.gameWindow = window;

        //If the save file exists, load it.
        //If it doesn't, initialize a List of high scores with
        //"zero" values.
        if (new File("BrooklynInvadersHighScores").exists())
            try {
                FileInputStream fis = new FileInputStream
                        ("BrooklynInvadersHighScores");
                ObjectInputStream ois = new ObjectInputStream(fis);
                scoreList = (ArrayList<HighScore>) ois.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        else
            scoreList = new ArrayList<>(
                    Collections.nCopies(3, new HighScore("Empty", 0)));

        //Button action listeners.
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                System.exit(0);
            }
        });

        howToPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHowToPlay();
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMainMenu();
            }
        });

        submitScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HighScore score = new HighScore(nameEntry(), calculateScore());
                scoreList.add(score);
                Collections.sort(scoreList, HighScore::compareTo);
                Collections.reverse(scoreList);

                //Create save file.
                try {
                    FileOutputStream fos = new FileOutputStream
                            ("BrooklynInvadersHighScores");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(scoreList);
                    oos.close();
                    fos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                window.dispose();
                System.exit(0);
            }
        });

        //Main menu
        if (type.equals("Main")) {

            //Button declarations
            gameWindow.setTitle("Brooklyn Invaders Main Menu");
            JButton play = new JButton("Play");
            JButton highScores = new JButton("High Scores");

            //Uses Boxlayout layout manager.
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            //Add buttons and
            //set positions.
            setPosition(play);
            this.add(play);
            setPosition(highScores);
            this.add(highScores);
            setPosition(howToPlay);
            this.add(howToPlay);
            setPosition(credits);
            this.add(credits); //TODO: Not yet implemented.
            setPosition(exit);

            //Button ActionListeners
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });

            highScores.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addHighScore();
                }
            });
        }

        //Next level menu
        else if (type.equals("Next Level")) {
            gameWindow.setTitle("Next Level");
            JButton nextLevel = new JButton("Next Level");

            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            setPosition(nextLevel);
            this.add(nextLevel);
            setPosition(mainMenu);
            this.add(mainMenu, BorderLayout.CENTER);
            setPosition(credits);
            this.add(credits);
            setPosition(exit);
            this.add(exit);

            nextLevel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });
        }

        //Game over menu
        else if (type.equals("Game Over")) {
            gameWindow.setTitle("Game Over");

            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            setPosition(submitScore);
            this.add(submitScore);
            setPosition(credits);
            this.add(credits);
            setPosition(howToPlay);
            this.add(howToPlay);
            setPosition(exit);
            this.add(exit);

        }

        //Game completion menu
        else if (type.equals("Game Completed")) {
            gameWindow.setTitle("Game Completed");

            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            setPosition(submitScore);
            this.add(submitScore);
            setPosition(credits);
            this.add(credits);
            setPosition(mainMenu);
            this.add(mainMenu);
            setPosition(exit);
            this.add(exit);
        }

        //High score menu.
        else if (type.equals("High Scores")) {

            gameWindow.setTitle("High Scores");
            background = new Backgrounds("Score Screen");

            JButton score1 = new JButton(scoreList.get(0).getName() + " - " + scoreList.get(0).getHighScore());
            JButton score2 = new JButton(scoreList.get(1).getName() + " - " + scoreList.get(1).getHighScore());
            JButton score3 = new JButton(scoreList.get(2).getName() + " - " + scoreList.get(2).getHighScore());

            setPosition(score1);
            this.add(score1);
            setPosition(score2);
            this.add(score2);
            setPosition(score3);
            this.add(score3);
            setPosition(mainMenu);
            mainMenu.setForeground(Color.RED);
            this.add(mainMenu);
        }

        //How to play menu.
        else if (type.equals("How To Play")) {

            gameWindow.setTitle("How To Play!");
            background = new Backgrounds("How To Play Screen");

            this.setLayout(new BorderLayout());
            mainMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
            setPosition(mainMenu);
            mainMenu.setForeground(Color.WHITE);
            this.add(mainMenu, BorderLayout.SOUTH);
        }
    }


    //Display background images on menus.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.paint(g);
    }

    //Pops the next level and adds it to the game window.
    //The current menu is removed and that level is repainted
    //in its own thread. If there is no level left in the stack,
    //the player is taken to the game completed menu.
    public void startGame() {
        if (!levels.isEmpty()) {
            Game level = levels.pop();
            gameWindow.setTitle("Brooklyn Invaders");
            gameWindow.remove(this);
            gameWindow.add(level);
            gameWindow.revalidate();
            gameWindow.repaint();
            level.requestFocusInWindow();
            level.timer.start();

            Thread level1Thread = new Thread() {
                public void run() {
                    level.start();
                    while (level.gameLoop)
                        while (level.pause == false && level.gameLoop == true) {
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                            level.repaint();
                        }
                    endGame(level);
                }
            };
            level1Thread.start();
        } else
            gameCompleted();
    }

    //When a level is completed, the player is
    //taken to the next level menu.
    public void endGame(Game level) {
        if (level.player1.lives <= 0) {
            gameOver(level);
        } else {
            scores.addFirst(level.score + level.timeLeft * 10);
            JLabel score = new JLabel("Level Score: " + scores.getFirst().toString());
            score.setForeground(Color.white);
            gameWindow.remove(level);
            Menu currentMenu = new Menu(gameWindow, "Next Level", levels);
            score.setAlignmentX(CENTER_ALIGNMENT);
            currentMenu.add(score);
            gameWindow.add(currentMenu);
            gameWindow.revalidate();
            gameWindow.repaint();
        }
    }

    //When the player loses all three lives, the
    //player is taken to the game over screen.
    public void gameOver(Game level) {
        gameWindow.remove(level);
        JLabel score = new JLabel("Total Score: " + calculateScore().toString());
        score.setAlignmentX(CENTER_ALIGNMENT);
        score.setForeground(Color.white);
        Menu currentMenu = new Menu(gameWindow, "Game Over", levels);
        currentMenu.add(score);
        gameWindow.add(currentMenu);
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    //When all levels in the stack are completed,
    //the player is taken to the game completed screen.
    public void gameCompleted() {
        JLabel score = new JLabel("Total Score: " + calculateScore().toString());
        score.setAlignmentX(CENTER_ALIGNMENT);
        score.setForeground(Color.white);
        gameWindow.remove(this);
        Menu currentMenu = new Menu(gameWindow, "Game Completed", levels);
        currentMenu.add(score);
        gameWindow.add(currentMenu);
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    //When the player presses the menu button,
    //they are taken to the main menu.
    public void addMainMenu() {
        gameWindow.remove(this);
        gameWindow.add(new Menu(gameWindow, "Main", levels));
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    //When the player presses the high score button,
    //they are taken to the high score menu.
    public void addHighScore() {
        gameWindow.remove(this);
        gameWindow.add(new Menu(gameWindow, "High Scores", levels));
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    //When the player presses how to
    //play, they are taken to the how to
    //play menu.
    public void addHowToPlay() {
        gameWindow.remove(this);
        gameWindow.add(new Menu(gameWindow, "How To Play", levels));
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    //Iterates through scores list and
    //generates a total score integer.
    public Integer calculateScore() {
        Integer totalScore = 0;
        for (Integer score : scores)
            totalScore = totalScore + score;
        return totalScore;
    }

    //Align the buttons and
    //make them transparent.
    public void setPosition(JButton button) {
        button.setPreferredSize(new Dimension(1024, 200));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.black);
    }

    //Handles name entry dialogue
    //and validation.
    public String nameEntry() {
        String name = JOptionPane.showInputDialog("What is your name? (4 Letters Max)",
                "Player 1");
        if (name == null || name.length() > 4 || !name.matches("[A-Za-z]*"))
            name = nameEntry();
        return name;
    }
}
