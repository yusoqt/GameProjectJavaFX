package effects;

import characters.Character;

public class SnowfallAuraEffect extends BaseDotEffect {
    private int speedReduction;
    private boolean isFirstTick = true;
    
    public SnowfallAuraEffect(int duration, int speedReduction) {
        super(0.0f, duration, "Snowfall Aura");
        this.speedReduction = speedReduction;
        System.out.println("[DEBUG] Created Snowfall Aura effect with duration: " + duration);
    }
    
    @Override
    public void applyEffect(Character target) {
        if (!target.hasEffect("Snowfall Aura")) {
            target.setSpd(target.getSpd() - speedReduction);
            System.out.println(target.getName() + " is slowed by the snowfall!");
            System.out.println("[DEBUG] Speed reduced by: " + speedReduction);
        } else {
            System.out.println("[DEBUG] Target already under Snowfall Aura effect");
        }
    }
    
    @Override
    public void removeEffect(Character target) {
        target.setSpd(target.getSpd() + speedReduction);
        System.out.println("[DEBUG] Removing Snowfall Aura effect from " + target.getName());
        System.out.println("[DEBUG] Speed restored by: " + speedReduction);
        System.out.println("Snowfall effect wears off from " + target.getName());
    }
    
    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) {
            return;
        }
        
        System.out.println("[DEBUG] Snowfall Aura tick - Duration before: " + getDuration());
        
        if (isFirstTick) {
            isFirstTick = false;
            return;
        }
        
        decrementDuration();
        
        System.out.println("[DEBUG] Snowfall Aura tick - Duration after: " + getDuration());
        
        if (getDuration() > 0) {
            System.out.println(target.getName() + " is still affected by the snowfall.");
        }
    }
}