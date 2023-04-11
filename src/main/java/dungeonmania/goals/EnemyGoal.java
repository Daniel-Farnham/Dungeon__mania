package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemyGoal implements Goal {
    private final int target;

    public EnemyGoal(int target) {
        this.target = target;
    }

    public boolean achieved(Game game) {
        return (game.getMap().getEntities(ZombieToastSpawner.class).stream().count() == 0
                && game.getKillScore() >= target);
    }

    public String toString(Game game) {
        return achieved(game) ? "" : ":enemies";
    }
}
