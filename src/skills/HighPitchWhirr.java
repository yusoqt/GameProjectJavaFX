package skills;

import characters.Character;
import effects.HighPitchWhirrEffect;

public class HighPitchWhirr extends Skill {
    public HighPitchWhirr() {
        super("High Pitch Whirr", 30, 4, "Emits a high-pitched sound that disorients the target");
    }
    
    @Override
    public void use(Character user, Character target) {
        if (!target.hasEffect("High Pitch Whirr")) {
            HighPitchWhirrEffect effect = new HighPitchWhirrEffect(2, 15);
            target.applyEffect(effect);
            System.out.println(user.getName() + " uses High Pitch Whirr on " + target.getName() + "!");
        } else {
            System.out.println("[DEBUG] Target already affected by High Pitch Whirr");
        }
    }
}