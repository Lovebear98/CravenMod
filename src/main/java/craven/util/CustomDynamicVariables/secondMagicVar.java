package craven.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import craven.cards.AbstractHungryCard;

import static craven.util.otherutil.variables.Variables.SecondMagicKey;

public class secondMagicVar  extends DynamicVariable {
    @Override
    public String key() {
        return SecondMagicKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractHungryCard){
            return ((AbstractHungryCard) abstractCard).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractHungryCard){
            return ((AbstractHungryCard) abstractCard).secondMagic;
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractHungryCard){
            return ((AbstractHungryCard) abstractCard).baseSecondMagic;
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractHungryCard){
            return ((AbstractHungryCard) abstractCard).secondMagicUpgraded;
        }
        return false;
    }
}
