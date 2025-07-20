package craven.util.CustomActions.cardmanip;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static craven.util.otherutil.MechanicManager.DevourInternal;
import static craven.util.otherutil.variables.Variables.p;

public class DelayedDevourBottom extends AbstractGameAction {
    private AbstractCard c;

    @Override
    public void update() {
        if(p().exhaustPile.isEmpty()){
            this.isDone = true;
        }
        c = AbstractDungeon.player.exhaustPile.getBottomCard();
        DevourInternal(c);


        isDone = true;
    }
}
