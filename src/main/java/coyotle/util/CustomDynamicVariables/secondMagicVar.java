package coyotle.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import coyotle.cards.AbstractProphecyCard;

import static coyotle.util.otherutil.variables.Variables.SecondMagicKey;

public class secondMagicVar  extends DynamicVariable {
    @Override
    public String key() {
        return SecondMagicKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractProphecyCard){
            return ((AbstractProphecyCard) abstractCard).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractProphecyCard){
            return ((AbstractProphecyCard) abstractCard).secondMagic;
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractProphecyCard){
            return ((AbstractProphecyCard) abstractCard).baseSecondMagic;
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractProphecyCard){
            return ((AbstractProphecyCard) abstractCard).secondMagicUpgraded;
        }
        return false;
    }
}
