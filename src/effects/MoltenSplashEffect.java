package effects;

import characters.Character;

public class MoltenSplashEffect extends BaseDotEffect {
    private float defenseReduction;
    private boolean isFirstTick = true;

    public MoltenSplashEffect(int duration) {
        super(0.0f, duration, "Molten Splash");
        this.defenseReduction = 5.0f;
        System.out.println("[DEBUG] Created Molten Splash effect with duration: " + duration);
    }

    @Override
    public void applyEffect(Character target) {
        int currentDef = target.getDef();
        target.setDef(currentDef - (int)defenseReduction);
        System.out.println("[DEBUG] " + target.getName() + "'s defense reduced by " + defenseReduction);
    }

    @Override
    public void tickEffect(Character target) {
        if (getDuration() <= 0) return;

        if (!isFirstTick) {
            System.out.println("[DEBUG] Molten Splash duration before: " + getDuration());
            decrementDuration();
            System.out.println("[DEBUG] Molten Splash duration after: " + getDuration());
        } else {
            isFirstTick = false;
        }

        if (getDuration() > 0) {
            System.out.println(target.getName() + "'s defense remains lowered from the molten splash.");
        }
    }

    @Override
    public void removeEffect(Character target) {
        int currentDef = target.getDef();
        target.setDef(currentDef + (int)defenseReduction);
        System.out.println("[DEBUG] " + target.getName() + "'s defense restored");
        System.out.println("Molten splash effect wears off from " + target.getName());
    }
}