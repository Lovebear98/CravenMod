package craven.util.otherutil;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import craven.patches.interfaces.CravingInterface;
import craven.patches.interfaces.PostDevourInterface;
import craven.powers.custompowers.CravingPower;
import craven.powers.custompowers.RiskCapPower;

import static craven.util.otherutil.variables.UIText.CantDrawText;
import static craven.util.otherutil.variables.Variables.isInCombat;
import static craven.util.otherutil.variables.Variables.p;

public class MechanicManager {
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

                                ///If we want to set unique card effects that can target other cards up
                        }///else if(abstractCard.hasTag(ChangeCraving)){
                               /// i += getExtraDevourCap(abstractCard);
                        ///}
                }
                return Math.max(i, 0);
        }

        /** Manager for the RISK mechanic */
        public static int Risk = 0;
        public static int RiskCap = 6;
        public static int TrueRiskCap(){
                int i = RiskCap;
                if(p() != null){
                        if(p().hasPower(RiskCapPower.POWER_ID)){
                                i += p().getPower(RiskCapPower.POWER_ID).amount;
                        }
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
                Risk += i;
                CheckRisk();
        }
        public static void CheckRisk() {
                if(Risk >= TrueRiskCap()){
                        Risk = TrueRiskCap();
                        ///AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p(), p(), new BlendInPower(p())));
                        ///return;
                }
                if(Risk < 0){
                        Risk = 0;
                }
                ///if (p().hasPower(BlendInPower.POWER_ID)) {
                ///        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p(), p(), BlendInPower.POWER_ID));
                ///}
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
                boolean MakeEffect = true;
                for(AbstractGameAction action : AbstractDungeon.actionManager.actions){
                        if (action instanceof TalkAction) {
                                MakeEffect = false;
                                break;
                        }
                }

                if(MakeEffect){
                        AbstractDungeon.actionManager.addToTop(new TalkAction(true, CantDrawText(), 1.5f, 1.5f));
                }
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



        ///For some reason this has to be separate
        @SpireEnum
        public static AbstractPotion.PotionRarity SPECIAL;
}
