package pathmaker.patches.visual;

import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.rewards.chests.*;
import pathmaker.cardsmods.FlowerCostMod;
import pathmaker.util.TextureLoader;

import static pathmaker.util.otherutil.ConfigManager.EnableAltUI;

public class SkinChestsPatch {
    @SpirePatch(clz = SmallChest.class,
            method = SpirePatch.CONSTRUCTOR)
    public static class smallchest {

        public static void Postfix(SmallChest __instance) {
            if(__instance != null && EnableAltUI){
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "img", Small);
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "openedImg", SmallOpen);
            }
        }
    }

    @SpirePatch(clz = MediumChest.class,
            method = SpirePatch.CONSTRUCTOR)
    public static class midchest {
        public static void Postfix(MediumChest __instance) {
            if(__instance != null && EnableAltUI){
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "img", Medium);
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "openedImg", MediumOpen);
            }
        }
    }

    @SpirePatch(clz = LargeChest.class,
            method = SpirePatch.CONSTRUCTOR)
    public static class bigchest {
        public static void Postfix(LargeChest __instance) {
            if(__instance != null && EnableAltUI){
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "img", Large);
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "openedImg", LargeOpen);
            }
        }
    }

    @SpirePatch(clz = BossChest.class,
            method = SpirePatch.CONSTRUCTOR)
    public static class bosschest {
        public static void Postfix(BossChest __instance) {
            if(__instance != null && EnableAltUI){
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "img", Boss);
                ReflectionHacks.setPrivate(__instance, AbstractChest.class, "openedImg", BossOpen);
            }
        }
    }



    private static final Texture Small = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/SmallChest.png");
    private static final Texture SmallOpen = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/SmallChestOpen.png");

    private static final Texture Medium = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/MediumChest.png");
    private static final Texture MediumOpen = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/MediumChestOpen.png");

    private static final Texture Large = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/BigChest.png");
    private static final Texture LargeOpen = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/BigChestOpen.png");

    private static final Texture Boss = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/BossChest.png");
    private static final Texture BossOpen = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/BossChestOpen.png");
}