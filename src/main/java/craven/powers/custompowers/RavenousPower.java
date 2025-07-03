package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.Wiz.DevourCards;
import static craven.util.otherutil.variables.Variables.p;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class RavenousPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(RavenousPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURN_BASED = false;

    public RavenousPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + ConditionalS() + DESCRIPTIONS[3];
    }

    private String ConditionalS() {
        if(amount == 1){
            return "";
        }
        return DESCRIPTIONS[4];
    }

    @Override
    public void atStartOfTurn() {
        int e = Math.min(p().exhaustPile.size(), amount);
        if(e > 0){
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, e));
            DevourCards(e);
        }
        addToTop(new LoseHPAction(owner, owner, amount));
        super.atStartOfTurn();
    }

    @Override
    public AbstractPower makeCopy() {
        return new RavenousPower(owner, amount);
    }
}