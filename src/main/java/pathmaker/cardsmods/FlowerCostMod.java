package pathmaker.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import pathmaker.orbs.AbstractFlowerOrb;

import java.util.List;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.variables.Variables.p;

public class FlowerCostMod extends AbstractCardModifier implements AlternateCardCostModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(FlowerCostMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));


    public FlowerCostMod(){}
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
            ///If they have orbs
            if(!p().orbs.isEmpty()){
                ///Check each orb
                for(AbstractOrb o: AbstractDungeon.player.orbs){
                    ///And if it's a flower orb
                    if(o instanceof AbstractFlowerOrb){
                        ///If its type matches the card's type
                        if(((AbstractFlowerOrb) o).type == abstractCard.type){
                            ///If the card is X-Cost
                            if(abstractCard.costForTurn == -1){
                                ///Return the player's current energy
                                return EnergyPanel.totalCount;
                            ///Otherwise
                            }else{
                                ///Return the card's current cost, PLUS any current energy we have (in case of
                                ///"spare energy" effects)
                                return EnergyPanel.totalCount + abstractCard.costForTurn;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int i) {
        ///If the card isn't free and doesn't cost 0 or -2
        if(!abstractCard.freeToPlay() && (abstractCard.costForTurn > 0 || abstractCard.costForTurn == -1)){
            ///Hold the card's type, since this can speed it up.
            AbstractCard.CardType TypeToHold = abstractCard.type;

            //And look through the player's orbs
            for(AbstractOrb o: AbstractDungeon.player.orbs){
                ///And if it's a flower orb
                if(o instanceof AbstractFlowerOrb){
                    ///If it's the right flower for our purposes
                    if(((AbstractFlowerOrb) o).type == TypeToHold){
                        ///Yeet
                        AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(o));
                        ///Fulfilled the cost, return 0 and stop;
                        return 0;
                    }
                }
            }
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
        return new FlowerCostMod();
    }
}
