package coyotle.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import coyotle.cardsmods.WindsingerMod;
import coyotle.relics.MasteroftheHunt;

import static coyotle.util.otherutil.Wiz.isStrikeOrDefend;
import static coyotle.util.otherutil.variables.Variables.p;

public class WindsingerPatch {

    @SpirePatch(clz = AbstractCard.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez =  {
            String.class, String.class, String.class,
                    int.class, String.class, AbstractCard.CardType.class,
            AbstractCard.CardColor.class, AbstractCard.CardRarity.class, AbstractCard.CardTarget.class,
                    DamageInfo.DamageType.class})
    public static class constructor {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance) {
            ///if the player exists and has the windsinger relic, the cards should come pre-modified
            if(p() != null){
                if(p().hasRelic(MasteroftheHunt.ID)){

                    if(isStrikeOrDefend(__instance)){
                        CardModifierManager.addModifier(__instance, new WindsingerMod());
                    }
                }
            }
        }
    }
}