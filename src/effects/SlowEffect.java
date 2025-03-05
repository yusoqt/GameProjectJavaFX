package effects;

import characters.Character;

public class SlowEffect extends BaseDotEffect {
    private int speedReduction;
    private boolean isFirstTick = true;
    
    public SlowEffect(int duration, int speedReduction) {
        super(0.0f, duration, "Slow");
        this.speedReduction = speedReduction;
        System.out.println("[DEBUG] Created Slow effect with duration: " + duration + ", speed reduction: " + speedReduction);
    }
    
    @Override
    public void applyEffect(Character target) {
        if (target.hasEffect("Slow")) {
            System.out.println("[DEBUG] Target already affected by Slow");
            return;
        }
        
        int currentSpeed = target.getSpd();
        target.setSpd(currentSpeed - speedReduction);
        System.out.println("[DEBUG] " + target.getName() + "'s speed reduced by " + speedReduction);
        System.out.println("[DEBUG] Speed before: " + currentSpeed + ", after: " + target.getSpd());
    }
    
    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) return;
        
        System.out.println("[DEBUG] Processing Slow effect");
        System.out.println("[DEBUG] Duration before: " + getDuration());
        
        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] Duration after: " + getDuration());
            
            if (getDuration() > 0) {
                System.out.println(target.getName() + " remains slowed. (" + getDuration() + " turns remaining)");
            } else {
                System.out.println("[DEBUG] Slow effect expired");
            }
        } else {
            isFirstTick = false;
            System.out.println("[DEBUG] First tick - not decrementing duration");
        }
    }
    
    @Override
    public void removeEffect(Character target) {
        System.out.println("[DEBUG] Removing Slow effect from " + target.getName());
        target.setSpd(target.getSpd() + speedReduction);
        System.out.println("[DEBUG] Restored " + speedReduction + " speed to " + target.getName());
        System.out.println(target.getName() + "'s movement speed returns to normal.");
    }
}
