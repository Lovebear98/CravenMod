package pathmaker.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathmaker.powers.BasePower;;import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.variables.Variables.p;

public class EndlessExpansePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(EndlessExpansePower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;
    private int UseCount = 0;

    public EndlessExpansePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        ///If the card can't be used
        if(!card.canUse(p(), null) && UseCount < amount){
            this.flash();
            ///Gain energy
            addToBot(new GainEnergyAction(1));
            ///Lock the power for the turn
            this.UseCount += 1;
            updateDescription();
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.UseCount = 0;
        updateDescription();
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
        return new EndlessExpansePower(owner, amount);
    }
}