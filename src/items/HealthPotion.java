package items;

import characters.Character;

public class HealthPotion extends Item {
    private int healAmount;
    
    public HealthPotion() {
        super("Health Potion", "Restores 50 HP");
        this.healAmount = 50;
    }
    
    public HealthPotion(String name, String description, int healAmount) {
        super(name, description);
        this.healAmount = healAmount;
    }
    
    @Override
    public void use(Character target) {
        target.heal(healAmount);
        System.out.println(target.getName() + " used Health Potion and restored " + healAmount + " HP!");
    }
}
