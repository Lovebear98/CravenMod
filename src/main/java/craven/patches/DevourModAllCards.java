package craven.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import craven.cardsmods.DevourCostMod;

///This patch puts the "DevourCostMod" on ALL cards so that Atlas - and any characters opting into his
///energy system - are able to Devour to pay their costs naturally.
public class DevourModAllCards {
    @SpirePatch(clz = AbstractCard.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez =  {
            String.class, String.class, String.class,
                    int.class, String.class, AbstractCard.CardType.class,
            AbstractCard.CardColor.class, AbstractCard.CardRarity.class, AbstractCard.CardTarget.class,
                    DamageInfo.DamageType.class})
    public static class constructor {
        public static void Postfix(AbstractCard __instance) {
            CardModifierManager.addModifier(__instance, new DevourCostMod());
        }
    }
}