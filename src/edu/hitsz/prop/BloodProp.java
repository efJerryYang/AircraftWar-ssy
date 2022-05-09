package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;

public class BloodProp extends AbstractProp{
    private final int healingAmount = 100;
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        bgmPath = "src/videos/get_supply.wav";
    }

    /**
     * 血量道具作用，调用HeroAircraft.decreaseHp()函数和HeroAircraft.getHP()函数调控英雄机血量
     * @param heroAircraft 传入英雄机进行操作
     */
    @Override
    public void work(HeroAircraft heroAircraft) {
        heroAircraft.decreaseHp(-healingAmount);
        System.out.println("HpSupply active");
        //产生对应音效
        MusicThread bloodBGM = new MusicThread(bgmPath);
        bloodBGM.start();
    }//保证英雄机生命值不超过最大值

    /**
     * 返回治疗量
     * @return
     */
    public int getHealingAmount() {
        return healingAmount;
    }
}
