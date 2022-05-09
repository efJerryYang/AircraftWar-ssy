package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.Explosive;

import java.util.List;

public abstract class AbstractEnemy extends AbstractAircraft implements Explosive {
    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 移动，y轴飞行出界后消失
     */
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    /**
     * 生成道具，返回道具列表
     * @return 生成的道具列表
     */
    public abstract List<AbstractProp> formProp();

    /**
     * 飞机坠毁对应得分
     * @return 得分
     */
    public abstract int getScore();

    /**
     * 调用decreaseHp函数使敌机hp扣除最大值实现敌机爆炸消失
     */
    @Override
    public void explode() {
        decreaseHp(Integer.MAX_VALUE);
    }
}
