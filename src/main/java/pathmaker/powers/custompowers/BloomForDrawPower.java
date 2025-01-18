package pathmaker.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathmaker.patches.interfaces.BloomInterface;
import pathmaker.powers.BasePower;

import static pathmaker.PathmakerMod.makeID;

;

public class BloomForDrawPower extends BasePower implements CloneablePowerInterface, BloomInterface {
    public static final String POWER_ID = makeID(BloomForDrawPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;
    private int Perm = 0;

    public BloomForDrawPower(AbstractCreature owner, int amount) {
        this(owner, amount, 0);
    }

    public BloomForDrawPower(AbstractCreature owner, int amount, int permamount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.Perm = permamount;
        updateDescription();
    }

    @Override
    public void OnBloom(AbstractCard c) {
        BloomInterface.super.OnBloom(c);
        this.flash();
        ///Gain energy based on the power's amount
        addToBot(new DrawCardAction(amount));
        ///Reduce the power EXCEPT how much is permanent
        addToTop(new ReducePowerAction(this.owner, this.owner, this, (amount - Perm)));
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0] + amount;
        if(amount > 1){
            s += DESCRIPTIONS[1];
        }else{
            s += DESCRIPTIONS[2];
        }
        if(amount > Perm){
            s += DESCRIPTIONS[3] +(amount-Perm) + DESCRIPTIONS[4];
        }
        this.description = s;
    }
    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new BloomForDrawPower(owner, amount);
    }
}