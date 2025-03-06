package items;

import characters.Character;

public abstract class Item {
    private String name;
    private String description;
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public abstract void use(Character target);
}