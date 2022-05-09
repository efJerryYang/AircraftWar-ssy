package edu.hitsz.application;

import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.EnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;

public class EasyGame extends Game{
    /**
     * 简单难度空实现
     */
    @Override
    protected void raiseDifficulty() {
        return;
    }

    /**
     * 简单难度生成敌机不生成boss机
     */
    @Override
    protected void createEnemy() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            double chance = Math.random();
            EnemyFactory enemyFactory;
            if (chance >= eliteChance) {
                enemyFactory = new MobEnemyFactory();
            }//一定概率产生普通敌机
            else {
                enemyFactory = new EliteEnemyFactory();
            }//一定概率产生精英敌机
            enemyAircrafts.add(enemyFactory.createEnemy());
        }
    }
}
