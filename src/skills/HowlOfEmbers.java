package skills;

import characters.Character;
import effects.AttackUpEffect;
import effects.DefenseDownEffect;

public class HowlOfEmbers extends Skill {
    public HowlOfEmbers() {
        super("Howl of Embers", 30, 4, "Increases user's attack and reduces target's defense");
    }
    
    @Override
    public void use(Character user, Character target) {
        AttackUpEffect atkUp = new AttackUpEffect(2, 10);
        user.applyEffect(atkUp);
        
        DefenseDownEffect defDown = new DefenseDownEffect(2, 10);
        target.applyEffect(defDown);
        
        System.out.println(user.getName() + " howls with burning fury!");
    }
}
