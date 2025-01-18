package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import coyotle.patches.interfaces.DreamInterface;
import coyotle.powers.BasePower;
import coyotle.util.CustomActions.VulnAllAction;
import coyotle.util.CustomActions.WeakAllAction;

import static coyotle.CoyotleMod.makeID;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class DreamsmokeMysticPower extends BasePower implements CloneablePowerInterface, BetterOnApplyPowerPower, DreamInterface {
    public static final String POWER_ID = makeID(DreamsmokeMysticPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private boolean Upgraded = false;

    private static final boolean TURN_BASED = false;

    public DreamsmokeMysticPower(AbstractCreature owner, int amount, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.Upgraded = upgraded;
    }

    public DreamsmokeMysticPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if(Upgraded){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        this.description = s;
    }

    @Override
    public void onEnterDream() {
        DreamInterface.super.onEnterDream();
        addToBot(new WeakAllAction(owner, amount));
        if(Upgraded){
            addToBot(new VulnAllAction(owner, amount));
        }
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new DreamsmokeMysticPower(owner, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(!Upgraded && abstractPower instanceof DreamsmokeMysticPower){
            if(((DreamsmokeMysticPower) abstractPower).Upgraded){
                this.Upgraded = true;
            }
        }
        return true;
    }
}