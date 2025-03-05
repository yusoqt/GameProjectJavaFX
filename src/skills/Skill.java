package skills;

import characters.Character;
import game.DifficultyManager;
import player.Player;
import java.util.Random;

/*
 * Skill: คลาสพื้นฐานสำหรับสกิล
 * มีชื่อ ดาเมจ ค่าคอสต์ คำอธิบาย และเมธอด use(...) สำหรับใช้งานสกิล
 */

public abstract class Skill {
    private String name;
    private int manaCost;
    private int cooldown;
    private String description;

    public Skill(String name, int manaCost, int cooldown, String description) {
        this.name = name;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getDescription() {
        return description;
    }

    public abstract void use(Character user, Character target);

    // คำนวณความเสียหายตามความยาก - แก้ไขโดยไม่ต้องใช้ DifficultyManager เป็น parameter
    protected int calculateDamage(int baseDamage, Character user, Character target) {
        float multiplier = 1.0f;
        
        // ตรวจสอบเพื่อปรับความเสียหาย
        if (!(user instanceof Player)) {
            // ลดความเสียหายของศัตรูทุกตัวลง 10%
            multiplier = 0.9f;
        }
        
        int damage = Math.max((int)(baseDamage * multiplier) - target.getDef(), 1);
        return damage;
    }
    
    // Overload method สำหรับกรณีมี DifficultyManager
    protected int calculateDamage(int baseDamage, Character user, Character target, DifficultyManager diffMgr) {
        float multiplier = 1.0f;
        
        // ถ้าผู้ใช้เป็นผู้เล่น ใช้ตัวคูณปกติ
        // ถ้าผู้ใช้เป็นมอนสเตอร์หรือบอส ใช้ตัวคูณความเสียหาย
        if (!(user instanceof Player) && diffMgr != null) {
            multiplier = diffMgr.getDamageMultiplier();
        }
        
        int damage = Math.max((int)(baseDamage * multiplier) - target.getDef(), 1);
        return damage;
    }
}