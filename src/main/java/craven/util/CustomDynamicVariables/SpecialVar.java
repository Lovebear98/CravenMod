package craven.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import craven.cards.AbstractHungryCard;

import static craven.util.otherutil.variables.Variables.SecondMagicKey;
import static craven.util.otherutil.variables.Variables.SpecialVarKey;

public class SpecialVar extends DynamicVariable {
    @Override
    public String key() {
        return SpecialVarKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractHungryCard){
            return ((AbstractHungryCard) abstractCard).SpecialVar() != 0;
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractHungryCard){
            return ((AbstractHungryCard) abstractCard).SpecialVar();
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return false;
    }
}
