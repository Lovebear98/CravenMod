package pathmaker.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathmaker.patches.interfaces.BloomInterface;
import pathmaker.powers.BasePower;
import pathmaker.util.CustomActions.ResolveBloomAction;

import static pathmaker.PathmakerMod.makeID;

;

public class RepeatBloomPower extends BasePower implements CloneablePowerInterface, BloomInterface {
    public static final String POWER_ID = makeID(RepeatBloomPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;
    private int UseCount = 0;
    private boolean InUse = false;

    public RepeatBloomPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.UseCount = 0;
        updateDescription();
    }

    @Override
    public void OnBloom(AbstractCard c) {
        BloomInterface.super.OnBloom(c);
        ///If uses are left and the Bloom isn't caused by us
        if(UseCount < amount && !InUse){
            ///Become in use
            this.flash();
            this.InUse = true;
            ///Bloom again
            addToBot(new ResolveBloomAction(null));
            ///Queue up unlocking the power
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    InUse = false;
                    isDone = true;
                }
            });
            ///Increment
            this.UseCount += 1;
            ///update description
            updateDescription();
        }
    }

    public void updateDescription() {
        String s = "";
        if(amount == 1){
            s = DESCRIPTIONS[0];
        }else{
            s = DESCRIPTIONS[1]+amount+DESCRIPTIONS[2];
        }
        if(UseCount < amount){
            s += DESCRIPTIONS[3]+UseCount+DESCRIPTIONS[4]+amount+DESCRIPTIONS[5];
        }else{
            s += DESCRIPTIONS[6];
        }
        this.description = s;
    }
    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new RepeatBloomPower(owner, amount);
    }
}