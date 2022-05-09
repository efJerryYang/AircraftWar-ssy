package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;

public class BloodPropFactory implements PropFactory{
    /**
     * 产生血量道具
     * @param abstractEnemy
     * @return
     */
    @Override
    public AbstractProp createProp(AbstractEnemy abstractEnemy) {
        return new BloodProp(abstractEnemy.getLocationX(), abstractEnemy.getLocationY(), 0, 1);
    }
}
