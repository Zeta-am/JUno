package it.uniroma1.model.player;

/**
 * The {@code UserPlayer} class is the concrete class of {@link Player}.
 * <p> General utility methods are defined in the abstract class player.
 * It is used to distinguish the type of player</p>
 * @author Andrea Musolino
 */
public class UserPlayer
    extends Player {

    /**
     * Constructor
     * @param name the name of the player
     */
    public UserPlayer(String name) {
        super(name);
    }

}
