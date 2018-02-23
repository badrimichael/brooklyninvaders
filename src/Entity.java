import java.awt.*;

/*
An entity in this game is any graphic that has the following two abilities: movement and attack.
An attack is not an entity in this context because while it may be capable of movement, it can't attack
because it is the attack itself. The following objects are entities: Player.java and Enemy.java. Each level is
composed of multiple entities. All entities are capable of killing other entities and moving across the screen.
*/

public abstract class Entity {

    //Entities are capable of movement.
    int x, y;
    volatile boolean attack;

    //Entities are graphics.
    public void paint(Graphics2D graphic) {
    }

    //Entities are capable of attack.
    public void attack() {
    }
}
