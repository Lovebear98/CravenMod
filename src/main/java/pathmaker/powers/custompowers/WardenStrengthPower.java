package pathmaker.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import pathmaker.patches.interfaces.BloomInterface;
import pathmaker.powers.BasePower;

import static pathmaker.PathmakerMod.makeID;

;

public class WardenStrengthPower extends BasePower implements CloneablePowerInterface, BloomInterface {
    public static final String POWER_ID = makeID(WardenStrengthPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public WardenStrengthPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void OnBloom(AbstractCard c) {
        BloomInterface.super.OnBloom(c);
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount)));
        addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount)));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }
    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new WardenStrengthPower(owner, amount);
    }
}