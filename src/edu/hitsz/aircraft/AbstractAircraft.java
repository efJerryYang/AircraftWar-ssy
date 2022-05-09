package edu.hitsz.aircraft;

import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShoot;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    /** 攻击方式 */
    protected int shootNum = 0;     //子弹一次发射数量
    protected int power = 0;       //子弹伤害
    protected int direction = 1;  //子弹射击方向 (向上发射：-1，向下发射：1)

    protected ShootStrategy shootStrategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootStrategy = new StraightShoot();
    }

    /**
     * 扣除或增加飞机的hp
     * @param decrease
     */
    public void decreaseHp(int decrease){
        if (decrease >= 0) {
            hp -= decrease;
        } else {
            hp = Math.min(hp-decrease,maxHp);
        }
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<AbstractBullet> shoot() {

        return this.shootStrategy.doShoot(this);
    }

    public int getDirection() {
        return direction;
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public synchronized void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public synchronized void increaseShootNum() {
        shootNum = 3;
    }

    public synchronized void decreaseShootNum() {
        shootNum = 1;
    }
}


