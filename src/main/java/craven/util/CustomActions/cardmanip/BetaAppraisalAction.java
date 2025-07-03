package craven.util.CustomActions.cardmanip;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static craven.util.otherutil.variables.UIText.AddText;
import static craven.util.otherutil.variables.Variables.p;

public class BetaAppraisalAction extends AbstractGameAction {
    private CardGroup Cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private AbstractCard DeckCard = new Dazed();
    private boolean ExhaustedDeck = false;

    public BetaAppraisalAction(int num) {
        this.amount = num;
        this.duration = this.startDuration = 0.25f;
    }

    @Override
    public void update() {
        if(p().drawPile.isEmpty()){
            ///Do stuff for this to just use created cards
            isDone = true;
        }


        if(duration == startDuration && AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            DeckCard = p().drawPile.getRandomCard(true);
            Cards.addToBottom(DeckCard);
            for(int i = amount; i > 0; i -= 1){
                Cards.addToTop(AbstractDungeon.returnTrulyRandomCardInCombat());
            }
            AbstractDungeon.gridSelectScreen.open(Cards, 1, true, AddText());
        }else{

            if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                if(c == DeckCard){
                    p().drawPile.moveToHand(c);
                    ExhaustedDeck = true;
                }else{
                    addToTop(new MakeTempCardInHandAction(c));
                }
                Cards.removeCard(c);
                AbstractDungeon.gridSelectScreen.selectedCards.remove(c);

                if(!ExhaustedDeck){
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(DeckCard, p().drawPile));
                    ExhaustedDeck = true;
                }
            }

            if(!Cards.isEmpty()){
                AbstractCard c = Cards.getBottomCard();
                addToTop(new MakeTempCardInExhaustAction(c, 1));
                Cards.removeCard(c);
            }

        }
        tickDuration();
    }
}
