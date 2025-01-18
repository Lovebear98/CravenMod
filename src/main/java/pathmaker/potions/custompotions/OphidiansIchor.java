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
import pathmaker.orbs.PowerFlower;

import static pathmaker.PathmakerMod.makeID;

public class OphidiansIchor extends CustomPotion {

    public static final String POTION_ID = makeID(OphidiansIchor.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public OphidiansIchor() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SNECKO, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.GOLDENROD.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[4], potionStrings.DESCRIPTIONS[5]));
    }

    public void initializeData() {
        this.potency = getPotency();

        String s = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];;
        if(potency != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        this.description = s;

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[4], potionStrings.DESCRIPTIONS[5]));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new ChannelAction(new PowerFlower()));
        addToBot(new DrawCardAction(potency));
    }

    @Override
    public int getPotency(int i) {
        return 2;
    }

    @Override
    public AbstractPotion makeCopy() {
        AbstractPotion p = new OphidiansIchor();
        return p;
    }
}
