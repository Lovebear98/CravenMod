package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.CheckRisk;

public class LoseRiskCapPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(LoseRiskCapPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public LoseRiskCapPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new ApplyPowerAction(owner, owner, new RiskCapPower(owner, -amount)));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseRiskCapPower(owner, amount);
    }
}