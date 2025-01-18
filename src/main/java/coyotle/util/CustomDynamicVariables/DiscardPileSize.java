package coyotle.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static coyotle.util.otherutil.variables.Variables.DiscardPileSizeNum;
import static coyotle.util.otherutil.variables.Variables.DiscardSizeKey;

public class DiscardPileSize extends DynamicVariable {
    @Override
    public String key() {
        return DiscardSizeKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return value(null) > baseValue(null);
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return DiscardPileSizeNum();
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
