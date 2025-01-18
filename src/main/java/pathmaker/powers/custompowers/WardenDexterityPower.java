package pathmaker.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import pathmaker.patches.interfaces.BloomInterface;
import pathmaker.powers.BasePower;

import static pathmaker.PathmakerMod.makeID;

;

public class WardenDexterityPower extends BasePower implements CloneablePowerInterface, BloomInterface {
    public static final String POWER_ID = makeID(WardenDexterityPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public WardenDexterityPower(AbstractCreature owner, int amount) {
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
        addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount)));
        addToBot(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, amount)));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }
    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new WardenDexterityPower(owner, amount);
    }
}