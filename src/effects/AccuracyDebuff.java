package effects;

import characters.Character;

public class AccuracyDebuff extends BaseDotEffect {
    private int accuracyReduction;
    private boolean isFirstTick = true;

    public AccuracyDebuff(int duration, int accuracyReduction) {
        super(0, duration, "Accuracy Down");
        this.accuracyReduction = accuracyReduction;
        System.out.println("[DEBUG] Created Accuracy Down effect with duration: " + duration);
    }

    @Override
    public void applyEffect(Character target) {
        int newAccuracy = Math.max(0, target.getAccuracy() - accuracyReduction);
        int actualReduction = target.getAccuracy() - newAccuracy;
        target.setAccuracy(newAccuracy);
        System.out.println("[DEBUG] " + target.getName() + "'s accuracy reduced by " + actualReduction);
        System.out.println("[DEBUG] Current accuracy: " + newAccuracy);
    }

    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) return;

        System.out.println("[DEBUG] Accuracy Down duration before: " + getDuration());
        
        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] Accuracy Down duration after: " + getDuration());
            if (getDuration() > 0) {
                System.out.println(target.getName() + "'s accuracy remains reduced.");
            }
        } else {
            isFirstTick = false;
            System.out.println("[DEBUG] First tick - not decrementing duration");
        }
    }

    @Override
    public void removeEffect(Character target) {
        target.setAccuracy(target.getAccuracy() + accuracyReduction);
        System.out.println("[DEBUG] Removing Accuracy Down effect");
        System.out.println("[DEBUG] Restored " + accuracyReduction + " accuracy to " + target.getName());
    }
}