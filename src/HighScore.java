import java.io.Serializable;
import java.util.Comparator;

/*
A high score contains the name of the player and the score they achieved in a particular video game.
High scores are worthless without a name attached to the score because players want to be recognized for
their ability to achieve the highest score possible. High scores provide replay value and a reason to compete
among others.
*/

public class HighScore implements Comparator<HighScore>, Serializable {
    //A high score is a score
    //attached to the player name.
    Integer highScore;
    String name;

    //Constructor to create a high score.
    HighScore(String name, Integer highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    //Get methods.
    public Integer getHighScore() {
        return highScore;
    }

    public String getName() {
        return name;
    }

    //Set methods.
    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Compare and compareTo methods
    //are necessary to sort the scores.
    public int compare(HighScore first, HighScore second) {
        return first.compareTo(second);
    }

    public int compareTo(HighScore target) {
        if (this.getHighScore() > target.getHighScore())
            return 1;
        else if (this.getHighScore() < target.getHighScore())
            return -1;
        else
            return 0;
    }
}

