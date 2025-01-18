package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import coyotle.cards.AbstractShardCard;

import static coyotle.ui.ProphecyPanel.FixPreviewInstantly;
import static coyotle.util.otherutil.variables.Variables.p;

public class ReplaceShardAction extends AbstractGameAction {
    @Override
    public void update() {
        this.duration = 0f;
        if(p() != null){
            if(!p().drawPile.isEmpty()){
                boolean HasShard = false;
                for(AbstractCard c: p().drawPile.group){
                    if(c instanceof AbstractShardCard){
                        HasShard = true;
                        break;
                    }
                }
                if(!HasShard){
                    addToTop(new CreateShardAction());
                }
            }
            FixPreviewInstantly = true;
        }
        isDone = true;
    }
}
