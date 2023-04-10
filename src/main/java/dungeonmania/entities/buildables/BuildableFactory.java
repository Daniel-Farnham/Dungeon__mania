package dungeonmania.entities.buildables;

import java.util.Map;
import dungeonmania.util.Position;

public class BuildableFactory {
    public Buildable createBuildable(String type, Position position, Map<String, Object> properties) {
        switch (type) {
            case "Shield":
                return new Shield(position, (int) properties.get("durability"), (double) properties.get("defence"));
            case "Bow":
                return new Bow(position, (int) properties.get("durability"));
            default:
                throw new IllegalArgumentException("Invalid buildable type: " + type);
        }
    }
}
