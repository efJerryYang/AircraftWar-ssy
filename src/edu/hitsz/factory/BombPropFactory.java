package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;

public class BombPropFactory implements PropFactory{
    /**
     * 产生炸弹道具
     * @param abstractEnemy
     * @return
     */
    @Override
    public AbstractProp createProp(AbstractEnemy abstractEnemy) {
        return new BombProp(abstractEnemy.getLocationX(), abstractEnemy.getLocationY(), 0, 1);
    }
}
