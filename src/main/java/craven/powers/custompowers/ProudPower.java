package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class ProudPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ProudPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURN_BASED = false;

    public ProudPower(AbstractCreature owner) {
        this(owner, owner, 1);
    }
    public ProudPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.source = source;
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
    public void atStartOfTurn() {
        super.atStartOfTurn();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.gameHandSize += this.amount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        AbstractDungeon.player.gameHandSize += stackAmount;
    }

    @Override
    public void onVictory() {
        super.onVictory();
        ///AbstractDungeon.player.gameHandSize -= this.amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new ProudPower(owner, source, amount);
    }
}