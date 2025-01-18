package pathmaker.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import pathmaker.cardsmods.FlowerCostMod;

public class FlowerModAllCardsPatch {
    @SpirePatch(clz = AbstractCard.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez =  {
            String.class, String.class, String.class,
                    int.class, String.class, AbstractCard.CardType.class,
            AbstractCard.CardColor.class, AbstractCard.CardRarity.class, AbstractCard.CardTarget.class,
                    DamageInfo.DamageType.class})
    public static class constructor {
        public static void Postfix(AbstractCard __instance) {
            CardModifierManager.addModifier(__instance, new FlowerCostMod());
        }
    }
}