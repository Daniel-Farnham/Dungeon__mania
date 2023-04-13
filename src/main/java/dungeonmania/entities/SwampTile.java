package dungeonmania.entities;
import dungeonmania.util.Position;
import dungeonmania.map.GameMap;

public class SwampTile extends Entity {
    public SwampTile(Position position) {
        super(position.asLayer(Entity.FLOOR_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        entity.setSwampCounter(1);
    }
}
