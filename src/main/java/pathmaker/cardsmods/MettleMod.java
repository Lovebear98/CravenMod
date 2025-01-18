package pathmaker.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.List;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.variables.Variables.p;

public class MettleMod extends AbstractCardModifier implements AlternateCardCostModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(MettleMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));


    public MettleMod(){
    }
    ///Name our cardmod
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    ///It's innateo n whatever it's applied to, because it's applied on EVERYTHING
    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }
    ///When applied, make sure we don't double up.
    @Override
    public boolean shouldApply(AbstractCard targetCard) {
        ///We only add a mod if it doesn't already have the mod
        List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(targetCard, identifier(null));
        return sameMod.isEmpty();
    }






    @Override
    public int getAlternateResource(AbstractCard abstractCard) {
        ///If the player exists
        if (p() != null) {
            ///And there are cards in their hand
            if(!p().hand.isEmpty()){
                ///Return the hand size MINUS ONE to exclude the card in question
                return p().hand.size()-1;
            }
        }
        return 0;
    }

    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int i) {
        ///If the card isn't free and doesn't cost 0 or -2
        if(!abstractCard.freeToPlay() && (abstractCard.costForTurn > 0 || abstractCard.costForTurn == -1)){
            ///Discard cards equal to its cost
            AbstractDungeon.actionManager.addToTop(new DiscardAction(p(), p(), i, false));
            return 0;
        }
        ///Somehow we didn't spend an alternate cost, spend energy normally
        return i;
    }

    @Override
    public Color getGlow(AbstractCard card) {

        return super.getGlow(card);
    }

    ///We CANNOT split this
    @Override
    public boolean canSplitCost(AbstractCard card) {
        return false;
    }

    ///And we prioritize the Flower
    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return true;
    }


















    @Override
    public AbstractCardModifier makeCopy() {
        return new MettleMod();
    }
}
