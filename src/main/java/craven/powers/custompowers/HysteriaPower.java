package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.patches.visual.AttackEffectEnum;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class HysteriaPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(HysteriaPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    private boolean WasUsed = false;

    public HysteriaPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onDeath() {
        super.onDeath();
        if(!WasUsed){
            WasUsed = true;
            addToBot(new DamageAllEnemiesAction(p(), amount, DamageInfo.DamageType.THORNS, AttackEffectEnum.SHADY));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new HysteriaPower(owner, amount);
    }




}