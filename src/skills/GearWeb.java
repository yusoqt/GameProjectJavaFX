package skills;

import characters.Character;
import effects.SlowEffect;
import java.util.Random;

public class GearWeb extends Skill {
    private Random random;

    public GearWeb() {
        super("Gear Web", 15, 3, "Launches a web of mechanical gears that can slow and entangle the target");
        this.random = new Random();
    }

    @Override
    public void use(Character user, Character target) {
        System.out.println(user.getName() + " launches a web of gears at " + target.getName() + "!");
        
        if (!target.hasEffect("Slow")) {
            System.out.println("[DEBUG] Applying Gear Web slow effect");
            SlowEffect slow = new SlowEffect(2, 5);  
            target.applyEffect(slow);
            System.out.println(target.getName() + " is entangled in the gear web, reducing their speed!");
        } else {
            System.out.println("[DEBUG] Target already affected by slow");
        }
    }
}