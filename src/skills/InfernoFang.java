package skills;

import characters.Character;
import effects.FireBurn;
import java.util.Random;

public class InfernoFang extends Skill {
    private double multiplier;
    private int baseDamage;
    
    public InfernoFang() {
        super("Inferno Fang", 20, 5, "Leaps to bite the target with intense flame power, dealing heavy damage and possibly inflicting Burn.");
        this.baseDamage = 20;  // ลดจาก 25
        this.multiplier = 1.5;  // ลดจาก 1.8
    }
    
    @Override
    public void use(Character user, Character target) {
        // ใช้วิธีคำนวณความเสียหายแบบทั่วไป
        int damage = Math.max((int)(user.getAtk() * multiplier) - target.getDef(), 1);
        
        // ลดโอกาสติดเอฟเฟค
        Random random = new Random();
        if (random.nextInt(100) < 25) { // ลดจาก 30%
            // สร้าง FireBurn ที่ถูกต้องตาม constructor
            target.applyEffect(new FireBurn(0.05f, 3)); // burnRate, duration
        }
        
        target.takeDamage(damage);
        System.out.println(user.getName() + " uses Inferno Fang, dealing " + damage + " damage!");
    }
}