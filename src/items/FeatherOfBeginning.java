package items;

import characters.Character;
import player.Player;

public class FeatherOfBeginning extends Feather {
    
    public FeatherOfBeginning() {
        super("Feather of Beginning", "A basic feather that increases MP by 20");
    }
    
    @Override
    public void use(Character target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.restoreMp(50);
            System.out.println(target.getName() + " used Feather of Beginning and restored 20 MP!");
        } else {
            System.out.println("This feather can only be used by players!");
        }
    }
}