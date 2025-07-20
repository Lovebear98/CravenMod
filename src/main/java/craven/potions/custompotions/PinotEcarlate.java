package craven.potions.custompotions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.custompowers.RavenousPower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class PinotEcarlate extends CustomPotion {

    public static final String POTION_ID = makeID(PinotEcarlate.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public PinotEcarlate() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.WHITE.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
    }

    public void initializeData() {
        this.potency = getPotency();

        this.description =
                potionStrings.DESCRIPTIONS[0] + potency +
                potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        if(p() != null){
            if(p().hasPower(RavenousPower.POWER_ID)){
                AbstractPower p = p().getPower(RavenousPower.POWER_ID);
                addToBot(new ReducePowerAction(p(), p(), p, potency));
            }else{
                addToBot(new ApplyPowerAction(p(), p(), new RavenousPower(p(), potency)));
            }
        }
    }

    @Override
    public int getPotency(int i) {
        return 7;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PinotEcarlate();
    }
}
