package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

/// This power does nothing on its own, and is checked for in the Ravenous Power.

public class BrazenPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(BrazenPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURN_BASED = false;

    public BrazenPower(AbstractCreature owner){
        this(owner, 0);
    }
    public BrazenPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = 0;
    }

    @Override
    public void stackPower(int stackAmount) {
        if(AbstractDungeon.player.hasPower(RavenousPower.POWER_ID)){
            RavenousPower pow = (RavenousPower) p().getPower(RavenousPower.POWER_ID);
            for(int i = stackAmount; i > 0; i -= 1){
                pow.Trigger(false);
            }
        }
    }

    public void updateDescription() {
        this.description =  DESCRIPTIONS[0];
    }
    @Override
    public AbstractPower makeCopy() {
        return new BrazenPower(owner, amount);
    }
}