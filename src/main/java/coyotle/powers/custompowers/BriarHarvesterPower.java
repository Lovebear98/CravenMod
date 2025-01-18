package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import coyotle.patches.interfaces.DreamInterface;
import coyotle.powers.BasePower;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.variables.Variables.PrintEnergy;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class BriarHarvesterPower extends BasePower implements CloneablePowerInterface, DreamInterface {
    public static final String POWER_ID = makeID(BriarHarvesterPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public BriarHarvesterPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + PrintEnergy(amount) + DESCRIPTIONS[1];
    }


    @Override
    public void onEnterDream() {
        DreamInterface.super.onEnterDream();
        addToBot(new GainEnergyAction(amount));
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new BriarHarvesterPower(owner, amount);
    }

}