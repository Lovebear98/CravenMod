package coyotle.relics;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import coyotle.cardsmods.WindsingerMod;
import coyotle.character.CoyotleCharacter;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.variables.Variables.PrintEnergy;
import static coyotle.util.otherutil.variables.Variables.p;

public class MasteroftheHunt extends CoyotleRelic {
    ///This relic doesn't do anything here. It's checked for in the ShardManager.

    private static final String NAME = MasteroftheHunt.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.HEAVY;
    private boolean PlayedStrike = false;
    private boolean PlayedDefend = false;
    private boolean TriggeredThisTurn = false;


    private static final int amount = 2;
    public MasteroftheHunt() {
        super(ID, NAME, CoyotleCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        if(p() != null){
            for(AbstractCard c: AbstractDungeon.player.masterDeck.group){
                if(c.hasTag(AbstractCard.CardTags.STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
                    CardModifierManager.addModifier(c, new WindsingerMod());
                }
            }
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
        if(targetCard.hasTag(AbstractCard.CardTags.STRIKE)){
            addToBot(new GainEnergyAction(amount));
            PlayedStrike = true;
        }
        if(targetCard.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
            addToBot(new DrawCardAction(amount));
            PlayedDefend = true;
        }

        if(!TriggeredThisTurn && PlayedStrike && PlayedDefend){
            addToBot(new GainEnergyAction(amount));
            addToBot(new DrawCardAction(amount));
            TriggeredThisTurn = true;
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        ResetUsages();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        ResetUsages();
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        ResetUsages();
    }

    @Override
    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;
    }

    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
    }


    private void ResetUsages() {
        this.PlayedStrike = false;
        this.PlayedDefend = false;
        TriggeredThisTurn = false;
    }

    @Override
    public String getUpdatedDescription() {
        String s = DESCRIPTIONS[0] + PrintEnergy(amount) + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        if(amount > 1){
            s += DESCRIPTIONS[3];
        }
        s += DESCRIPTIONS[4];
        return s;
    }
}
