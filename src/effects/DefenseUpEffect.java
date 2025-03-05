package effects;

import characters.Character;

public class DefenseUpEffect extends BaseDotEffect {
    private int defenseIncrease;
    private boolean isFirstTick = true;

    public DefenseUpEffect(int duration, int defenseIncrease) {
        super(0, duration, "Defense Up");
        this.defenseIncrease = defenseIncrease;
        System.out.println("[DEBUG] Created Defense Up effect with duration: " + duration);
    }

    @Override
    public void applyEffect(Character target) {
        target.setDef(target.getDef() + defenseIncrease);
        System.out.println("[DEBUG] " + target.getName() + "'s defense increased by " + defenseIncrease);
    }

    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) {
            return;
        }

        System.out.println("[DEBUG] Defense Up processing for " + target.getName());
        System.out.println("[DEBUG] Current duration: " + getDuration());

        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] Duration decreased to: " + getDuration());
            
            if (getDuration() > 0) {
                System.out.println(target.getName() + "'s defense boost continues. (" + getDuration() + " turns remaining)");
            } else {
                System.out.println("[DEBUG] Defense Up effect expired");
            }
        } else {
            isFirstTick = false;
            System.out.println("[DEBUG] First tick - not decrementing duration");
        }
    }

    @Override
    public void removeEffect(Character target) {
        System.out.println("[DEBUG] Removing Defense Up effect from " + target.getName());
        target.setDef(target.getDef() - defenseIncrease);
        System.out.println(target.getName() + "'s defense returns to normal.");
        System.out.println("[DEBUG] Defense decreased by: " + defenseIncrease);
    }
}