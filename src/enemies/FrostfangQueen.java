package enemies;

import java.util.List;
import skills.*;

public class FrostfangQueen extends BaseBoss {
    public FrostfangQueen() {
        super("Frostfang Queen", 900, 90, 70, 85, List.of( 
            // ปรับค่าพารามิเตอร์ให้น้อยลงเพื่อลดความรุนแรงของสกิล
            new SnowstormVeil("Snowstorm Veil", 4, 3, 15), // ลดความเสียหายและระยะเวลาของ effect
            new PowerSurge("Power Surge", 4, 3, 20) // ลดความเสียหาย
        ), null);
    }
}