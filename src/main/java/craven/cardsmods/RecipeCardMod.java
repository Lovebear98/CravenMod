package craven.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import craven.cards.power.RecipeCard;

import java.util.List;
import java.util.Objects;

import static craven.CravenMod.makeID;


public class RecipeCardMod extends AbstractCardModifier {
    private boolean Upgraded;

    public RecipeCardMod(boolean Upg){
        this.Upgraded = Upg;
    }

    public static final String ID = makeID(RecipeCardMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardMods"));

    public boolean shouldApply(AbstractCard targetCard) {
        ///We only add a mod if it doesn't already have the mod
        List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(targetCard, identifier(null));
        if(!sameMod.isEmpty()){
            if(this.Upgraded){
                ((RecipeCardMod)sameMod.get(0)).Upgraded = true;
            }
        }
        return sameMod.isEmpty();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        if(card instanceof RecipeCard){
            return true;
        }
        return super.isInherent(card);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(!(Objects.equals(card.cardID, RecipeCard.ID))){
            if(Upgraded){
                rawDescription = rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
            }else{
                rawDescription = rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
            }
        }
        return super.modifyDescription(rawDescription, card);
    }

    public void DevouredRecipe(){
        AbstractCard.CardRarity rarity;

        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 90) {
            rarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            rarity = AbstractCard.CardRarity.RARE;
        }
        AbstractCard c = CardLibrary.getAnyColorCard(AbstractCard.CardType.POWER, rarity);
        if(Upgraded){
            CardModifierManager.addModifier(c, new RecipeCardMod(this.Upgraded));
        }
        addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new RecipeCardMod(Upgraded);
    }
}