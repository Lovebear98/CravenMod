package craven.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import craven.util.TextureLoader;

import java.util.List;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.CravingCap;
import static craven.util.otherutil.Wiz.*;
import static craven.util.otherutil.variables.Variables.p;

public class DevourCostMod extends AbstractCardModifier implements AlternateCardCostModifier {

    public static final Texture FangsIcon = TextureLoader.getTexture("craven/images/icons/XCost.png");

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(DevourCostMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));


    public DevourCostMod(){}
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
        ///If the player is valid to devour
        if (DevourEnabled()) {
            ///If it's an x cost
            if(abstractCard.costForTurn == -1){
                ///Return the X Cost Cap
                return CravingCap(abstractCard);
            }
            ///Otherwise, return infinite energy
            return 999;
        }
        ///Otherwise return 0 and pretend we're not here
        return 0;
    }

    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int i) {
        ///If the card isn't free and doesn't cost 0 or -2
        if(DevourEnabled() && !abstractCard.freeToPlay() && (abstractCard.costForTurn > 0 || abstractCard.costForTurn == -1)){
            ///Get the number of cards in our exhaust pile or the cost, whichever is lower
            int Cards = Math.min(i, p().exhaustPile.size());
            ///Get the leftovers if we don't have enough cards
            int Leftovers = i - Cards;

            ///If we have any cards to Devour
            if(Cards > 0){
                ///Devour them
                DevourCards(Cards);
            }

            ///If we have leftovers
            if(Leftovers > 0){
                ///Apply Ravenous for the difference.
                GainRavenous(Leftovers);
            }

            ///And return what's left afterwards, which SHOULD be 0, but may somehow not be
            return (i - Cards - Leftovers);
        }
        ///We didn't spend an alternate cost, spend energy normally
        return i;
    }

    ///We CANNOT split this
    @Override
    public boolean canSplitCost(AbstractCard card) {
        return !DevourEnabled();
    }

    ///And we prioritize Devouring at all times
    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return DevourEnabled();
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        super.onRender(card, sb);
        if(card.costForTurn == -1){
            RenderIcon(card);
        }
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        super.onSingleCardViewRender(card, sb);
        if(card.costForTurn == -1){
            RenderIcon(card);
        }
    }

    private void RenderIcon(AbstractCard card) {
        if(DevourEnabled()){
            ExtraIcons.icon(FangsIcon).text(String.valueOf(CravingCap(card))).render(card);
        }
    }











    @Override
    public AbstractCardModifier makeCopy() {
        return new DevourCostMod();
    }
}
