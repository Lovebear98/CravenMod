package craven.patches.visual;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.DamageHeartEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import craven.CravenMod;
import craven.util.TextureLoader;

///Creating his custom "Glitch Screen" attack effect
public class AttackEffectPatch {

    private static Texture wineTex = TextureLoader.getTexture("craven/images/vfx/SlayPoisonRed.png");
    private static Texture gildTex = TextureLoader.getTexture("craven/images/vfx/GildedCut.png");
    private static Texture shadyTex = TextureLoader.getTexture("craven/images/vfx/Shady.png");


    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "loadImage"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "loadImage"
    )
    public static class loadImagePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<TextureAtlas.AtlasRegion> tryLoadImage(AbstractGameEffect __instance, AbstractGameAction.AttackEffect ___effect)
        {
            CravenMod.logger.info("WE HAVE RECIEVED THIS EFFECT: ["+___effect+"]");
            ///Try this whole brick for each unique effect to play it if we ask for it
            try {
                if (___effect == AttackEffectEnum.WINE) {
                    ///Set rotation
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", MathUtils.random(-120f, 120f));
                    ///Tell it what to show
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(wineTex, 0, 0, wineTex.getWidth(), wineTex.getHeight()));
                }
            }
            ///Log an error if it's missing
            catch (Exception e)
            {
                CravenMod.logger.error("Failed to get texture in " + __instance.getClass().getName());
                e.printStackTrace();
            }

            try {
                if (___effect == AttackEffectEnum.GILDED) {
                    ///Set rotation
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", MathUtils.random(-120f, 120f));
                    ///Tell it what to show
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(gildTex, 0, 0, wineTex.getWidth(), wineTex.getHeight()));
                }
            }
            ///Log an error if it's missing
            catch (Exception e)
            {
                CravenMod.logger.error("Failed to get texture in " + __instance.getClass().getName());
                e.printStackTrace();
            }

            try {
                if (___effect == AttackEffectEnum.SHADY) {
                    ///Set rotation
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", MathUtils.random(0));
                    ///Tell it what to show
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(shadyTex, 0, 0, wineTex.getWidth(), wineTex.getHeight()));
                }
            }
            ///Log an error if it's missing
            catch (Exception e)
            {
                CravenMod.logger.error("Failed to get texture in " + __instance.getClass().getName());
                e.printStackTrace();
            }

            return SpireReturn.Continue();
        }
        ///
    }

    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "playSound"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "playSound"
    )
    public static class playSoundPatch
    {
        @SpirePrefixPatch
        public static SpireReturn tryPlaySound(AbstractGameEffect __instance, AbstractGameAction.AttackEffect effect)
        {
            if(effect == AttackEffectEnum.WINE){
                CardCrawlGame.sound.play("ATTACK_POISON", 0.15f);
            }
            if(effect == AttackEffectEnum.GILDED){
                CardCrawlGame.sound.play("ATTACK_FAST", 0.15f);
            }
            if(effect == AttackEffectEnum.SHADY){
                CardCrawlGame.sound.play("ORB_EVOKE_DARK", 0.15f);
            }
            return SpireReturn.Continue();

        }
    }
}