package craven.potions.custompotions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import craven.cardsmods.BonusCravingMod;
import craven.util.CustomActions.ApplyCardModAction;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.UIText.BoostText;

public class HighProofSpirit extends CustomPotion {

    public static final String POTION_ID = makeID(HighProofSpirit.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public HighProofSpirit() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.WHITE.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void initializeData() {
        this.potency = getPotency();

        this.description =
                potionStrings.DESCRIPTIONS[0] + potency +
                potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new SelectCardsInHandAction(1, BoostText(), false, false, card -> card.cost == -1 || card.costForTurn == -1, selectedcards -> {
            for(AbstractCard c: selectedcards){
                addToTop(new ApplyCardModAction(c, new BonusCravingMod(potency)));
            }
        }));
    }

    @Override
    public int getPotency(int i) {
        return 3;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HighProofSpirit();
    }
}
