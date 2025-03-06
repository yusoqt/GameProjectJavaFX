package effects;

import characters.Character;

public class BurnEffect extends BaseDotEffect {
    private float damage;
    private boolean isFirstTick = true;
    
    public BurnEffect(int duration, float damage) {
        super(damage, duration, "Burn");
        this.damage = damage;
        System.out.println("[DEBUG] Created Burn effect with duration: " + duration);
    }
    
    @Override
    public void applyEffect(Character target) {
        System.out.println(target.getName() + " is burning! (Duration: " + getDuration() + " turns)");
    }
    
    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) {
            return;
        }
        
        System.out.println("\n[DEBUG] Processing Burn effect on " + target.getName());
        System.out.println("[DEBUG] Duration before: " + getDuration());
        
        target.takeDamage((int)damage);
        System.out.println(target.getName() + " takes " + damage + " burn damage!");
        
        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] Duration after: " + getDuration());
            
            if (getDuration() > 0) {
                System.out.println("(" + getDuration() + " turns of burn remaining)");
            } else {
                System.out.println("[DEBUG] Burn effect expired");
            }
        } else {
            isFirstTick = false;
            System.out.println("[DEBUG] First tick - starting burn effect");
        }
    }
    
    @Override
    public void removeEffect(Character target) {
        System.out.println("[DEBUG] Removing burn effect from " + target.getName());
        System.out.println(target.getName() + "'s burn effect has been extinguished!");
    }
}