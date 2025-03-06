package enemies;

import characters.Character;
import java.util.List;
import skills.Skill;
import game.Difficulty;
import game.DifficultyManager;

public class Monster extends Character {
    private static DifficultyManager diffManager;

    public static void setDifficultyManager(DifficultyManager manager) {
        diffManager = manager;
    }

    public Monster(String name, int hp, int atk, int def, int spd) {
        super(name, (int)(hp * 0.5), (int)(atk * 0.5), (int)(def * 0.7), spd);
    }
    
    public Monster(String name, int hp, int atk, int def, int spd, List<Skill> skills) {
        super(name, (int)(hp * 0.5), (int)(atk * 0.5), (int)(def * 0.7), spd, skills);
    }
}