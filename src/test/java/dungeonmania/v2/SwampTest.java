package dungeonmania.v2;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.*;
import dungeonmania.mvp.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwampTest {
    @Test
    @DisplayName("Testing player swamp")
    public void playerSwamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("v2_swamp", "c_basicGoalsTest_enemy");
        // Get into the swamp
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        // Check if the player can't move 2 times
        Position prevPosition = TestUtils.getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(TestUtils.getPlayer(res).get().getPosition(), prevPosition);
        prevPosition = TestUtils.getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(TestUtils.getPlayer(res).get().getPosition(), prevPosition);

        // Check if it get out
        prevPosition = TestUtils.getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.UP);
        assertNotEquals(TestUtils.getPlayer(res).get().getPosition(), prevPosition);
    }

    @Test
    @DisplayName("Testing mercenary swamp")
    public void mercenarySwamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("v2_swamp", "c_basicGoalsTest_enemy");
        // Get into the swamp
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        // At this point, mercenary gets in
        res = dmc.tick(Direction.LEFT);

        Position prevPosition = TestUtils.getEntityPos(res, "mercenary");
        res = dmc.tick(Direction.UP);
        assertEquals(TestUtils.getEntityPos(res, "mercenary"), prevPosition);
        prevPosition = TestUtils.getEntityPos(res, "mercenary");
        res = dmc.tick(Direction.UP);
        assertEquals(TestUtils.getEntityPos(res, "mercenary"), prevPosition);

        // Check if it get out
        prevPosition = TestUtils.getEntityPos(res, "mercenary");
        res = dmc.tick(Direction.UP);
        assertNotEquals(TestUtils.getEntityPos(res, "mercenary"), prevPosition);
    }
}
