package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;

public class FattenedPower extends BasePower implements CloneablePowerInterface, OnReceivePowerPower {
    public static final String POWER_ID = makeID(FattenedPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public FattenedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if(this.amount != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        this.description = s;
    }

    @Override
    public AbstractPower makeCopy() {
        return new FattenedPower(owner, amount);
    }



    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(source != owner && source != null && power.type == PowerType.DEBUFF){
            this.flash();
            int i = 0;
            if(power instanceof FattenedPower){
                i += power.amount;
            }
            addToTop(new DrawCardAction(this.amount + i));
            addToTop(new RemoveSpecificPowerAction(owner, source, this));
        }
        return true;
    }
}