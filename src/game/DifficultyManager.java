package game;

public class DifficultyManager {
    private Difficulty difficulty;
    
    public DifficultyManager(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public double getExpMultiplier() {
        switch (difficulty) {
            case EASY: return 1.5;     // เพิ่มประสบการณ์ในโหมด EASY
            case MEDIUM: return 1.0;   // ค่ามาตรฐาน
            case HARD: return 0.8;     
            case NIGHTMARE: return 0.7; 
            default: return 1.0;
        }
    }

    public float getStatMultiplier() {
        switch (difficulty) {
            case EASY: return 0.5f;     // ลดเหลือ 50% ของค่าปกติ
            case MEDIUM: return 0.7f;   // ลดเหลือ 70% ของค่าปกติ
            case HARD: return 0.9f;     // ลดเหลือ 90% ของค่าปกติ
            case NIGHTMARE: return 1.1f; // เพิ่มเป็น 110% ของค่าปกติ
            default: return 0.7f;
        }
    }
    
    public float getDamageMultiplier() {
        switch (difficulty) {
            case EASY: return 0.6f;     // ลดเหลือ 60% ของค่าปกติ
            case MEDIUM: return 0.8f;   // ลดเหลือ 80% ของค่าปกติ
            case HARD: return 0.9f;     // ลดเหลือ 90% ของค่าปกติ
            case NIGHTMARE: return 1.0f; // คงเดิม 100%
            default: return 0.8f;
        }
    }
    
    public float getPlayerHpMultiplier() {
        switch (difficulty) {
            case EASY: return 1.5f;     // เพิ่มเป็น 150% ของค่าปกติ
            case MEDIUM: return 1.2f;   // เพิ่มเป็น 120% ของค่าปกติ
            case HARD: return 1.0f;     // คงเดิม 100%
            case NIGHTMARE: return 0.9f; // ลดเหลือ 90%
            default: return 1.2f;
        }
    }
    
    public float getPlayerMpMultiplier() {
        switch (difficulty) {
            case EASY: return 1.5f;     // เพิ่มเป็น 150% ของค่าปกติ
            case MEDIUM: return 1.3f;   // เพิ่มเป็น 130% ของค่าปกติ
            case HARD: return 1.1f;     // เพิ่มเป็น 110% ของค่าปกติ
            case NIGHTMARE: return 1.0f; // คงเดิม 100%
            default: return 1.3f;
        }
    }
    
    public float getEnemyAccuracyMultiplier() {
        switch (difficulty) {
            case EASY: return 0.6f;     // ลดเหลือ 60% ของค่าปกติ
            case MEDIUM: return 0.8f;   // ลดเหลือ 80% ของค่าปกติ
            case HARD: return 0.9f;     // ลดเหลือ 90% ของค่าปกติ
            case NIGHTMARE: return 1.0f; // คงเดิม 100%
            default: return 0.8f;
        }
    }
}