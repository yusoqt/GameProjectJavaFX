package skills;

import characters.Character;
import effects.ChillEffect;

public class IceJavelin extends Skill {
    private float damageMultiplier = 1.2f;
    private int baseDamage = 25; 

    public IceJavelin() {
        super("Ice Javelin", 25, 3, "Throws a javelin of ice that can slow the target");
    }
    
    private int calculateDamage(Character user, Character target) {
        return Math.max((int)(baseDamage + (user.getAtk() * damageMultiplier) - (target.getDef() * 0.1)), 1);
    }
    
    @Override
    public void use(Character user, Character target) {
        int damage = calculateDamage(user, target);
        target.takeDamage(damage);
        System.out.println(user.getName() + " throws an Ice Javelin at " + target.getName() + ", dealing " + damage + " damage.");
        
        // Apply slow effect if not already slowed
        if (!target.hasEffect("Chill")) {
            ChillEffect chill = new ChillEffect(2, 5); 
            target.applyEffect(chill);
            System.out.println(target.getName() + " is slowed by the impact of the Ice Javelin!");
        }
    }
}