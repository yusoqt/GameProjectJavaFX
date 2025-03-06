package enemies;

import java.util.List;
import skills.*;

public class VolcanoBoss extends BaseBoss {
    public VolcanoBoss() {
        super("Volcano Boss", 950, 95, 75, 65, List.of( 
            new LavaSurge(),
            new MoltenCore(),
            new EruptionSmash()
        ), null);
    }
}