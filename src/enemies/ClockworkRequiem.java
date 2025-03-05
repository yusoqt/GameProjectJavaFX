package enemies;

import java.util.List;
import skills.*;

public class ClockworkRequiem extends BaseBoss {
    public ClockworkRequiem() {
        super("Clockwork Requiem", 900, 90, 75, 80, List.of(
            new TimeDistortion(),
            new GearCannon()
        ), null);
    }
}