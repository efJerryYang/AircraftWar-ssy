package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends AbstractProp{
    private List<Explosive> explosiveList = new ArrayList<>();

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        bgmPath = "src/videos/bomb_explosion.wav";
    }

    /**
     * 炸弹作用函数，打印"BombSupply active!",清除除了boss机以外的敌机和所有的敌机子弹
     * @param heroAircraft
     */
    @Override
    public void work(HeroAircraft heroAircraft) {
        explodeAll();
        System.out.println("BombSupply active");
        //产生对应音效
        MusicThread bloodBGM = new MusicThread(bgmPath);
        bloodBGM.start();
    }

    //增加观察者
    public void addExplosive(Explosive explosive) {
        explosiveList.add(explosive);
    }

    //删除观察者
    public void removeExplosive(Explosive explosive) {
        explosiveList.remove(explosive);
    }

    //通知所有观察者爆炸消失
    public void explodeAll() {
        for (Explosive explosive : explosiveList) {
            explosive.explode();
        }
    }
}
