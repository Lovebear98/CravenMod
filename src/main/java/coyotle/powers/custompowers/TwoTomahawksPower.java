package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import coyotle.cards.generated.Valor;
import coyotle.powers.BasePower;

import static coyotle.CoyotleMod.makeID;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class TwoTomahawksPower extends BasePower implements CloneablePowerInterface, BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID(TwoTomahawksPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private boolean Upgraded = false;

    private static final boolean TURN_BASED = false;

    public TwoTomahawksPower(AbstractCreature owner, int amount, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.Upgraded = upgraded;

        updateDescription();
    }

    public TwoTomahawksPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0];
        if(Upgraded) {
            s += DESCRIPTIONS[1];
        }
            s += DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];

        if(amount > 1){
            s += DESCRIPTIONS[4];
        }

        s += DESCRIPTIONS[5];
        this.description = s;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if(card.hasTag(AbstractCard.CardTags.STRIKE)){
            flash();
            if(Upgraded) {
                action.exhaustCard = true;
            }
            addToBot(new MakeTempCardInHandAction(new Valor(), amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TwoTomahawksPower(owner, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(!Upgraded && abstractPower instanceof TwoTomahawksPower){
            if(((TwoTomahawksPower) abstractPower).Upgraded){
                this.Upgraded = true;
            }
        }
        return true;
    }
}