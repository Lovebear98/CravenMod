package craven.util.CustomActions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ApplyCardModAction extends AbstractGameAction {
    private final AbstractCard Card;
    private final AbstractCardModifier Mod;

    public ApplyCardModAction(AbstractCard c, AbstractCardModifier mod) {
        this.Card = c;
        this.Mod = mod;
    }

    @Override
    public void update() {
        CardModifierManager.addModifier(Card, Mod);
        isDone = true;
    }
}
