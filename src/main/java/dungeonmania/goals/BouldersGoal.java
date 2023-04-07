package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.Switch;

public class BouldersGoal implements Goal {
    
    public boolean achieved(Game game) {
        return game.getMap().getEntities(Switch.class).stream().allMatch(Switch::isActivated);
    }

    
    public String toString(Game game) {
        return achieved(game) ? "" : ":boulders";
    }
}
