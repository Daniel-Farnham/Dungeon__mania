package dungeonmania.entities.buildables;

import java.util.Map;
import dungeonmania.util.Position;

public class BuildableFactory {
    public Buildable createBuildable(String type, Position position, Map<String, Object> properties) {
        System.out.println("Type: " + type);
        System.out.println("Properties: " + properties);
        switch (type) {
        case "Shield":
            return new Shield(position, (int) properties.get("durability"), (double) properties.get("defence"));
        case "Bow":
            return new Bow(position, (int) properties.get("durability"));
        case "Sceptre":
            return new Sceptre(position, (int) properties.get("durability"),
                    (int) properties.get("mindControlDuration"));
        default:
            throw new IllegalArgumentException("Invalid buildable type: " + type);
        }
    }
}
