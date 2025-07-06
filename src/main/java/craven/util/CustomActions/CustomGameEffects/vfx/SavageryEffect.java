package craven.util.CustomActions.CustomGameEffects.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static craven.character.RiskGauge.SizeCorrect;
import static craven.util.otherutil.ImageManager.Scratch;
import static craven.util.otherutil.variables.Variables.RandomBoolean;

public class SavageryEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private boolean flipX;
    private boolean flipY;
    private float targetAlpha;
    private Texture img;
    private static final float FadeTime = 0.05f;

    public SavageryEffect(AbstractMonster target) {
        this.img = Scratch;

        this.renderBehind = false;

        this.color = Color.WHITE.cpy();
        this.color.a = 0f;

        this.x = target.hb.x;
        this.y = target.hb.y;

        this.scale = 0.75f;

        this.flipX = RandomBoolean();
        this.flipY = RandomBoolean();
        this.rotation = MathUtils.random();

        this.targetAlpha = 1f;
        this.duration = this.startingDuration = 0.15f;
    }


    public void update() {
        if(this.duration == this.startingDuration){
            CardCrawlGame.sound.playA("ATTACK_IRON_1", 0.0f);
        }

        if ((this.startingDuration - this.duration) < FadeTime) {
            this.color.a = Interpolation.fade.apply(0.0F, this.targetAlpha, this.startingDuration - this.duration);
        } else if (this.duration < FadeTime) {
            this.color.a = Interpolation.fade.apply(this.targetAlpha, 0.0F, FadeTime - this.duration);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, x, y, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale, this.scale, this.rotation, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX, this.flipY);

    }

    public void dispose() {
    }
}
