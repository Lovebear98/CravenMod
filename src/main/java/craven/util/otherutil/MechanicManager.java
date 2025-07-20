package craven.util.otherutil;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import craven.cardsmods.BonusCravingMod;
import craven.cardsmods.RecipeCardMod;
import craven.patches.interfaces.CravingInterface;
import craven.patches.interfaces.OnDevouredInterface;
import craven.patches.interfaces.PostDevourInterface;
import craven.patches.interfaces.SecondsInterface;
import craven.powers.custompowers.CravingPower;
import craven.powers.custompowers.MealPrepPower;
import craven.powers.custompowers.PalateCleanserPower;
import craven.powers.custompowers.RiskCapPower;
import craven.relics.FoundFood;
import craven.relics.Ribbon;
import craven.util.CustomActions.imported.PhantomPlayCardAction;

import java.util.List;

import static craven.patches.DracoTailField.inDracoTailField;
import static craven.util.otherutil.ConfigManager.ScavengeCount;
import static craven.util.otherutil.variables.UIText.CantDrawText;
import static craven.util.otherutil.variables.Variables.isInCombat;
import static craven.util.otherutil.variables.Variables.p;

public class MechanicManager {




        public static int ScavengeCount() {
                int i = ScavengeCount;
                if(p() != null && p().hasRelic(FoundFood.ID)){
                        FoundFood r = (FoundFood) p().getRelic(FoundFood.ID);
                        i += r.counter;
                }
                return i;
        }


        public static final int BaseCravingCap = 3;
        public static int CravingCap(AbstractCard abstractCard){
                int i;
                if(p() != null){
                        i = p().energy.energyMaster;
                }else{
                        i = BaseCravingCap;
                }
                if(p() != null){
                        if(p().hasPower(CravingPower.POWER_ID)){
                                i += p().getPower(CravingPower.POWER_ID).amount;
                        }
                }
                if(abstractCard != null){
                        if(abstractCard instanceof CravingInterface){
                                i += ((CravingInterface) abstractCard).CravingBonus();
                        }
                        if(CardModifierManager.hasModifier(abstractCard, BonusCravingMod.ID)){
                                List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(abstractCard, BonusCravingMod.ID);
                                BonusCravingMod mod = (BonusCravingMod) sameMod.get(0);
                                i += mod.bonus;

                        }
                }
                return Math.max(i, 0);
        }

        /** Manager for the RISK mechanic */
        public static int Risk = 0;
        public static int BaseRiskCap = 6;
        public static int TrueRiskCap(){
                int i = BaseRiskCap;
                if(p() != null){
                        if(p().hasPower(RiskCapPower.POWER_ID)){
                                i += p().getPower(RiskCapPower.POWER_ID).amount;
                        }
                        if(p().hasRelic(Ribbon.ID)){
                                i += Ribbon.RibbonRisKAmount;
                        }
                }
                if(i < 0){
                        i = 0;
                }
                return i;
        }
        public static void ResetRisk(){
                Risk = 0;
                if(isInCombat()){
                        AdjustRisk(0);
                }
        }
        public static void AdjustRisk(){
                AdjustRisk(1);
        }
        public static void AdjustRisk(int i){
                boolean NewlyCapped = false;
                boolean StartedBelowCap = Risk < TrueRiskCap();
                if(i < 0 && p() != null){
                        if(p().hasPower(PalateCleanserPower.POWER_ID)){
                                AbstractPower p = p().getPower(PalateCleanserPower.POWER_ID);
                                p.flash();
                                CheckRisk();
                                return;
                        }
                }
                Risk += i;
                if(Risk >= TrueRiskCap() && StartedBelowCap){
                        NewlyCapped = true;
                }
                CheckRisk(NewlyCapped);
        }
        public static void CheckRisk() {
                CheckRisk(false);
        }
        public static void CheckRisk(boolean NewlyCapped) {
                if(Risk >= TrueRiskCap()){
                        Risk = TrueRiskCap();
                }
                if(Risk < 0){
                        Risk = 0;
                }
                if(NewlyCapped){
                        if(p().hasPower(MealPrepPower.POWER_ID)){
                                MealPrepPower pow = (MealPrepPower) p().getPower(MealPrepPower.POWER_ID);
                                pow.Trigger();
                        }
                }
        }

        public static int RemainingDraws(){
                return Math.max(0, TrueRiskCap() - Risk);
        }

        public static int RiskLevel(){
                if(Risk >= TrueRiskCap()){
                        return 4;
                }
                if(Risk >= (TrueRiskCap() * 0.75f)){
                        return 3;
                }
                if(Risk >= (TrueRiskCap() * 0.5f)){
                        return 2;
                }
                if(Risk >= (TrueRiskCap() * 0.25f)){
                        return 1;
                }
                return 0;
        }
        public static void AttemptNoDrawText(){
                for(AbstractGameAction action : AbstractDungeon.actionManager.actions){
                        if (action instanceof TalkAction) {
                                return;
                        }
                }

            AbstractDungeon.actionManager.addToTop(new TalkAction(true, CantDrawText(), 1.5f, 1.5f));
        }

        public static void DevourCallback(int i){
                for(AbstractCard c: p().hand.group){
                        if(c instanceof PostDevourInterface){
                                ((PostDevourInterface) c).PostDevour(i);
                        }
                }
                for(AbstractCard c: p().drawPile.group){
                        if(c instanceof PostDevourInterface){
                                ((PostDevourInterface) c).PostDevour(i);
                        }
                }
                for(AbstractCard c: p().discardPile.group){
                        if(c instanceof PostDevourInterface){
                                ((PostDevourInterface) c).PostDevour(i);
                        }
                }

                for(AbstractPower p: p().powers){
                        if(p instanceof PostDevourInterface){
                                ((PostDevourInterface) p).PostDevour(i);
                        }
                }

                for(AbstractRelic r: p().relics){
                        if(r instanceof PostDevourInterface){
                                ((PostDevourInterface) r).PostDevour(i);
                        }
                }
        }


        public static void DevourInternal(AbstractCard c){
                DevourInternal(c, p().exhaustPile);
        }

        public static void DevourInternal(AbstractCard c, CardGroup pile) {
                pile.removeCard(c);
                if(c instanceof SecondsInterface){
                        if(((SecondsInterface) c).SecondsCount() > 0){
                                ((SecondsInterface) c).OnSecondsTrigger();
                                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeSameInstanceOf()));
                        }
                }
                if(c instanceof OnDevouredInterface){
                        ((OnDevouredInterface) c).PostDevoured();
                }
                if(CardModifierManager.hasModifier(c, RecipeCardMod.ID)){
                        List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(c, RecipeCardMod.ID);
                        if(!sameMod.isEmpty()){
                                RecipeCardMod mod = (RecipeCardMod) sameMod.get(0);
                                mod.DevouredRecipe();
                        }
                }
                if (inDracoTailField.get(c)) {
                        AbstractMonster m = AbstractDungeon.getRandomMonster();
                        AbstractDungeon.actionManager.addToBottom(new PhantomPlayCardAction(c.makeSameInstanceOf(), m));
                }
        }


        ///For some reason this has to be separate
        @SpireEnum
        public static AbstractPotion.PotionRarity CURATED;
}
