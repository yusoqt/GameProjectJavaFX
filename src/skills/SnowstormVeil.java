package skills;

import effects.SlowEffect;
import characters.Character;

public class SnowstormVeil extends Skill {
    private int duration;
    private int accuracyReduction;

    public SnowstormVeil(String name, int cooldown, int duration, int accuracyReduction) {
        super(name, 15, cooldown, "Creates a veil of snow that reduces accuracy and deals frost damage."); 
        this.duration = duration;
        this.accuracyReduction = accuracyReduction;
    }

    @Override
    public void use(Character user, Character target) {
        // ลดความรุนแรงของสกิล
        int damage = Math.max((int)(user.getAtk() * 0.7) - target.getDef(), 1); // ลดค่าตัวคูณความเสียหาย
        target.takeDamage(damage);
        System.out.println(user.getName() + " uses Snowstorm Veil, dealing " + damage + " damage and reducing accuracy!");
        target.applyEffect(new SlowEffect(duration, accuracyReduction));
    }
}