package craven.potions.custompotions;

import basemod.abstracts.CustomPotion;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static craven.CravenMod.makeID;

public class Chaser extends CustomPotion {

    public static final String POTION_ID = makeID(Chaser.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public Chaser() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.WHITE.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[4], potionStrings.DESCRIPTIONS[5]));
    }

    public void initializeData() {
        this.potency = getPotency();

        this.description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[4], potionStrings.DESCRIPTIONS[5]));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        for(int i = potency; i > 0; i -= 1){
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat();
            if(!c.isEthereal){
                CardModifierManager.addModifier(c, new EtherealMod());
            }
            if(!c.exhaust && !(c.type == AbstractCard.CardType.POWER)){
                CardModifierManager.addModifier(c, new ExhaustMod());
            }
            addToBot(new MakeTempCardInDiscardAction(c, 1));
        }
    }

    @Override
    public int getPotency(int i) {
        return 5;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Chaser();
    }
}
