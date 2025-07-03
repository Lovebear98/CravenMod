package craven.util.CustomActions.cardmanip;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DelayedAddToExhaust extends AbstractGameAction {
    private AbstractCard cx;

    public DelayedAddToExhaust(AbstractCard cx) {
        this.cx = cx;
    }

    @Override
    public void update() {
        AbstractDungeon.player.exhaustPile.addToTop(cx);
        isDone = true;
    }
}
