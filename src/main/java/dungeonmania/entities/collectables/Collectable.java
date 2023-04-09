package dungeonmania.entities.collectables;

import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;

public interface Collectable {
    void onPlayerCollect(GameMap map, Player player);
}
