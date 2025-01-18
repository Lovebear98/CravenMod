package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import coyotle.cards.AbstractShardCard;

import java.util.ArrayList;

import static coyotle.ui.ProphecyPanel.FixPreviewInstantly;
import static coyotle.util.otherutil.variables.Variables.p;

public class ShardsToBottomAction extends AbstractGameAction {
    public ShardsToBottomAction(){
        this.duration = this.startDuration = 1.0f;
    }
    @Override
    public void update() {
        ArrayList<AbstractCard> seen = new ArrayList<>();
            if(p() != null){
                if(!p().drawPile.group.isEmpty()){
                    for(AbstractCard c: p().drawPile.group){
                        if(c instanceof AbstractShardCard){
                            if(!seen.contains(c)){
                                seen.add(c);
                                this.duration -= 0.01f;
                            }
                        }
                    }
                }
            }

            this.duration -= 0.01f;
            for(AbstractCard c: seen){
                p().drawPile.removeCard(c);
            }

            this.duration -= 0.01f;
            for(AbstractCard c: seen){
                p().drawPile.addToBottom(c);
            }

        FixPreviewInstantly = true;

        isDone = true;
    }
}
