package effects;

import characters.Character;

public class HighPitchWhirrEffect extends BaseDotEffect {
    private int accuracyReduction;
    private boolean isFirstTick = true;

    public HighPitchWhirrEffect(int duration, int accuracyReduction) {
        super(0, duration, "High Pitch Whirr");
        this.accuracyReduction = accuracyReduction;
        System.out.println("[DEBUG] Created High Pitch Whirr effect with duration: " + duration);
    }

    @Override
    public void applyEffect(Character target) {
        // Check if target already has effect
        if (target.hasEffect("High Pitch Whirr")) {
            System.out.println("[DEBUG] Target already affected by High Pitch Whirr");
            return;
        }

        System.out.println("[DEBUG] Applying High Pitch Whirr effect");
        System.out.println("[DEBUG] Current accuracy: " + target.getAccuracy());
        
        int newAccuracy = Math.max(0, target.getAccuracy() - accuracyReduction);
        target.setAccuracy(newAccuracy);
        
        System.out.println(target.getName() + " is disoriented by the high-pitched sound!");
        System.out.println("[DEBUG] New accuracy: " + newAccuracy);
    }

    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) return;

        System.out.println("[DEBUG] High Pitch Whirr duration before: " + getDuration());
        
        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] High Pitch Whirr duration after: " + getDuration());
        } else {
            isFirstTick = false;
            System.out.println("[DEBUG] First tick of High Pitch Whirr - not decrementing");
        }

        if (getDuration() > 0) {
            System.out.println(target.getName() + " continues to be disturbed by the high-frequency noise.");
        }
    }

    @Override
    public void removeEffect(Character target) {
        System.out.println("[DEBUG] Removing High Pitch Whirr effect");
        System.out.println("[DEBUG] Current accuracy: " + target.getAccuracy());
        
        target.setAccuracy(target.getAccuracy() + accuracyReduction);
        System.out.println("High-Pitch Whirr effect wears off from " + target.getName());
        System.out.println("[DEBUG] Restored accuracy: " + target.getAccuracy());
    }
}