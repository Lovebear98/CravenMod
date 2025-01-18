package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import coyotle.powers.BasePower;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.variables.Variables.p;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class TurnEndDiscardHandPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(TurnEndDiscardHandPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public TurnEndDiscardHandPower(AbstractCreature owner, int amount) {
        this(owner);
    }

    public TurnEndDiscardHandPower(AbstractCreature owner){
        this();
    }

    public TurnEndDiscardHandPower(){
        super(POWER_ID, TYPE, TURN_BASED, p(), 0);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToTop(new DiscardAction(p(), p(), p().hand.size(), true));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new TurnEndDiscardHandPower(owner, amount);
    }
}