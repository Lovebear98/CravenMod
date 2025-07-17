package craven.potions.custompotions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import craven.powers.custompowers.RavenousPower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class BloodyMary extends CustomPotion {

    public static final String POTION_ID = makeID(BloodyMary.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public BloodyMary() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.HEART, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.WHITE.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[4], potionStrings.DESCRIPTIONS[5]));
    }

    public void initializeData() {
        this.potency = getPotency();

        this.description =
                potionStrings.DESCRIPTIONS[0] + potency +
                potionStrings.DESCRIPTIONS[1] + potency +
                potionStrings.DESCRIPTIONS[2] + potency +
                potionStrings.DESCRIPTIONS[3];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[4], potionStrings.DESCRIPTIONS[5]));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new HealAction(p(), p(), potency));
        addToBot(new GainEnergyAction(potency));
        addToBot(new ApplyPowerAction(p(), p(), new RavenousPower(p(), potency)));
    }

    @Override
    public int getPotency(int i) {
        return 6;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BloodyMary();
    }
}
