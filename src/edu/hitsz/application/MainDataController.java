package edu.hitsz.application;

public class MainDataController {
    private static int difficulty = 0;
    private static int score = 0;
    private static String backGroundPath = "src/images/bg3.jpg";
    private static String difficultyStr = "简单";
    private static boolean musicSwitch = true;
    private static String dataPath = "src/data/easy.dat";
    private static int enemyMaxNumber = 4;
    private static int bossThreshold = 2000;
    private static int bossHp = 500;
    private static int eliteHp = 30;
    private static int mobHp = 30;
    private static int enemyCycleDuration = 600;
    private static int enemyShootDuration = 600;
    private static int heroAircraftShootDuration = 600;
    private static double eliteChance = 0.2;
    private static double bossStrengthenRate = 1;
    private static double enemyStrengthenRate = 1;
    private static int enemyCycleMin = 600;
    private static int enemyShootMin = 600;
    private static double eliteChanceMax = 0.4;

    public static void setDifficulty(int i) {
        if (i <= 0) {
            difficulty = 0;
            easySet();
        } else if (i == 1) {
            difficulty = 1;
            normalSet();
        } else {
            difficulty = 2;
            hardSet();
        }
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static void setScore(int score) {
        MainDataController.score = score;
    }

    public static int getScore() {
        return score;
    }

    /**
     * 简单难度下设置相关数据
     */
    public static void easySet() {
        backGroundPath = "src/images/bg3.jpg";
        difficultyStr = "简单";
        dataPath = "src/data/easy.dat";
        enemyMaxNumber = 4;
        eliteHp = 30;
        mobHp = 30;
        enemyCycleDuration = 800;
        enemyShootDuration = 800;
        heroAircraftShootDuration = 400;
        eliteChance = 0.2;
    }

    /**
     * 普通难度下设置相关数据
     */
    public static void normalSet() {
        backGroundPath = "src/images/bg4.jpg";
        difficultyStr = "普通";
        dataPath = "src/data/normal.dat";
        enemyMaxNumber = 6;
        bossThreshold = 1000;
        bossHp = 300;
        eliteHp = 40;
        mobHp = 30;
        enemyCycleDuration = 600;
        enemyShootDuration = 600;
        heroAircraftShootDuration = 600;
        eliteChance = 0.25;
        enemyCycleMin = 480;
        enemyShootMin = 400;
        eliteChanceMax = 0.4;
    }

    /**
     * 困难难度下设置相关设置
     */
    public static void hardSet() {
        backGroundPath = "src/images/bg5.jpg";
        difficultyStr = "困难";
        dataPath = "src/data/hard.dat";
        enemyMaxNumber = 8;
        bossThreshold = 800;
        bossHp = 400;
        eliteHp = 50;
        mobHp = 30;
        enemyCycleDuration = 480;
        enemyShootDuration = 600;
        heroAircraftShootDuration = 800;
        eliteChance = 0.3;
        enemyCycleMin = 400;
        enemyShootMin = 480;
        eliteChanceMax = 0.45;
    }

    public static String getBackGroundPath() {
        return backGroundPath;
    }

    public static String getDataPath() {
        return dataPath;
    }

    public static String getDifficultyStr() {
        return difficultyStr;
    }

    public static void setMusicSwitch(boolean musicSwitch) {
        MainDataController.musicSwitch = musicSwitch;
    }

    public static boolean getMusicSwitch() {
        return musicSwitch;
    }

    public static int getEnemyMaxNumber() {
        return enemyMaxNumber;
    }

    public static int getBossThreshold() {
        return bossThreshold;
    }

    public static int getBossHp() {
        return bossHp;
    }

    public static int getEliteHp() {
        return eliteHp;
    }

    public static int getMobHp() {
        return mobHp;
    }
    public static int getEnemyCycleDuration() {
        return enemyCycleDuration;
    }

    public static int getEnemyShootDuration() {
        return enemyShootDuration;
    }

    public static int getHeroAircraftShootDuration() {
        return heroAircraftShootDuration;
    }

    public static double getEliteChance() {
        return eliteChance;
    }

    public static void strengthenBoss() {
        if (bossStrengthenRate < 1.5) {
            bossStrengthenRate += 0.1;
        }
    }

    public static double getBossStrengthenRate() {
        return bossStrengthenRate;
    }

    public static void strengthenEnemy() {
        if (enemyStrengthenRate < 1.5) {
            enemyStrengthenRate += 0.02;
        }
    }

    public static double getEnemyStrengthenRate() {
        return enemyStrengthenRate;
    }

    public static int getEnemyCycleMin() {
        return enemyCycleMin;
    }

    public static int getEnemyShootMin() {
        return enemyShootMin;
    }

    public static double getEliteChanceMax() {
        return eliteChanceMax;
    }
}
