package enemies;

import java.util.List;
import characters.Character;
import game.DifficultyManager;
import skills.Skill;

public class BaseBoss extends Character {
    private static DifficultyManager diffManager;
    private String fieldEffect;
    
    public static void setDifficultyManager(DifficultyManager manager) {
        diffManager = manager;
    }
    
    public BaseBoss(String name, int hp, int atk, int def, int spd, List<Skill> skills, String fieldEffect) {
        // ถ้า diffManager ไม่เป็น null ให้ปรับค่าสถิติตามความยาก
        super(name,
            diffManager != null ? (int)(hp * diffManager.getStatMultiplier() * 1.2) : hp,   // บอสจะมี HP มากกว่ามอนสเตอร์ 20%
            diffManager != null ? (int)(atk * diffManager.getDamageMultiplier() * 1.2) : atk, // บอสจะมี ATK มากกว่ามอนสเตอร์ 20%
            diffManager != null ? (int)(def * diffManager.getStatMultiplier() * 1.1) : def,   // บอสจะมี DEF มากกว่ามอนสเตอร์ 10%
            diffManager != null ? (int)(spd * diffManager.getStatMultiplier()) : spd,   // SPD ตามปกติ
            skills
        );
        this.fieldEffect = fieldEffect;
    }
    
    public String getFieldEffect() {
        return fieldEffect;
    }
}