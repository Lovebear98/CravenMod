package craven.potions.custompotions;

import basemod.abstracts.CustomPotion;
import basemod.abstracts.CustomSavable;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import craven.relics.WineCasket;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.SPECIAL;
import static craven.util.otherutil.variables.Variables.PrintEnergy;
import static craven.util.otherutil.variables.Variables.p;

public class IchorBordeaux extends CustomPotion implements CustomSavable<Integer> {

    public static final String POTION_ID = makeID(IchorBordeaux.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public boolean UsedThisTurn = false;
    private int Stacks = 0;

    public IchorBordeaux() {
        super(NAME, POTION_ID, SPECIAL, PotionSize.S, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.WHITE.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

    }
    ///Set up the info
    public void initializeData() {
        this.potency = getPotency();
        if(Stacks > 0){
            if(Stacks > 5){
                this.description = potionStrings.DESCRIPTIONS[0] + potionStrings.DESCRIPTIONS[3] + Stacks + potionStrings.DESCRIPTIONS[4] + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
            }else{
                this.description = potionStrings.DESCRIPTIONS[0] + PrintEnergy(Stacks) + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
            }
        }else{
            this.description = potionStrings.DESCRIPTIONS[0] + potionStrings.DESCRIPTIONS[3] + 0 + potionStrings.DESCRIPTIONS[4] + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    ///No discarding the potion unless you discard the relic
    @Override
    public boolean canDiscard() {
        if(p().hasRelic(WineCasket.ID)){
            return false;
        }
        return super.canDiscard();
    }

    ///Render the stack count on it
    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        FontHelper.blockInfoFont.getData().setScale(this.scale * 1.5f);
        FontHelper.renderFont(sb, FontHelper.blockInfoFont, String.valueOf(Stacks), this.hb.x+(this.hb.width * 0.65f), this.hb.y+(this.hb.height * 0.35f), Color.WHITE.cpy());
        FontHelper.blockInfoFont.getData().setScale(1.0F);
    }
    ///You can't use it twice in one turn
    @Override
    public boolean canUse() {
        if(UsedThisTurn || Stacks == 0){
            return false;
        }
        return super.canUse();
    }
    ///It's just a fancy energy potion
    @Override
    public void use(AbstractCreature abstractCreature) {
        UsedThisTurn = true;
        addToBot(new GainEnergyAction(Stacks));
        this.Stacks = 0;
    }
    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new IchorBordeaux();
    }

    @Override
    public Integer onSave() {
        return Stacks;
    }

    @Override
    public void onLoad(Integer integer) {
        if(integer != null){
            Stacks = integer;
            initializeData();
        }
    }

    public void AddStack() {
        this.Stacks += potency;
        this.flash();
        this.initializeData();
    }
}
