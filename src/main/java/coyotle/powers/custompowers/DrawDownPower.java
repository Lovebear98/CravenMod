package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import coyotle.powers.BasePower;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.variables.Variables.p;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class DrawDownPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(DrawDownPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;

    private static final boolean TURN_BASED = false;
    private boolean Increased = false;
    public boolean FirstApplied = false;

    public DrawDownPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        if(owner instanceof AbstractPlayer){
            p().gameHandSize -= amount;
            if(p().gameHandSize <= 0){
                p().gameHandSize = 1;
            }
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(owner instanceof AbstractPlayer){
            p().gameHandSize -= stackAmount;
            if(p().gameHandSize <= 0){
                p().gameHandSize = 1;
            }
        }
    }

    @Override
    public void onRemove() {
        if(owner instanceof AbstractPlayer){
            if(!Increased){
                p().gameHandSize += amount;
                Increased = true;
            }
        }
        super.onRemove();
    }

    @Override
    public void onVictory() {
        if(owner instanceof AbstractPlayer){
            if(!Increased){
                p().gameHandSize += amount;
                Increased = true;
            }
        }
        super.onVictory();
    }

    public void updateDescription() {
        String s = DESCRIPTIONS[0] +amount + DESCRIPTIONS[1];
        if(amount != 1){
            s += DESCRIPTIONS[2];
        }

        s += DESCRIPTIONS[3];
        this.description = s;
    }


    @Override
    public AbstractPower makeCopy() {
        return new DrawDownPower(owner, amount);
    }

    public void FirstApp() {
        p().gameHandSize -= amount;
        if(p().gameHandSize <= 0){
            p().gameHandSize = 1;
        }
        FirstApplied = true;
    }
}