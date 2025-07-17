package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

;import static craven.CravenMod.makeID;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class PalateCleanserPower extends BasePower implements CloneablePowerInterface, BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID(PalateCleanserPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private boolean Perm = false;

    public PalateCleanserPower(AbstractCreature owner) {
        this(owner, false);
    }

    public PalateCleanserPower(AbstractCreature owner, boolean Perm) {
        this(owner, 1, Perm);
    }


    public PalateCleanserPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public PalateCleanserPower(AbstractCreature owner, int amount, boolean Perm) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.Perm = Perm;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(Perm){
            this.amount = -1;
            updateDescription();
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if(Perm){
            this.amount = -1;
            updateDescription();
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(Perm){
            this.amount = -1;
            updateDescription();
        }
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0];
        if(!Perm){
            s += DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
        if(!Perm && amount != 1){
            s += DESCRIPTIONS[3];
        }
        s += DESCRIPTIONS[4];
        this.description = s;
    }

    @Override
    public void atStartOfTurn() {
        if(!Perm && amount != -1){
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
        super.atStartOfTurn();
    }

    @Override
    public AbstractPower makeCopy() {
        return new PalateCleanserPower(owner);
    }


    @Override
    public boolean betterOnApplyPower(AbstractPower pow, AbstractCreature target, AbstractCreature source) {
        if(!Perm){
            if(pow instanceof PalateCleanserPower){
                if(((PalateCleanserPower) pow).Perm){
                    this.Perm = true;
                    updateDescription();
                }
            }
        }
        return true;
    }
}