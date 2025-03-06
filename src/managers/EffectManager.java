package managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import buffs.BaseBuff;
import debuffs.BaseDebuff;
import characters.Character;
import effects.BaseDotEffect;

public class EffectManager {
    private List<BaseBuff> buffs;
    private List<BaseDebuff> debuffs;
    private List<BaseDotEffect> dotEffects;
    private Character target; 

    public EffectManager() {
        buffs = new ArrayList<>();
        debuffs = new ArrayList<>();
        dotEffects = new ArrayList<>();
    }
    public void setTarget(Character target) {
        this.target = target;
    }

    public void addDotEffect(BaseDotEffect effect, Character target) {
        effect.applyEffect(target);
        dotEffects.add(effect);
        System.out.println("[DEBUG] Added DoT effect: " + effect.getName());
    }

    public void removeDotEffect(BaseDotEffect effect) {
        dotEffects.remove(effect);
    }

    public void addBuff(BaseBuff buff, Character target) {
        buff.apply(target);
        buffs.add(buff);
        System.out.println("[DEBUG] Added buff: " + buff.getName());
    }

    public void removeBuff(BaseBuff buff) {
        buffs.remove(buff);
    }

    public void addDebuff(BaseDebuff debuff, Character target) {
        debuff.apply(target);
        debuffs.add(debuff);
        System.out.println("[DEBUG] Added debuff: " + debuff.getName());
    }

    public void removeDebuff(BaseDebuff debuff) {
        debuffs.remove(debuff);
    }

    public void processTurn(Character target) {
        System.out.println("\n[DEBUG] === Processing Turn Start ===");
        System.out.println("[DEBUG] Current active effects:");
        System.out.println("DoT Effects: " + dotEffects.size());
        System.out.println("Debuffs: " + debuffs.size());
        System.out.println("Buffs: " + buffs.size());
        
        processEffects(target);
        
        System.out.println("[DEBUG] === Processing Turn End ===\n");
    }

    private void processEffects(Character target) {
        System.out.println("[DEBUG] Starting effect processing...");
        
        processDotEffects(target);
        processDebuffs(target);
        processBuffs(target);
        printEffectsSummary();
    }

    private void processDotEffects(Character target) {
        if (dotEffects.isEmpty()) return;

        Iterator<BaseDotEffect> iterator = dotEffects.iterator();
        while (iterator.hasNext()) {
            BaseDotEffect effect = iterator.next();
            System.out.println("[DEBUG] Processing " + effect.getName() + " (Duration: " + effect.getDuration() + ")");
            
            effect.tickEffect(target);
            
            if (effect.isExpired()) {
                System.out.println("[DEBUG] " + effect.getName() + " has expired");
                effect.removeEffect(target);
                iterator.remove();
            }
        }
    }

    private void processDebuffs(Character target) {
        Iterator<BaseDebuff> debuffIterator = debuffs.iterator();
        while (debuffIterator.hasNext()) {
            BaseDebuff debuff = debuffIterator.next();
            System.out.println("\n[DEBUG] Processing debuff: " + debuff.getName());
            System.out.println("[DEBUG] Duration before: " + debuff.getDuration());
            
            debuff.onTurnEnd(target);
            debuff.decrementDuration();
            
            System.out.println("[DEBUG] Duration after: " + debuff.getDuration());
            
            if (debuff.getDuration() <= 0) {
                System.out.println("[DEBUG] Removing expired debuff: " + debuff.getName());
                debuff.remove(target);
                debuffIterator.remove();
            }
        }
    }

    private void processBuffs(Character target) {
        Iterator<BaseBuff> buffIterator = buffs.iterator();
        while (buffIterator.hasNext()) {
            BaseBuff buff = buffIterator.next();
            System.out.println("[DEBUG] Processing buff: " + buff.getName());
            System.out.println("[DEBUG] Duration before: " + buff.getDuration());
            
            buff.onTurnEnd(target);
            buff.decrementDuration();
            
            System.out.println("[DEBUG] Duration after: " + buff.getDuration());
            
            if (buff.getDuration() <= 0) {
                System.out.println("[DEBUG] Removing expired buff: " + buff.getName());
                buff.remove(target);
                buffIterator.remove();
            }
        }
    }

    private void printEffectsSummary() {
        System.out.println("\n[DEBUG] === Effects Summary ===");
        if (!dotEffects.isEmpty()) {
            System.out.println("DoT Effects:");
            for (BaseDotEffect effect : dotEffects) {
                System.out.println("- " + effect.getName() + " (Duration: " + effect.getDuration() + ")");
            }
        }
        if (!debuffs.isEmpty()) {
            System.out.println("Debuffs:");
            for (BaseDebuff debuff : debuffs) {
                System.out.println("- " + debuff.getName() + " (Duration: " + debuff.getDuration() + ")");
            }
        }
        if (!buffs.isEmpty()) {
            System.out.println("Buffs:");
            for (BaseBuff buff : buffs) {
                System.out.println("- " + buff.getName() + " (Duration: " + buff.getDuration() + ")");
            }
        }
    }

    public List<BaseDotEffect> getActiveDotEffects() {
        return new ArrayList<>(dotEffects);
    }

    public List<BaseBuff> getActiveBuffs() {
        return new ArrayList<>(buffs);
    }

    public List<BaseDebuff> getActiveDebuffs() {
        return new ArrayList<>(debuffs);
    }
}