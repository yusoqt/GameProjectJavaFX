package effects;

import characters.Character;

public class ChillEffect extends BaseDotEffect {
    private int slowAmount;
    private boolean isFirstTick = true;
    
    public ChillEffect(int duration, int slowAmount) {
        super(0.0f, duration, "Chill");
        this.slowAmount = slowAmount;
        System.out.println("[DEBUG] Created Chill effect with duration: " + duration + ", slow amount: " + slowAmount);
    }
    
    @Override
    public void applyEffect(Character target) {
        int currentSpeed = target.getSpd();
        target.setSpd(currentSpeed - slowAmount);
        System.out.println("[DEBUG] " + target.getName() + "'s speed reduced by " + slowAmount);
        System.out.println("[DEBUG] Speed before: " + currentSpeed + ", after: " + target.getSpd());
    }
    
    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) return;
        
        System.out.println("[DEBUG] Processing Chill effect on " + target.getName());
        System.out.println("[DEBUG] Chill duration before: " + getDuration());
        
        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] Chill duration after: " + getDuration());
            
            if (getDuration() > 0) {
                System.out.println(target.getName() + " remains slowed. (" + getDuration() + " turns remaining)");
            } else {
                System.out.println("[DEBUG] Chill effect expired");
            }
        } else {
            isFirstTick = false;
            System.out.println("[DEBUG] First tick of Chill - not decrementing duration");
        }
    }
    
    @Override
    public void removeEffect(Character target) {
        System.out.println("[DEBUG] Removing Chill effect from " + target.getName());
        int currentSpeed = target.getSpd();
        target.setSpd(currentSpeed + slowAmount);
        System.out.println("[DEBUG] Restored " + slowAmount + " speed to " + target.getName());
        System.out.println(target.getName() + "'s speed returns to normal.");
    }
}