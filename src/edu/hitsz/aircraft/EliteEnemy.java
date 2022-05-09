package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.application.MainDataController;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.factory.BulletPropFactory;
import edu.hitsz.factory.PropFactory;
import edu.hitsz.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 可以射击
 * @author hitsz
 */

public class EliteEnemy extends AbstractEnemy{
    /**
     * @param locationX 精英敌机位置x坐标
     * @param locationY 精英敌机位置y坐标
     * @param speedX 提供精英敌机水平速度的值,这里根据提供的速度随机向左或向右
     * @param speedY 竖直速度
     * @param hp    初始生命值
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX*(1 - 2*(int)(2*Math.random())), speedY, hp);
        this.shootNum = 1;
        this.power = (int)(30*MainDataController.getEnemyStrengthenRate());
        this.direction = 1;
    }

    /**
     * 产生道具
     * @return 生成的道具列表
     */
    @Override
    public List<AbstractProp> formProp() {
        List<AbstractProp> props= new LinkedList<>();
        double chance = Math.random();
        PropFactory propFactory;
        if (chance >= 0.1) {
            if (chance >= 0.7) {
                propFactory = new BloodPropFactory();//0.3的概率产生血量道具
            } else if (chance >= 0.4) {
                propFactory = new BombPropFactory();//0.3的概率产生炸弹道具
            } else {
                propFactory = new BulletPropFactory();//0.3的概率产生火力道具
            }
            props.add(propFactory.createProp(this));
        }//0.1的概率不产生道具
        return props;
    }

    @Override
    public int getScore() {
        return 60;
    }
}
