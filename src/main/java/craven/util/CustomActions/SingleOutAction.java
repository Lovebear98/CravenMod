package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static com.megacrit.cardcrawl.actions.defect.SeekAction.TEXT;

public class SingleOutAction extends AbstractGameAction {
    private final float startingDuration;
    private CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public SingleOutAction(int num) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.amount = num;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            Iterator var1;
            AbstractCard c;
            if (this.duration == this.startingDuration) {
                var1 = AbstractDungeon.player.powers.iterator();

                while(var1.hasNext()) {
                    AbstractPower p = (AbstractPower)var1.next();
                    p.onScry();
                }

                if (AbstractDungeon.player.drawPile.isEmpty() || this.amount <= 0) {
                    this.isDone = true;
                    return;
                }

                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                    tmpGroup.addToTop((AbstractCard) AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                }

                AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, false, TEXT[1]);
            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    AbstractDungeon.player.drawPile.moveToHand(c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            var1 = AbstractDungeon.player.discardPile.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                c.triggerOnScry();
            }

            this.tickDuration();
        }
    }
}
