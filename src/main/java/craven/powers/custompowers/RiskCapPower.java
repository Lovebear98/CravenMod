package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
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
        this.canGoNegative = true;
    }


    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        fixAmount();
        CheckRisk();
    }

    @Override
    public void stackPower(int stackAmount) {

        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        fixAmount();
        CheckRisk();
    }
    @Override
    public void reducePower(int reduceAmount) {

        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        fixAmount();
        CheckRisk();
    }
    @Override
    public void onRemove() {
        super.onRemove();
        CheckRisk();
    }

    private void fixAmount() {
        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }
    }

    public void updateDescription() {
        if(this.amount >= 0){
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }else{
            this.description = DESCRIPTIONS[2] + (amount * -1) + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RiskCapPower(owner, amount);
    }
}