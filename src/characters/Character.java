package characters;

import java.util.ArrayList;
import java.util.List;
import skills.Skill;
import effects.BaseDotEffect;
import buffs.BaseBuff;
import debuffs.BaseDebuff;

/*
 * Character: คลาสพื้นฐานสำหรับตัวละครทั้งผู้เล่นและศัตรู
 * เก็บค่าสถานะ (HP, ATK, DEF, SPD) และเมธอดสำหรับรับ/สร้างความเสียหาย
 */

public abstract class Character {
    protected String name;
    protected int maxHp;  
    protected int hp;
    protected int atk;
    protected int def;
    protected int spd;
    protected int accuracy = 100; 
    protected List<Skill> skills;
    protected List<BaseDotEffect> dotEffects;
    protected List<BaseBuff> buffs;
    protected List<BaseDebuff> debuffs;
    private int attack;
    private int speed;
    private List<BaseDotEffect> activeEffects = new ArrayList<>();
    private boolean isStunned;
    private boolean isFrozen;

    public Character(String name, int hp, int atk, int def, int spd, int accuracy) {
        this.name = name;
        this.maxHp = hp;  
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.accuracy = accuracy;
        this.skills = new ArrayList<>();
        this.dotEffects = new ArrayList<>();
        this.buffs = new ArrayList<>();
        this.debuffs = new ArrayList<>();
    }
    
    public Character(String name, int hp, int atk, int def, int spd) {
        this(name, hp, atk, def, spd, 100);
    }
    
    public Character(String name, int hp, int atk, int def, int spd, List<Skill> skills) {
        this(name, hp, atk, def, spd, 100);
        this.skills = skills;
    }
    
    public int getHp() { return hp; }
    public void takeDamage(int damage) { 
        this.hp = Math.max(0, this.hp - damage);  // ป้องกัน HP ติดลบ
    }
    public String getName() { return name; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public void setDef(int def) {
        this.def = def;
    }
    public int getSpd() { return spd; }
    public int getAccuracy() { return accuracy; }
    public void setAccuracy(int accuracy) { this.accuracy = Math.max(0, Math.min(accuracy, 100)); }
    public List<Skill> getSkills() { return skills; }
    
    public void setSpd(int spd) {
        this.spd = spd;
    }
    
    public void applyEffect(BaseDotEffect effect) {
        activeEffects.add(effect);
        effect.applyEffect(this);
    }
    
    public void addBuff(BaseBuff buff) {
        buff.apply(this);  
    }
    
    public void addDebuff(BaseDebuff debuff) {
        debuffs.add(debuff);
        debuff.apply(this);
    }
    
    public void heal(int amount) {
        this.hp = Math.min(hp + amount, maxHp);
        System.out.println(name + " heals for " + amount + " HP.");
    }
    
    public void increaseDef(int amount) {
        this.def += amount;
        System.out.println(name + "'s DEF increases by " + amount + ".");
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));  
    }
    
    protected void recalcStats() {
        this.hp = this.maxHp;
    }

    public List<BaseDotEffect> getActiveEffects() {
        return activeEffects;
    }
    
    public void removeEffect(BaseDotEffect effect) {
        activeEffects.remove(effect);
        effect.removeEffect(this);
    }

    public void setStunned(boolean stunned) {
        this.isStunned = stunned;
    }
    
    public boolean isStunned() {
        return isStunned;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }

    public boolean isFrozen() {
        return isFrozen;
    }
    
    public boolean hasEffect(String effectName) {
        for (BaseDotEffect effect : dotEffects) {
            if (effect.getName().equals(effectName)) {
                return true;
            }
        }
        for (BaseBuff buff : buffs) {
            if (buff.getName().equals(effectName)) {
                return true;
            }
        }
        for (BaseDebuff debuff : debuffs) {
            if (debuff.getName().equals(effectName)) {
                return true;
            }
        }
        return false;
    }
}