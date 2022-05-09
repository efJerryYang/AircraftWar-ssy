package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MainDataController;

public class BossEnemyFactory implements EnemyFactory {
    private int bossHp = MainDataController.getBossHp();
    /**
     * 产生boss敌机
     * @return
     */
    @Override
    public AbstractEnemy createEnemy() {
        return new BossEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                3,
                0,
                (int)(bossHp*MainDataController.getBossStrengthenRate()));
    }
}
