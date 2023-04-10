package dungeonmania.v2;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.mvp.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemiesGoalTest {

    @Test
    @DisplayName("Test achieving a basic enemy goal")
    public void enemies() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemy_copy", "mvp_enemyGoal");
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // move player to left, collect the sword
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // move player to up, destroy the spawner
        res = dmc.tick(Direction.LEFT);
        // cardinally adjacent: true, has sword: true
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));
        // move player to left, kill spider
        res = dmc.tick(Direction.LEFT);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @DisplayName("Test a basic enemy goal fails")
    public void enemiesFail() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemy", "mvp_enemyGoal");

        // move player to left, collect the sword
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // move player to up, destroy the spawner
        res = dmc.tick(Direction.UP);

        // // move player to left, kill spider
        // res = dmc.tick(Direction.LEFT);

        // assert goal does not met
        assertEquals(":enemies", TestUtils.getGoals(res));
    }
}
