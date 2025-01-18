package pathmaker.util.otherutil.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import pathmaker.cards.AbstractBloomCard;

import static pathmaker.util.otherutil.variables.Variables.SecondMagicKey;

public class secondMagicVar  extends DynamicVariable {
    @Override
    public String key() {
        return SecondMagicKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractBloomCard){
            return ((AbstractBloomCard) abstractCard).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractBloomCard){
            return ((AbstractBloomCard) abstractCard).secondMagic;
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractBloomCard){
            return ((AbstractBloomCard) abstractCard).baseSecondMagic;
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractBloomCard){
            return ((AbstractBloomCard) abstractCard).secondMagicUpgraded;
        }
        return false;
    }
}
