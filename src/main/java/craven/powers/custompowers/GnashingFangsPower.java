package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.BasePower;

import static craven.CravenMod.makeID;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class GnashingFangsPower extends BasePower implements CloneablePowerInterface, OnReceivePowerPower {
    public static final String POWER_ID = makeID(GnashingFangsPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private final Color myColor = Color.YELLOW.cpy();


    public GnashingFangsPower(AbstractCreature owner) {
        this(owner, 1);
    }


    public GnashingFangsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount2 = amount;
    }



    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ResetAmount();
                isDone = true;
            }
        });
    }

    private void ResetAmount() {
        this.flash();
        this.amount2 = this.amount;
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {

        super.renderAmount(sb, x, y, c);

        if (this.amount2 != 0) {
            Color col = myColor.cpy();
            if (!this.isTurnBased) {
                col.a = c.a;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, col);
        }
    }


    @Override
    public int onLoseHp(int damageAmount) {
        if(amount2 > 0){
            int i = Math.min(amount2, damageAmount);
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    amount2 -= i;
                    isDone = true;
                }
            });
            if(i > 0){
                this.flash();
                addToBot(new ApplyPowerAction(owner, owner, new RavenousPower(owner, i)));
                return damageAmount - i;
            }
        }
        return super.onLoseHp(damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }



    @Override
    public AbstractPower makeCopy() {
        return new GnashingFangsPower(owner, amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(power instanceof GnashingFangsPower){
            this.amount2 += ((GnashingFangsPower) power).amount2;
        }
        return true;
    }
}