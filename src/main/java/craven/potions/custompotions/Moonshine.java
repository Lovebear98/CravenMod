package craven.potions.custompotions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static craven.CravenMod.makeID;

public class Moonshine extends CustomPotion {

    public static final String POTION_ID = makeID(Moonshine.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public Moonshine() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.MOON, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.WHITE.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void initializeData() {
        this.potency = getPotency();

        String s = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        if(potency != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        this.description = s;

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new BetterDrawPileToHandAction(potency));
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Moonshine();
    }
}
