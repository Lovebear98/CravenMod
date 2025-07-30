package craven.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.Risk;
import static craven.util.otherutil.MechanicManager.TrueRiskCap;

public class TherianPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(TherianPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TherianPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        int Remainder = TrueRiskCap() - Risk;
        String s = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if(Remainder > 0){
            s += DESCRIPTIONS[2] + (Remainder * amount);
        }
        this.description = s;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        int Remainder = TrueRiskCap() - Risk;
        if(Remainder > 0){
            addToBot(new GainBlockAction(owner, owner, amount * Remainder));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TherianPower(owner, amount);
    }
}