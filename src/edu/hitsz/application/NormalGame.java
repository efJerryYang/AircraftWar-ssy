package edu.hitsz.application;

import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.EnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;

public class NormalGame extends Game{
    /**
     * 普通难度随时间提高敌机生成周期，射击周期，敌机属性，精英机概率
     */
    @Override
    protected void raiseDifficulty() {
        if (enemyCycle[1] > enemyCycleMin) {
            enemyCycle[1] -= timeInterval;
        }
        if (enemyShootCycle[1] > enemyShootMin) {
            enemyShootCycle[1] -= timeInterval;
        }
        if (eliteChance < eliteChanceMax) {
            eliteChance += 0.02;
        }
        MainDataController.strengthenEnemy();
        System.out.printf("提高难度：敌机生成周期：%d，敌机射击周期：%d,精英机概率：%.2f,敌机属性提升倍率：%.2f\n",enemyCycle[1],enemyShootCycle[1],eliteChance,MainDataController.getEnemyStrengthenRate());
    }

    /**
     * 普通难度生成敌机生成boss机不提升其属性
     */
    @Override
    protected void createEnemy() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            double chance = Math.random();
            EnemyFactory enemyFactory;

            //boss阈值满足且不存在boss
            if (bossFlag && !bossExist) {
                System.out.printf("产生boss敌机\n");
                enemyFactory = new BossEnemyFactory();
                //产生对应音效
                bossBGM = new CycleMusicThread("src/videos/bgm_boss.wav");
                bossBGM.start();
                bossNum++;
            } else if (chance >= eliteChance) {
                enemyFactory = new MobEnemyFactory();
            }//一定概率产生普通敌机
            else {
                enemyFactory = new EliteEnemyFactory();
            }//一定概率产生精英敌机
            bossFlag = false;
            enemyAircrafts.add(enemyFactory.createEnemy());
        }
    }
}
