package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;
import edu.hitsz.strategy.MultidirectionShoot;
import edu.hitsz.strategy.StraightShoot;

public class BulletProp extends AbstractProp{
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        bgmPath = "src/videos/get_supply.wav";
    }

    /**
     * 火力作用函数，打印"FireSupply active!"，并且定时10秒将英雄机变为散射
     * @param heroAircraft 英雄机
     */
    @Override
    public void work(HeroAircraft heroAircraft) {
        heroAircraft.increaseShootNum();
        heroAircraft.setShootStrategy(new MultidirectionShoot());
        //通过bulletGetNum参数来判断当前运行的shootThread数量，直到最后一个退出才将散射变回直射
        Thread shootThread = new Thread(()->{
            try {
                heroAircraft.increaseBulletGetNum();
                Thread.sleep(10000);
                if (heroAircraft.getBulletGetNum() <= 1) {
                    heroAircraft.decreaseShootNum();
                    heroAircraft.setShootStrategy(new StraightShoot());
                }
                heroAircraft.decreaseBulletGetNum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        shootThread.start();
        System.out.println("FireSupply active");
        //产生对应音效
        MusicThread bloodBGM = new MusicThread(bgmPath);
        bloodBGM.start();
    }
}
