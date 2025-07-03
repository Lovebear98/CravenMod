package craven.util.CustomActions.cardmanip;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import craven.util.CustomActions.CustomGameEffects.vfx.ShowCardAndAddToExhaustEffect;

public class MakeTempCardInExhaustAction extends AbstractGameAction {
    private final int numCards;
    private final AbstractCard c;

    public MakeTempCardInExhaustAction(AbstractCard card, int amount) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.numCards = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.c = card;
    }

    @Override
    public void update() {
        if (this.c.type != AbstractCard.CardType.CURSE && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }

        if (this.duration == this.startDuration) {
                for(int i = 0; i < this.numCards; ++i) {
                    AbstractDungeon.effectList.add(0, new ShowCardAndAddToExhaustEffect(this.makeNewCard()));
                }
        }
        this.tickDuration();
    }

    private AbstractCard makeNewCard() {
        return this.c.makeStatEquivalentCopy();
    }
}
