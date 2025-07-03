package craven.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import craven.cards.generated.Mechanics.Rations;
import craven.character.CravenCharacter;
import craven.util.CustomActions.cardmanip.MakeTempCardInExhaustAction;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class SilverPromise extends AtlasRelic {
    ///This relic's RiskBonus is checked for in the MechanicsManager.

    public static final int FreeRations = 1;

    private static final String NAME = SilverPromise.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.SOLID;
    public SilverPromise() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);

        AbstractCard c = new Rations();
        this.tips.add(new CardPowerTip(c));
    }


    @Override
    public void atTurnStart() {
        if(GameActionManager.turn != 1){
            addToBot(new RelicAboveCreatureAction(p(), this));
            addToBot(new MakeTempCardInExhaustAction(new Rations(), 1));
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
    }

    @Override
    public void onVictory() {
        super.onVictory();
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

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + FreeRations + DESCRIPTIONS[1];
    }
}
