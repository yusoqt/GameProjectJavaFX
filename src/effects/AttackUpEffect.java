package effects;

import characters.Character;

public class AttackUpEffect extends BaseDotEffect {
    private int attackIncrease;
    private boolean isFirstTick = true;

    public AttackUpEffect(int duration, int attackIncrease) {
        super(0, duration, "Attack Up");
        this.attackIncrease = attackIncrease;
    }

    @Override
    public void applyEffect(Character target) {
        target.setAtk(target.getAtk() + attackIncrease);
        System.out.println("[DEBUG] " + target.getName() + "'s attack increased by " + attackIncrease);
    }

    @Override
    public void removeEffect(Character target) {
        target.setAtk(target.getAtk() - attackIncrease);
        System.out.println("[DEBUG] " + target.getName() + "'s attack returned to normal");
    }

    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) return;

        if (!isFirstTick) {
            decrementDuration();
            System.out.println("[DEBUG] Attack Up duration: " + getDuration());
        } else {
            isFirstTick = false;
        }

        if (getDuration() > 0) {
            System.out.println(target.getName() + "'s attack remains increased.");
        }
    }
}