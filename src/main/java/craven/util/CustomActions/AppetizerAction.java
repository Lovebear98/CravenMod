package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import craven.cards.attack.Appetizer;

import static craven.util.otherutil.ConfigManager.EnableEarCandy;
import static craven.util.otherutil.MechanicManager.DevourInternal;
import static craven.util.otherutil.variables.UIText.DevourText;
import static craven.util.otherutil.variables.Variables.p;

public class AppetizerAction extends AbstractGameAction {
    private final Appetizer Card;
    private final float startingDuration;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public AppetizerAction(Appetizer appetizer) {
        this.Card = appetizer;

        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;

        this.amount = 1;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {

            /// If this just started
            if (this.duration == this.startingDuration) {

                /// End the action immediately if we have no cards in hand
                if (AbstractDungeon.player.hand.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                /// Otherwise, open a hand select screen  to pick 1 card
                AbstractDungeon.handCardSelectScreen.open(DevourText(), this.amount, false, false);

                /// But if it's not the start and our selected cards aren't empty
            } else if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                /// Get the first selected card and yeet it
                AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();

                DevourInternal(c, p().hand);

                c.purgeOnUse = true;
                Card.StoredCard = c.makeStatEquivalentCopy();
                Card.cardsToPreview = Card.StoredCard.makeStatEquivalentCopy();

                AbstractDungeon.handCardSelectScreen.selectedCards.removeCard(c);
            }

            this.tickDuration();

            if (this.isDone) {
                if(EnableEarCandy){
                    CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
                }
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }
        }
    }
}
