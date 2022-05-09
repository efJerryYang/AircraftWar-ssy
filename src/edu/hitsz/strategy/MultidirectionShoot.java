package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class MultidirectionShoot implements ShootStrategy{
    /**
     * 散射射出子弹
     * @param aircraft
     * @return
     */
    @Override
    public List<AbstractBullet> doShoot(AbstractAircraft aircraft) {
        List<AbstractBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection()*2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection()*5;
        AbstractBullet abstractBullet;
        if (aircraft instanceof HeroAircraft) {
            for(int i=0; i<aircraft.getShootNum(); i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                abstractBullet = new HeroBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, speedX + i*2 - aircraft.getShootNum() + 1, speedY, aircraft.getPower());
                res.add(abstractBullet);
            }
        } else {
            for(int i=0; i<aircraft.getShootNum(); i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                abstractBullet = new EnemyBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, speedX + i*2 - aircraft.getShootNum() + 1, speedY, aircraft.getPower());
                res.add(abstractBullet);
            }
        }
        return res;
    }
}
