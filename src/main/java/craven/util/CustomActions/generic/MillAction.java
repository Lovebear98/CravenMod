package craven.util.CustomActions.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class MillAction extends AbstractGameAction
{
    private float startingDuration;
    private int startAmount = 0;

    private int millNum;
    private int postReturnAmount = 0;
    private AbstractCard ignoredCard;
    private AbstractCard returnIgnoredCard;


    public MillAction(int numCards)
    {
        amount = numCards;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    public MillAction(int numCards, AbstractCard cardToIgnore)
    {
        amount = numCards;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        ignoredCard = cardToIgnore;
    }

    public MillAction(int numCards, boolean block, int postReturnAmount, AbstractCard returnToIgnore)
    {
        amount = numCards;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        this.postReturnAmount = postReturnAmount;
        returnIgnoredCard = returnToIgnore;
    }

    public void update()
    {
        if(amount > 0)
        {
            PreMill();
            while (amount > 0)
            {
                if (AbstractDungeon.player.drawPile.size() > 0)
                    ProcessMill();
                amount--;
            }
            PostMill();
            isDone = true;
        }
    }

    private void PreMill()
    {

    }

    private void ProcessMill()
    {
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        millNum++;
        if(card != null && ((!card.equals(ignoredCard))))
        {
            CheckRebound(card);
            return;
        }
    }

    private void CheckRebound(AbstractCard card)
    {
            MoveToDiscard(card);
    }



    private void MoveToDiscard(AbstractCard card)
    {
        CardCrawlGame.sound.play("CARD_REJECT");
        AbstractDungeon.player.drawPile.moveToDiscardPile(AbstractDungeon.player.drawPile.getTopCard());

    }

    private void PostMill()
    {
        AbstractDungeon.player.hand.applyPowers();
    }




}
