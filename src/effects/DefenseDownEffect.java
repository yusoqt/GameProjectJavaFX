package effects;

import characters.Character;

public class DefenseDownEffect extends BaseDotEffect {
    private int defenseReduction;
    private boolean isFirstTick = true;

    public DefenseDownEffect(int duration, int defenseReduction) {
        super(0, duration, "Defense Down");
        this.defenseReduction = defenseReduction;
    }

    @Override
    public void applyEffect(Character target) {
        target.setDef(target.getDef() - defenseReduction);
        System.out.println("[DEBUG] " + target.getName() + "'s defense reduced by " + defenseReduction);
    }

    @Override
    public void removeEffect(Character target) {
        target.setDef(target.getDef() + defenseReduction);
        System.out.println("[DEBUG] " + target.getName() + "'s defense restored");
    }

    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) return;

        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] Defense Down duration: " + getDuration());
        } else {
            isFirstTick = false;
        }

        if (getDuration() > 0) {
            System.out.println(target.getName() + "'s defense remains lowered.");
        }
    }
}
