package craven.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import craven.powers.BasePower;
import craven.relics.VoiceOfReason;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.Wiz.DevourCards;
import static craven.util.otherutil.variables.Variables.p;

;

///Hidden 'smoothing' synergy, you will ALWAYS generate a Howling Bluegrass while you have this power

public class RavenousPower extends BasePower implements CloneablePowerInterface, HealthBarRenderPower {
    public static final String POWER_ID = makeID(RavenousPower.class.getSimpleName());
    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURN_BASED = false;

    public RavenousPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        String s;
        if(p() != null && p().hasRelic(VoiceOfReason.ID)){
            s = DESCRIPTIONS[5] + (amount * 2) + DESCRIPTIONS[6];
        }else{
            s = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        s += amount + DESCRIPTIONS[2] + ConditionalS() + DESCRIPTIONS[3];
        this.description =  s;
    }

    private String ConditionalS() {
        if(amount == 1){
            return "";
        }
        return DESCRIPTIONS[4];
    }

    @Override
    public void atStartOfTurn() {
        if(!p().hasRelic(VoiceOfReason.ID)){
        Trigger(true);
        }
        super.atStartOfTurn();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(p().hasRelic(VoiceOfReason.ID)) {
            Trigger(true);
        }
        super.atEndOfTurn(isPlayer);
    }


    public void Trigger(boolean DealDamage) {
        if(!p().hasRelic(VoiceOfReason.ID)){
            this.flash();
            int e = Math.min(p().exhaustPile.size(), amount);
            if(e > 0){
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, e));
                DevourCards(e);
            }
            if(DealDamage){
                addToTop(new LoseHPAction(owner, owner, amount));
            }
            if(p().hasPower(BrazenPower.POWER_ID)){
                AbstractMonster m = AbstractDungeon.getRandomMonster();
                addToBot(new LoseHPAction(m, owner, amount));
            }
        }else if(p().hasRelic(VoiceOfReason.ID)){
            this.flash();
            int e = Math.min(p().exhaustPile.size(), amount);
            if(e > 0){
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, e));
                DevourCards(e);
            }
            if(DealDamage){
                addToTop(new DamageAction(owner, new DamageInfo(owner, amount * 2, DamageInfo.DamageType.NORMAL)));
            }
            if(p().hasPower(BrazenPower.POWER_ID)){
                AbstractMonster m = AbstractDungeon.getRandomMonster();
                addToTop(new DamageAction(m, new DamageInfo(owner, amount * 2, DamageInfo.DamageType.NORMAL)));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RavenousPower(owner, amount);
    }

    @Override
    public int getHealthBarAmount() {
        ///Get the damage we'll ostensibly deal, always flooring with 0
        int result = amount;

        ///If they have Buffer, set the damage to 0.
        if(this.owner.hasPower(BufferPower.POWER_ID)){
            result = 0;
        }
        ///If the target is Intangible
        if(result > 0 && (this.owner.hasPower(IntangiblePower.POWER_ID) || this.owner.hasPower(IntangiblePlayerPower.POWER_ID))){
            ///Set damage to 1
            result = 1;
        }

        ///If they're Invincible
        if(result > 0 && this.owner.hasPower(InvinciblePower.POWER_ID)){
            ///Get that power
            InvinciblePower p = (InvinciblePower) owner.getPower(InvinciblePower.POWER_ID);
            ///Get its remaining damage that can be dealt, at LEAST 0
            int i = Math.max(0, p.amount);
            ///And cap our output based on the invincible cap (i)
            result = Math.min(result, i);
        }
        return result;
    }

    public static final Color RavenousColor = new Color(255/255f, 232/255f, 194/255f, 1);
    @Override
    public Color getColor() {
        return RavenousColor.cpy();
    }
}