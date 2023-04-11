package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;
import org.json.JSONObject;

public class PlayerState {
    private Player player;
    private boolean isInvincible = false;
    private boolean isInvisible = false;

    public PlayerState(Player player) {
        this.player = player;
    }

    public JSONObject toJsonObject() {
        JSONObject ret = new JSONObject();
        ret.put("invisible", isInvisible);
        ret.put("invincible", isInvincible);
        return ret;
    }

    public boolean isInvincible() {
        return isInvincible;
    };

    public boolean isInvisible() {
        return isInvisible;
    };

    public void transitionInvisible() {
        this.isInvisible = true;
    }

    public void transitionInvincible() {
        this.isInvincible = true;
    }

    public void transitionBase() {
        this.isInvisible = false;
        this.isInvincible = false;
    }
}
