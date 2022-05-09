package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

public interface ShootStrategy {
    /**
     * 返回飞机射出子弹
     * @param aircraft
     * @return
     */
    List<AbstractBullet> doShoot(AbstractAircraft aircraft);
}
