package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Treasure extends Entity implements InventoryItem, Collectable {
    public Treasure(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            onPlayerCollect(map, (Player) entity);
        }
    }

    @Override
    public void onPlayerCollect(GameMap map, Player player) {
        if (player.pickUp(this)) {
            map.destroyEntity(this);
        }
    }
}
