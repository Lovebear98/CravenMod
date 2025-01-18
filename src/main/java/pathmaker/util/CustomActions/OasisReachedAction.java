package pathmaker.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import pathmaker.patches.interfaces.OasisReachedInterface;

import static pathmaker.util.otherutil.variables.Variables.p;

public class OasisReachedAction extends AbstractGameAction {
    private final AbstractCard card;

    public OasisReachedAction(AbstractCard abstractOasisCard) {
        this.card = abstractOasisCard;
    }

    @Override
    public void update() {
        for(AbstractCard c: p().hand.group){
            if(c instanceof OasisReachedInterface){
                ((OasisReachedInterface) c).OnOasisReached(card);
            }
        }
        for(AbstractCard c: p().drawPile.group){
            if(c instanceof OasisReachedInterface){
                ((OasisReachedInterface) c).OnOasisReached(card);
            }
        }
        for(AbstractCard c: p().discardPile.group){
            if(c instanceof OasisReachedInterface){
                ((OasisReachedInterface) c).OnOasisReached(card);
            }
        }


        for(AbstractPower p: p().powers){
            if(p instanceof OasisReachedInterface){
                ((OasisReachedInterface) p).OnOasisReached(card);
            }
        }


        for(AbstractRelic r: p().relics){
            if(r instanceof OasisReachedInterface){
                ((OasisReachedInterface) r).OnOasisReached(card);
            }
        }

        isDone = true;
    }
}
