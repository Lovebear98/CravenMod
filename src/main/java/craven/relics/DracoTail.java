package craven.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import craven.character.CravenCharacter;
import craven.patches.DracoTailField;
import craven.powers.custompowers.RavenousPower;
import craven.util.CustomActions.imported.PhantomPlayCardAction;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

import static craven.CravenMod.makeID;
import static craven.patches.DracoTailField.inDracoTailField;
import static java.lang.Boolean.TRUE;

public class DracoTail extends AtlasRelic implements CustomBottleRelic, CustomSavable<Integer> {

    private static final String NAME = DracoTail.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.FLAT;


    private static AbstractCard card;
    private Boolean cardSelected = TRUE;

    public DracoTail() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        fixDescription();
    }

    public Predicate<AbstractCard> isOnCard() {
        return inDracoTailField::get;
    }

    @Override
    public Integer onSave() {
        if (card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(card);
        } else {
            return -1;
        }
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                inDracoTailField.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;

        CardGroup group = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards().getPowers());
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3] + name + DESCRIPTIONS[2], false, false, false, false);
    }
    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                inDracoTailField.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            inDracoTailField.set(card, true);
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.INCOMPLETE) {
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }

    @Override
    public boolean canSpawn() {
        return CardHelper.hasCardType(AbstractCard.CardType.POWER);
    }

    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
