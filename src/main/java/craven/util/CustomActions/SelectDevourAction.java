package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import craven.util.CustomActions.cardmanip.DelayedDevourSpecific;

import static craven.util.otherutil.variables.UIText.ChooseACardToText;
import static craven.util.otherutil.variables.UIText.DevourText;
import static craven.util.otherutil.variables.Variables.p;

public class SelectDevourAction extends AbstractGameAction {
    private boolean SelectedCards = false;

    public SelectDevourAction(int i) {
        this.amount = i;
        this.duration = this.startDuration = 0.15f;
    }

    @Override
    public void update() {
        if(this.duration == startDuration && !SelectedCards){
            if(!p().exhaustPile.isEmpty()){
                AbstractDungeon.gridSelectScreen.open(p().exhaustPile, amount, true, ChooseACardToText(amount) + DevourText());
            }
            SelectedCards = true;
        }
        if(SelectedCards){
            if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.effectsQueue.add(new DelayedDevourSpecific(c));
                AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
            }
        }
        tickDuration();
    }
}
