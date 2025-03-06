package effects;

import characters.Character;

public abstract class BaseDotEffect {
    protected String name;
    protected int duration;
    protected float rate;
    protected boolean isFirstTick = true; 

    public BaseDotEffect(float rate, int duration, String name) {
        this.rate = rate;
        this.duration = duration;
        this.name = name;
        System.out.println("[DEBUG] Created " + name + " effect with duration: " + duration);
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void decrementDuration() {
        if (!isFirstTick && duration > 0) {
            System.out.println("[DEBUG] " + name + " duration before: " + duration);
            duration--;
            System.out.println("[DEBUG] " + name + " duration after: " + duration);
        } else {
            isFirstTick = false;
            System.out.println("[DEBUG] First tick of " + name + " - not decrementing");
        }
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public abstract void applyEffect(Character target);
    public abstract void removeEffect(Character target);
    public abstract void tickEffect(Character target);
}