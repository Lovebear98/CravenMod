package craven.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.PowerTip;
import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.PrintEnergy;
import static craven.util.otherutil.variables.Variables.p;

public class SilverPromise extends AtlasRelic {
    ///This relic's RiskBonus is checked for in the MechanicsManager.

    public static final int StartingEnergy = 3;

    private static final String NAME = SilverPromise.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.SOLID;
    public SilverPromise() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = StartingEnergy;
        fixDescription();
    }


    @Override
    public void atTurnStart() {
        if(GameActionManager.turn != 0 && this.counter > 0){
            addToBot(new RelicAboveCreatureAction(p(), this));
            addToBot(new GainEnergyAction(this.counter));
            this.counter -= 1;
            fixDescription();
            if(counter <= 0){
                this.usedUp();
            }
        }
    }


    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.unusedUp();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.unusedUp();
    }


    @Override
    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;

        this.description = MSG[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
        this.counter = StartingEnergy;
        fixDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + PrintEnergy(counter) + DESCRIPTIONS[1] + 1 + DESCRIPTIONS[2];
    }
}
