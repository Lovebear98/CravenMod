package pathmaker.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pathmaker.cards.AbstractOasisCard;

import static pathmaker.util.otherutil.variables.Variables.p;

public class OasisAction extends AbstractGameAction {
    @Override
    public void update() {
        if(p() != null){
            if(!p().hand.group.isEmpty()){
                for(AbstractCard c: AbstractDungeon.player.hand.group){
                    if(c instanceof AbstractOasisCard){
                        ((AbstractOasisCard) c).onDrawOtherCard();
                    }
                }
            }
        }
        isDone = true;
    }
}

