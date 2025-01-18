//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coyotle.util.CustomActions.CustomGameEffects.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_X;
import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_Y;
import static coyotle.ui.ProphecyPanel.SizeCorrect;
import static coyotle.util.otherutil.ImageManager.ProphecyPulseFX;

public class ProphecyDeckPulse extends AbstractGameEffect {
    private float scale1;
    private float scale2;
    private float scale3;
    private float scale4;
    private float scale5;
    private final float targetscale1;
    private final float targetscale2;
    private final float targetscale3;
    private final float targetscale4;
    private final float targetscale5;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;
    private float x4;
    private float y4;
    private float x5;
    private float y5;
    private float aV1;
    private float aV2;
    private float aV3;
    private float aV4;
    private float aV5;
    private final float targetAlpha;
    private final boolean flipX1;
    private final boolean flipY1;
    private final boolean flipX2;
    private final boolean flipY2;
    private final boolean flipX3;
    private final boolean flipY3;
    private final boolean flipX4;
    private final boolean flipY4;
    private final boolean flipX5;
    private final boolean flipY5;
    protected float rotation1 = 0.0f;
    protected float rotation2 = 0.0f;
    protected float rotation3 = 0.0f;
    protected float rotation4 = 0.0f;
    protected float rotation5 = 0.0f;
    private Texture img;

    public ProphecyDeckPulse() {
        this.img = ProphecyPulseFX;

        this.renderBehind = false;

        this.color = new Color(MathUtils.random(200, 255)/255f, MathUtils.random(200, 255)/255f, MathUtils.random(200, 255)/255f, 0f);

        float sX = DRAW_PILE_X;
        float sY = DRAW_PILE_Y;

        float w = img.getWidth() * SizeCorrect;
        float h = img.getHeight() * SizeCorrect;

        this.x1 = sX + MathUtils.random(-w*0.75f, -w*0.25f);
        this.y1 = sY + MathUtils.random(-h*0.5f, -h*0.25f);
        this.x2 = sX + MathUtils.random(-w*0.75f, -w*0.25f);
        this.y2 = sY + MathUtils.random(-h*0.5f, -h*0.25f);
        this.x3 = sX + MathUtils.random(-w*0.75f, -w*0.25f);
        this.y3 = sY + MathUtils.random(-h*0.5f, -h*0.25f);
        this.x4 = sX + MathUtils.random(-w*0.75f, -w*0.25f);
        this.y4 = sY + MathUtils.random(-h*0.5f, -h*0.25f);
        this.x5 = sX + MathUtils.random(-w*0.75f, -w*0.25f);
        this.y5 = sY + MathUtils.random(-h*0.5f, -h*0.25f);

        this.aV1 = MathUtils.random(15f, 20f);
        this.aV2 = MathUtils.random(15f, 20f);
        this.aV3 = MathUtils.random(15f, 20f);
        this.aV4 = MathUtils.random(15f, 20f);
        this.aV5 = MathUtils.random(15f, 20f);

        this.scale1 = MathUtils.random (0.5f, 0.9f);
        this.scale2 = MathUtils.random (0.5f, 0.9f);
        this.scale3 = MathUtils.random (0.5f, 0.9f);
        this.scale4 = MathUtils.random (0.5f, 0.9f);
        this.scale5 = MathUtils.random (0.5f, 0.9f);


        this.targetscale1 = MathUtils.random(1.1f, 2f);
        this.targetscale2 = MathUtils.random(1.1f, 2f);
        this.targetscale3 = MathUtils.random(1.1f, 2f);
        this.targetscale4 = MathUtils.random(1.1f, 2f);
        this.targetscale5 = MathUtils.random(1.1f, 2f);

        this.targetAlpha = 0.75f;

        this.flipX1 = MathUtils.randomBoolean();
        this.flipY1 = MathUtils.randomBoolean();
        this.flipX2 = MathUtils.randomBoolean();
        this.flipY2 = MathUtils.randomBoolean();
        this.flipX3 = MathUtils.randomBoolean();
        this.flipY3 = MathUtils.randomBoolean();
        this.flipX4 = MathUtils.randomBoolean();
        this.flipY4 = MathUtils.randomBoolean();
        this.flipX5 = MathUtils.randomBoolean();
        this.flipY5 = MathUtils.randomBoolean();



        this.duration = this.startingDuration = 1f;
    }


    public void update() {
        this.rotation1 += this.aV1 * Gdx.graphics.getDeltaTime();
        this.rotation2 += this.aV1 * Gdx.graphics.getDeltaTime();
        this.rotation3 += this.aV1 * Gdx.graphics.getDeltaTime();
        this.rotation4 += this.aV1 * Gdx.graphics.getDeltaTime();
        this.rotation5 += this.aV1 * Gdx.graphics.getDeltaTime();

        this.scale1 = Interpolation.bounce.apply(this.scale1, this.targetscale1, this.startingDuration - this.duration);
        this.scale1 = Interpolation.bounce.apply(this.scale2, this.targetscale2, this.startingDuration - this.duration);
        this.scale1 = Interpolation.bounce.apply(this.scale3, this.targetscale3, this.startingDuration - this.duration);
        this.scale1 = Interpolation.bounce.apply(this.scale4, this.targetscale4, this.startingDuration - this.duration);
        this.scale1 = Interpolation.bounce.apply(this.scale5, this.targetscale5, this.startingDuration - this.duration);


        if (this.startingDuration - this.duration < (this.startingDuration * 0.5f)) {
            this.color.a = Interpolation.fade.apply(0.0F, this.targetAlpha, this.startingDuration - this.duration);
        } else if (this.duration < (this.startingDuration * 0.5f)) {
            this.color.a = Interpolation.fade.apply(this.targetAlpha, 0.0F, (this.startingDuration * 0.5f) - this.duration);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, x1, y1, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale1, this.scale1, rotation1, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX1, this.flipY1);
        sb.draw(this.img, x2, y2, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale2, this.scale2, rotation2, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX2, this.flipY2);
        sb.draw(this.img, x3, y3, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale3, this.scale3, rotation3, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX3, this.flipY3);
        sb.draw(this.img, x4, y4, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale4, this.scale4, rotation4, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX4, this.flipY4);
        sb.draw(this.img, x5, y5, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale5, this.scale5, rotation5, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX5, this.flipY5);

    }

    public void dispose() {
    }
}
