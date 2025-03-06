package items;

import characters.Character;

public abstract class Feather extends Item {
    
    public Feather(String name, String description) {
        super(name, description);
    }
    
    @Override
    public void use(Character target) {
        // การใช้งาน feather พื้นฐาน
        System.out.println("Using feather on " + target.getName());
    }
}