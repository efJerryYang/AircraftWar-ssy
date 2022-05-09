package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BulletProp;

public class BulletPropFactory implements PropFactory{
    /**
     * 产生火力道具
     * @param abstractEnemy
     * @return
     */
    @Override
    public AbstractProp createProp(AbstractEnemy abstractEnemy) {
        return new BulletProp(abstractEnemy.getLocationX(), abstractEnemy.getLocationY(), 0, 1);
    }
}

