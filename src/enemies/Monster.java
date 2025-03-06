package enemies;

import characters.Character;
import java.util.List;
import skills.Skill;
import game.Difficulty;
import game.DifficultyManager;
import java.util.Objects;

public class Monster extends Character {
    private static DifficultyManager diffManager;

    public static void setDifficultyManager(DifficultyManager manager) {
        diffManager = manager;
    }

    public Monster(String name, int hp, int atk, int def, int spd) {
        super(name, 
            (int)(hp * diffManager.getStatMultiplier()),    // ใช้ getStatMultiplier แทน getHealthMultiplier
            (int)(atk * diffManager.getDamageMultiplier()), // getDamageMultiplier คงเดิม
            (int)(def * diffManager.getStatMultiplier()),   // ใช้ getStatMultiplier แทน getDefenseMultiplier
            (int)(spd * diffManager.getStatMultiplier())    // ใช้ getStatMultiplier แทน getSpeedMultiplier
        );
    }
    
    public Monster(String name, int hp, int atk, int def, int spd, List<Skill> skills) {
        super(name, 
            (int)(hp * diffManager.getStatMultiplier()),    // แก้ไขเหมือนด้านบน
            (int)(atk * diffManager.getDamageMultiplier()),
            (int)(def * diffManager.getStatMultiplier()),
            (int)(spd * diffManager.getStatMultiplier()),
            skills
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Monster monster = (Monster) obj;
        return getName().equals(monster.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}