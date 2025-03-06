package skills;

import characters.Character;
import effects.SnowfallAuraEffect;

public class SnowfallAura extends Skill {
    private int healAmount;
    
    public SnowfallAura() {
        super("Snowfall Aura", 20, 5, "Emits a chilling aura that heals the Wisp slightly and continuously damages enemies with biting snow.");
        this.healAmount = 10;
    }
    
    @Override
    public void use(Character user, Character target) {
        // Heal user
        user.heal(healAmount);
        System.out.println(user.getName() + " heals for " + healAmount + " HP.");
        System.out.println(user.getName() + " is enveloped in a Snowfall Aura and heals " + healAmount + " HP.");
        
        // Only apply new effect if target doesn't have it
        if (!target.hasEffect("Snowfall Aura")) {
            SnowfallAuraEffect effect = new SnowfallAuraEffect(3, 5);
            target.applyEffect(effect);
            System.out.println(target.getName() + " is battered by biting snow from the aura!");
        } else {
            System.out.println("[DEBUG] Skipping Snowfall Aura application - target already affected");
        }
    }
}