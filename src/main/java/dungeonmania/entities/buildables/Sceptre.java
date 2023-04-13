package dungeonmania.entities.buildables;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Position;

public class Sceptre extends Buildable {
    private int duration;

    public Sceptre(Position position, int durability, int duration) {
        super(position, durability);
        this.duration = duration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    public int getDuration() {
        return duration;
    }

}
