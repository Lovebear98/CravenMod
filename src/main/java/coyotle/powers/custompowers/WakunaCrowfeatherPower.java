package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import coyotle.powers.BasePower;
import coyotle.util.CustomActions.ProphecyAction;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.ShardManager.SapphireKey;
import static coyotle.util.otherutil.variables.Variables.PrintSapphire;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class WakunaCrowfeatherPower extends BasePower implements CloneablePowerInterface, BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID(WakunaCrowfeatherPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private boolean Killswitch = false;
    private boolean Upgraded = false;

    private static final boolean TURN_BASED = false;

    public WakunaCrowfeatherPower(AbstractCreature owner, int amount, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.Upgraded = upgraded;
    }

    public WakunaCrowfeatherPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0];
        if(Upgraded){
            s += DESCRIPTIONS[2];
        }else{
            s += DESCRIPTIONS[1];
        }

        s += DESCRIPTIONS[3] + PrintSapphire(amount) + DESCRIPTIONS[4];
        this.description = s;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if(!Killswitch){
            boolean ShouldTrigger = false;
            if(!Upgraded && card.costForTurn == 2){
                ShouldTrigger = true;
            }else if(card.costForTurn > -1 && card.costForTurn % 2 == 0){
                ShouldTrigger = true;
            }

            if(ShouldTrigger){
                flash();
                addToBot(new ProphecyAction(amount, SapphireKey));
            }else{
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
                Killswitch = true;
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WakunaCrowfeatherPower(owner, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(!Upgraded && abstractPower instanceof WakunaCrowfeatherPower){
            if(((WakunaCrowfeatherPower) abstractPower).Upgraded){
                this.Upgraded = true;
            }
        }
        return true;
    }
}