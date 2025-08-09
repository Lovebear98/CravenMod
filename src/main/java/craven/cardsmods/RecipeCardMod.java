package craven.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.Accuracy;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.stances.CalmStance;
import craven.cards.power.RecipeCard;
import craven.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.CravingCap;
import static craven.util.otherutil.variables.Variables.p;


public class RecipeCardMod extends AbstractCardModifier {

    public static final Texture RecipeCardIcon = TextureLoader.getTexture("craven/images/icons/RecipeCard.png");

    public static final ArrayList<String> bannedCards = new ArrayList<>();
    public static void initializebannedcards(){
        if(!bannedCards.isEmpty()){
            bannedCards.add(MentalFortress.ID);
            bannedCards.add(Rushdown.ID);
            bannedCards.add(Berserk.ID);
            bannedCards.add(Accuracy.ID);
            bannedCards.add(Evolve.ID);
            bannedCards.add(FireBreathing.ID);
            bannedCards.add(RecipeCard.ID);
        }
    }
    private boolean Upgraded;

    public RecipeCardMod(boolean Upg){
        this.Upgraded = Upg;
        initializebannedcards();
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

    public void DevouredRecipe(){
        AbstractCard.CardRarity rarity;

        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 90) {
            rarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            rarity = AbstractCard.CardRarity.RARE;
        }
        AbstractCard c;

        do{
            c = CardLibrary.getAnyColorCard(AbstractCard.CardType.POWER, rarity);
        }while(InvalidPower(c));
        if(Upgraded){
            CardModifierManager.addModifier(c, new RecipeCardMod(this.Upgraded));
        }
        addToBot(new MakeTempCardInHandAction(c, 1));
    }

    private boolean InvalidPower(AbstractCard c) {
        if(!bannedCards.contains(c.cardID)){
            if(c.cardID.equals(Capacitor.ID) || c.cardID.equals(Defragment.ID) || c.cardID.equals(Loop.ID) || c.cardID.equals(BiasedCognition.ID)){
                return HasOrb();
            }
            if(c.cardID.equals(LikeWater.ID)){
                return p().stance.ID.equals(CalmStance.STANCE_ID);
            }
            if(c.cardID.equals(Evolve.ID)){
                return HasStatus();
            }
            if(c.cardID.equals(FireBreathing.ID)){
                return (HasStatus() || HasCurse());
            }
        }
        return false;
    }

    private boolean HasStatus(){
        for(AbstractCard d: p().hand.group){
            if(d.type == AbstractCard.CardType.STATUS){
                return true;
            }
        }
        for(AbstractCard d: p().drawPile.group){
            if(d.type == AbstractCard.CardType.STATUS){
                return true;
            }
        }
        for(AbstractCard d: p().discardPile.group){
            if(d.type == AbstractCard.CardType.STATUS){
                return true;
            }
        }
        return false;
    }

    private boolean HasCurse(){
        for(AbstractCard d: p().hand.group){
            if(d.type == AbstractCard.CardType.CURSE){
                return true;
            }
        }
        for(AbstractCard d: p().drawPile.group){
            if(d.type == AbstractCard.CardType.CURSE){
                return true;
            }
        }
        for(AbstractCard d: p().discardPile.group){
            if(d.type == AbstractCard.CardType.CURSE){
                return true;
            }
        }
        return false;
    }

    private boolean HasOrb(){
        for(AbstractOrb o: p().orbs){
            if(Objects.equals(o.ID, EmptyOrbSlot.ORB_ID)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new RecipeCardMod(Upgraded);
    }



    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        super.onRender(card, sb);
            RenderIcon(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        super.onSingleCardViewRender(card, sb);
            RenderIcon(card);
    }

    private void RenderIcon(AbstractCard card) {
        ExtraIcons.icon(RecipeCardIcon).render(card);
    }
}