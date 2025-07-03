package craven.util.CustomActions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RemoveCardModAction extends AbstractGameAction {
    private final AbstractCard c;
    private final String id;
    private final boolean b;

    public RemoveCardModAction(AbstractCard card, String identifier, boolean inherent) {
        this.c = card;
        this.id = identifier;
        this.b = inherent;
    }

    @Override
    public void update() {
        CardModifierManager.removeModifiersById(c, id, b);
        isDone = true;
    }
}
