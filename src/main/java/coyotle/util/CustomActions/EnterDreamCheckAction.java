package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import coyotle.cards.generated.Mechanics.TheDream;

import static coyotle.util.otherutil.DreamManager.EnterDream;

public class EnterDreamCheckAction extends AbstractGameAction {
    private final AbstractCard card;


    public EnterDreamCheckAction(){
        this(new TheDream());
    }
    public EnterDreamCheckAction(AbstractCard abstractCard) {
    this.card = abstractCard;
    }

    @Override
    public void update() {
        EnterDream(card);
        isDone = true;
    }
}
