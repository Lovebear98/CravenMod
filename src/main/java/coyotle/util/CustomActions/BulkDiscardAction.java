//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BulkDiscardAction extends AbstractGameAction {
    private ArrayList<AbstractCard> targetCards;
    private CardGroup group;

    public BulkDiscardAction(ArrayList<AbstractCard> targetCards) {
        this.targetCards = targetCards;// 16
        this.actionType = ActionType.DISCARD;// 17
        this.duration = Settings.ACTION_DUR_FAST;// 18
    }// 19

    public BulkDiscardAction(ArrayList<AbstractCard> targetCards, CardGroup group) {
        this.targetCards = targetCards;// 22
        this.group = group;// 23
        this.actionType = ActionType.DISCARD;// 24
        this.duration = Settings.ACTION_DUR_FAST;// 25
    }// 26

    public void update() {
        if (this.group == null) {// 31
            this.group = AbstractDungeon.player.hand;// 32
        }
        if(!targetCards.isEmpty()){
            AbstractCard c = targetCards.get(0);
            if(this.group.contains(c)){
                this.group.moveToDiscardPile(c);// 35
                GameActionManager.incrementDiscard(false);// 36
                c.triggerOnManualDiscard();// 37
                targetCards.remove(c);
            }
        }else{
            isDone = true;
        }

        this.tickDuration();// 41
    }// 42
}
