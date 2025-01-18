package coyotle.patches;


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
import coyotle.CoyotleMod;
import coyotle.util.MyAttackEffects;
import coyotle.util.TextureLoader;

import static coyotle.util.otherutil.SoundManager.GALAXYATTACKKEY;
import static coyotle.util.otherutil.SoundManager.SMOKEATTACKKEY;

///Creating his custom "Glitch Screen" attack effect
public class AttackEffectsPatch {

    private static Texture smokeTex = TextureLoader.getTexture("coyotle/images/vfx/SmokeAttack.png");
    private static Texture galaxyTex = TextureLoader.getTexture("coyotle/images/vfx/GalaxyAttack.png");


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
            ///Try this whole brick for each unique effect to play it if we ask for it
            try {
                if (___effect == MyAttackEffects.Smoke) {
                    ///Set rotation
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", MathUtils.random(-120f, 120f));
                    ///Tell it what to show
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(smokeTex, 0, 0, smokeTex.getWidth(), smokeTex.getHeight()));
                }
            }
            ///Log an error if it's missing
            catch (Exception e)
            {
                CoyotleMod.logger.error("Failed to get texture in " + __instance.getClass().getName());
                e.printStackTrace();
            }

            try {
                if (___effect == MyAttackEffects.Galaxy) {
                    ///Set rotation
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", MathUtils.random(-120f, 120f));
                    ///Tell it what to show
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(galaxyTex, 0, 0, galaxyTex.getWidth(), galaxyTex.getHeight()));
                }
            }
            ///Log an error if it's missing
            catch (Exception e)
            {
                CoyotleMod.logger.error("Failed to get texture in " + __instance.getClass().getName());
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
            ///And play the right sound
            if (effect == MyAttackEffects.Smoke) {
                CardCrawlGame.sound.play(SMOKEATTACKKEY, 0.6f);
            }
            if (effect == MyAttackEffects.Galaxy) {
                CardCrawlGame.sound.play(GALAXYATTACKKEY, 0.2f);
            }
            return SpireReturn.Continue();
        }
    }
}