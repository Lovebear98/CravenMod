package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.PrintEnergy;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class CannedFoodPower extends BasePower implements CloneablePowerInterface, BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID(CannedFoodPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURN_BASED = false;
    private boolean Used = false;

    public CannedFoodPower(AbstractCreature owner) {
        this(owner, owner, 1);
    }
    public CannedFoodPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.source = source;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + PrintEnergy(amount) + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
    }


    @Override
    public void onVictory() {
        super.onVictory();
        ///AbstractDungeon.player.gameHandSize -= this.amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new CannedFoodPower(owner, source, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(!Used){
            if(power instanceof RavenousPower && target == owner){
                this.Used = true;
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
                addToTop(new GainEnergyAction(amount));
            }
        }
        return true;
    }
}