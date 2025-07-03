package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.cards.generated.Mechanics.Rations;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class HungerPangsPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(HungerPangsPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public HungerPangsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
        addToBot(new HungerPangsAction());
    }

    private int cappedamount(int i){
        return Math.min(amount, p().hand.size());
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if(amount != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];

        this.description = s;
    }

    @Override
    public AbstractPower makeCopy() {
        return new HungerPangsPower(owner, amount);
    }

    private class HungerPangsAction extends AbstractGameAction {
        @Override
        public void update() {
            int i = cappedamount(amount);
            addToTop(new MakeTempCardInHandAction(new Rations(), i));
            addToTop(new ExhaustAction(p(), p(), i, true));
            isDone = true;
        }
    }
}