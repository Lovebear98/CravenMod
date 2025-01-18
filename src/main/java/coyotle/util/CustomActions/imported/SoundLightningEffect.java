package coyotle.util.CustomActions.imported;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

import static coyotle.util.otherutil.variables.Variables.VFXDuration;

public class SoundLightningEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private TextureAtlas.AtlasRegion img = null;

    public SoundLightningEffect(float x, float y) {
        if (this.img == null) {// 23
            this.img = ImageMaster.vfxAtlas.findRegion("combat/lightning");// 24
        }

        this.x = x - (float)this.img.packedWidth / 2.0F;// 27
        this.y = y;// 28
        this.color = Color.CYAN.cpy();// 29
        this.duration = VFXDuration();// 30
        this.startingDuration = VFXDuration();// 31
    }// 32

    public void update() {
        if (this.duration == this.startingDuration) {// 36
            ///The only change...
            CardCrawlGame.sound.play("ORB_LIGHTNING_PASSIVE", 0.6f);
            CardCrawlGame.screenShake.shake(ShakeIntensity.LOW, ShakeDur.MED, false);// 37

            for(int i = 0; i < 15; ++i) {// 38
                AbstractDungeon.topLevelEffectsQueue.add(new ImpactSparkEffect(this.x + MathUtils.random(-20.0F, 20.0F) * Settings.scale + 150.0F * Settings.scale, this.y + MathUtils.random(-20.0F, 20.0F) * Settings.scale));// 39 41 42
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();// 46
        if (this.duration < 0.0F) {// 47
            this.isDone = true;// 48
        }

        this.color.a = Interpolation.elasticOut.apply(this.duration * 2.0F);// 51
    }// 52

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);// 56
        sb.setColor(this.color);// 57
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 58
        sb.setBlendFunction(770, 771);// 59
    }// 61

    public void dispose() {
    }// 66
}