package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.CheckRisk;

public class RiskCapPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(RiskCapPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RiskCapPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        CheckRisk();
    }
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        CheckRisk();
    }
    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        CheckRisk();
    }
    @Override
    public void onRemove() {
        super.onRemove();
        CheckRisk();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RiskCapPower(owner, amount);
    }
}