package craven.util.CustomActions.cardmanip;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import craven.patches.interfaces.OnDevouredInterface;
import craven.patches.interfaces.SecondsInterface;

import static craven.util.otherutil.variables.Variables.p;

public class    DelayedDevourBottom extends AbstractGameAction {
    private AbstractCard c;

    @Override
    public void update() {
        if(p().exhaustPile.isEmpty()){
            this.isDone = true;
        }
        c = AbstractDungeon.player.exhaustPile.getBottomCard();
        AbstractDungeon.player.exhaustPile.removeCard(c);
        if(c instanceof SecondsInterface){
            if(((SecondsInterface) c).SecondsCount() > 0){
                ((SecondsInterface) c).OnSecondsTrigger();
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeSameInstanceOf()));
            }
        }
        if(c instanceof OnDevouredInterface){
            ((OnDevouredInterface) c).PostDevoured();
        }


        isDone = true;
    }
}
