package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.StraightShoot;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    private static HeroAircraft heroAircraft;
    //bulletProp获得的数量
    private int bulletGetNum = 0;
    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 1;
        this.power = 30;
        this.direction = -1;
    }

    /**
     * 实现单例模式的双重检查锁定，保证只创建一架英雄机
     * @return 返回唯一的英雄机
     */
    public static HeroAircraft getHeroAircraft() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0,
                            0,
                            2000);
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<AbstractBullet> shoot() {
        MusicThread shootBGM = new MusicThread("src/videos/bullet.wav");
        shootBGM.start();
        return super.shoot();
    }

    public void increaseBulletGetNum() {
        bulletGetNum++;
    }

    public void decreaseBulletGetNum() {
        bulletGetNum--;
    }

    public int getBulletGetNum() {
        return bulletGetNum;
    }
}
