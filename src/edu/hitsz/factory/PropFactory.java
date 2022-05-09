package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.prop.AbstractProp;


public interface PropFactory {
    AbstractProp createProp(AbstractEnemy abstractEnemy);
}
