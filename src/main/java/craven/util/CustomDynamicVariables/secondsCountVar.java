package craven.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import craven.patches.interfaces.SecondsInterface;

import static craven.util.otherutil.variables.Variables.SecondsCountKey;

public class secondsCountVar extends DynamicVariable {
    @Override
    public String key() {
        return SecondsCountKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if(abstractCard instanceof SecondsInterface){
            return ((SecondsInterface) abstractCard).SecondsCount() != ((SecondsInterface) abstractCard).BaseSecondsCount();
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if(abstractCard instanceof SecondsInterface){
            return ((SecondsInterface) abstractCard).SecondsCount();
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if(abstractCard instanceof SecondsInterface){
            return ((SecondsInterface) abstractCard).BaseSecondsCount();
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if(abstractCard instanceof SecondsInterface){
            return ((SecondsInterface) abstractCard).SecondsUpgraded();
        }
        return false;
    }
}
