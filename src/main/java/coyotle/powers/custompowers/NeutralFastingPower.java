package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import coyotle.powers.BasePower;

import static coyotle.CoyotleMod.makeID;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class NeutralFastingPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(NeutralFastingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;

    private static final boolean TURN_BASED = false;
    private boolean Increased = false;
    public boolean FirstApplied = false;

    public NeutralFastingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);

        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        if (powerStrings.DESCRIPTIONS[1].isEmpty()) {
            sb.append(LocalizedStrings.PERIOD);
        } else {
            sb.append(powerStrings.DESCRIPTIONS[1]);
        }

        this.description = sb.toString();
    }

    public void atStartOfTurn() {
        this.addToBot(new LoseEnergyAction(this.amount));
        this.flash();
    }


    @Override
    public AbstractPower makeCopy() {
        return new NeutralFastingPower(owner, amount);
    }
}