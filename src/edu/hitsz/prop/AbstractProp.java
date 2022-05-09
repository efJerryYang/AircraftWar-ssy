package edu.hitsz.prop;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraft.HeroAircraft;

/**
 * 所有道具的抽象父类
 * 血量道具，火力道具，炸弹道具
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    protected String bgmPath;
    /**
     * @param locationX 道具位置x坐标
     * @param locationY 道具位置y坐标
     * @param speedX 道具速度
     * @param speedY 道具速度
     */
    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 移动，y轴飞行出界后消失
     */
    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
    /**
     * 道具作用方法，所有道具必须实现
     * @param heroAircraft
     */
    public abstract void work(HeroAircraft heroAircraft);
}
