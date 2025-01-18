package pathmaker.potions.custompotions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import pathmaker.orbs.AttackFlower;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.BloomManager.*;

public class BlessedWater extends CustomPotion {

    public static final String POTION_ID = makeID(BlessedWater.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public BlessedWater() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.GOLDENROD.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
    }

    public void initializeData() {
        this.potency = getPotency();

        this.description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        int i = BloomCap() - BloomCount;
        int e = potency - i;

        Growth(i);

        addToBot(new DrawCardAction(e));
    }

    @Override
    public int getPotency(int i) {
        return 5;
    }

    @Override
    public AbstractPotion makeCopy() {
        AbstractPotion p = new BlessedWater();
        return p;
    }
}
