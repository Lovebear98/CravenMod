package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.CheckRisk;

public class MealPrepPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(MealPrepPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MealPrepPower(AbstractCreature owner) {
        this(owner, 0);
    }
    public MealPrepPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onRemove() {
        super.onRemove();
    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MealPrepPower(owner, amount);
    }

    public void Trigger() {
        this.flash();
        addToBot(new ArmamentsAction(true));
    }
}