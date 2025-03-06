package debuffs;

import characters.Character;

public abstract class BaseDebuff {
    protected String name;
    protected int duration;

    public BaseDebuff(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void decrementDuration() {
        System.out.println("[DEBUG] " + name + " duration before: " + duration);
        duration--;
        System.out.println("[DEBUG] " + name + " duration after: " + duration);
    }

    // Add getName() method
    public String getName() {
        return name;
    }

    public abstract void apply(Character target);
    public abstract void remove(Character target);
    public abstract void onTurnStart(Character target);
    public abstract void onTurnEnd(Character target);
}