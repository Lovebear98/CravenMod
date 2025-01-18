package pathmaker.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathmaker.orbs.AttackFlower;
import pathmaker.orbs.PowerFlower;
import pathmaker.orbs.SkillFlower;
import pathmaker.patches.interfaces.BloomInterface;
import pathmaker.powers.BasePower;

import static pathmaker.PathmakerMod.makeID;

;

public class BloomForFlowerPower extends BasePower implements CloneablePowerInterface, BloomInterface {
    public static final String POWER_ID = makeID(BloomForFlowerPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;
    private int UseCount = 0;

    public BloomForFlowerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void OnBloom(AbstractCard c) {
        BloomInterface.super.OnBloom(c);
        if(c != null){
            if(UseCount < amount){
                boolean FoundType = false;
                if(c.type == AbstractCard.CardType.ATTACK){
                    addToBot(new ChannelAction(new AttackFlower()));
                    FoundType = true;
                }
                if(!FoundType && c.type == AbstractCard.CardType.SKILL){
                    addToBot(new ChannelAction(new SkillFlower()));
                    FoundType = true;
                }
                if(!FoundType && c.type == AbstractCard.CardType.POWER){
                    addToBot(new ChannelAction(new PowerFlower()));
                    FoundType = true;
                }

                ///We only do anything noteworthy if we actually used a valid card
                if(FoundType){
                    ///Lock the power for the turn
                    this.flash();
                    this.UseCount += 1;
                    updateDescription();
                }
            }
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
        return new BloomForFlowerPower(owner, amount);
    }
}