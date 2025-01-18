package pathmaker.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathmaker.powers.BasePower;

import java.util.Objects;

import static pathmaker.PathmakerMod.makeID;

;

public class PathoftheCrocodilePower extends BasePower implements CloneablePowerInterface, OnReceivePowerPower {
    public static final String POWER_ID = makeID(PathoftheCrocodilePower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public AbstractCard.CardType GrowType;


    public PathoftheCrocodilePower(AbstractCreature owner, AbstractCard.CardType type) {
        this(owner, 1, type);
    }

    public PathoftheCrocodilePower(AbstractCreature owner, int amount) {
        this(owner, amount, AbstractCard.CardType.STATUS);
    }

    public PathoftheCrocodilePower(AbstractCreature owner, int amount, AbstractCard.CardType type) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.GrowType = type;
        updateDescription();
    }



    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToTop(new ReducePowerAction(owner, owner, this, 1));
    }


    public void updateDescription() {
        String s = DESCRIPTIONS[0];

        if(GrowType == AbstractCard.CardType.ATTACK){
            s += DESCRIPTIONS[1];
        }
        if(GrowType == AbstractCard.CardType.SKILL){
            s += DESCRIPTIONS[2];
        }
        if(GrowType == AbstractCard.CardType.POWER){
            s += DESCRIPTIONS[3];
        }
        if(GrowType == AbstractCard.CardType.STATUS){
            s += DESCRIPTIONS[4];
        }
        if(GrowType == AbstractCard.CardType.CURSE){
            s += DESCRIPTIONS[5];
        }

        s += amount + DESCRIPTIONS[6];
        if(amount != 1){
            s += DESCRIPTIONS[7];
        }
        s += DESCRIPTIONS[8];
        this.description = s;
    }
    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new PathoftheCrocodilePower(owner, amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(Objects.equals(abstractPower.ID, POWER_ID)){
            if(abstractPower instanceof PathoftheCrocodilePower){
                this.GrowType = ((PathoftheCrocodilePower) abstractPower).GrowType;
                updateDescription();
            }
        }
        return true;
    }
}