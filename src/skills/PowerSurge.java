package skills;

import characters.Character;
import effects.AttackUpEffect; 

public class PowerSurge extends Skill {
    private int duration;
    private int boostAmount;

    public PowerSurge(String name, int cooldown, int duration, int boostAmount) {
        super(name, 20, cooldown, "Surges with power, dealing damage and boosting ATK.");
        this.duration = duration;
        this.boostAmount = boostAmount;
    }

    @Override
    public void use(Character user, Character target) {
        // ลดความรุนแรงของสกิล
        int damage = Math.max((int)(user.getAtk() * 0.8) - target.getDef(), 1);
        target.takeDamage(damage);
        System.out.println(user.getName() + " uses Power Surge, dealing " + damage + " damage!");
        
        // ให้เอฟเฟคกับผู้ใช้เอง
        user.applyEffect(new AttackUpEffect(duration, boostAmount));
        System.out.println(user.getName() + "'s attack is boosted by " + boostAmount + "% for " + duration + " turns!");
    }
}