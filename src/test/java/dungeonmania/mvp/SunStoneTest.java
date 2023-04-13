package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SunStoneTest {
    @Test
    @Tag("SS-1")
    @DisplayName("Test player can pick up a SunStone")
    public void pickUpSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunstoneTest_pickUp", "c_sunstoneTest_pickUp");

        // move player to right
        res = dmc.tick(Direction.RIGHT);

        // assert SunStone picked up
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("SS-2")
    @DisplayName("Test SunStone can be used to open and walk through a door")
    public void useSunStoneOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_useSunStoneOpenDoor", "c_SunStoneTest_useSunStoneOpenDoor");

        // pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // walk through door and check SunStone is still there
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("SS-3")
    @DisplayName("Test SunStone counts towards the treasure goal")
    public void sunStoneTreasureGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_sunStoneTreasureGoal", "c_SunStoneTest_sunStoneTreasureGoal");

        // move player to right
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
  
        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("SS-4")
    @DisplayName("Test SunStone cannot be used to bribe mercenaries")
    public void sunStoneBribeMercenary() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_sunStoneBribeMercenary", "c_SunStoneTest_sunStoneBribeMercenary");

        // pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // attempt bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("SS-5")
    @DisplayName("Test SunStone can be used interchangeably with treasure or keys when building entities")
    public void buildShieldWithSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithSunStone",
                "c_SunStoneTest_BuildShieldWithSunStone");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    
        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
    
        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    
        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());
    
        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        // SunStone is still in inventory after use
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("SS-6")
    @DisplayName("Test building a sceptre with SunStone")
    public void buildSceptreWithSunStone() {
    DungeonManiaController dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildSceptreWithSunStone",
            "c_BuildablesTest_BuildSceptreWithSunStone");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    
    // Pick up Wood
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getInventory(res, "wood").size());

    // Pick up Treasure
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getInventory(res, "treasure").size());

    // Pick up SunStone
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

    // Build Sceptre
    assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
    res = assertDoesNotThrow(() -> dmc.build("sceptre"));
    assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

    // Materials used in construction disappear from inventory
    assertEquals(0, TestUtils.getInventory(res, "wood").size());
    assertEquals(0, TestUtils.getInventory(res, "key").size());
    assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
@Tag("S-1")
@DisplayName("Test the effects of the Sceptre controlling a Mercenary")
public void sceptreControlDuration() throws InvalidActionException {
    // Set up the dungeon and player position with a sceptre and mercenary
    DungeonManiaController dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d_sceptreTest_controlMercernary", "c_sceptreTest_controlMercernary");

    String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
    assertEquals(1, TestUtils.getEntities(res, "mercenary").size());
    
    // Pick up Wood
     res = dmc.tick(Direction.RIGHT);
     assertEquals(1, TestUtils.getInventory(res, "wood").size());
 
     // Pick up Treasure
     res = dmc.tick(Direction.RIGHT);
     assertEquals(1, TestUtils.getInventory(res, "treasure").size());
    
     // Pick up SunStone
     res = dmc.tick(Direction.RIGHT);
     assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

    // Build Sceptre
    assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
    res = assertDoesNotThrow(() -> dmc.build("sceptre"));
    assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

    // Use the sceptre on the mercenary
    res = assertDoesNotThrow(() -> dmc.interact(mercId));

    // Test if the mercenary is under control
    // Assuming you have a way to check if a mercenary is under control, like isControlled() method
    res = dmc.tick(Direction.RIGHT);
    assertEquals(0, res.getBattles().size());
}

}