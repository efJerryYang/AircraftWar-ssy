package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MainDataController;

public class MobEnemyFactory implements EnemyFactory {
    private int mobHp = MainDataController.getMobHp();
    /**
     * 产生普通敌机
     * @return
     */
    @Override
    public AbstractEnemy createEnemy() {
        return new MobEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                0,
                10,
                (int)(mobHp*MainDataController.getEnemyStrengthenRate()));
    }
}
