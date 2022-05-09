package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MainDataController;

public class EliteEnemyFactory implements EnemyFactory {
    private int eliteHp = MainDataController.getEliteHp();
    /**
     * 产生精英敌机
     * @return
     */
    @Override
    public AbstractEnemy createEnemy() {
        return new EliteEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                3,
                7,
                (int)(eliteHp*MainDataController.getEnemyStrengthenRate()));
    }
}

