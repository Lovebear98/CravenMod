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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static coyotle.ui.ProphecyPanel.SizeCorrect;
import static coyotle.util.otherutil.ImageManager.DreamSmoke;

public class DreamSmokeEffect extends AbstractGameEffect {
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
    private float targetAlpha;
    private boolean flipX1;
    private boolean flipY1;
    private boolean flipX2;
    private boolean flipY2;
    private boolean flipX3;
    private boolean flipY3;
    private boolean flipX4;
    private boolean flipY4;
    private boolean flipX5;
    private boolean flipY5;
    protected float rotation1 = 0.0f;
    protected float rotation2 = 0.0f;
    protected float rotation3 = 0.0f;
    protected float rotation4 = 0.0f;
    protected float rotation5 = 0.0f;
    private Texture img;

    public DreamSmokeEffect() {
        this.img = DreamSmoke;

        this.renderBehind = true;

        this.color = new Color(MathUtils.random(100, 200)/255f, MathUtils.random(100, 200)/255f, MathUtils.random(100, 200)/255f, 0f);

        this.x1 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y1 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);
        this.x2 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y2 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);
        this.x3 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y3 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);
        this.x4 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y4 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);
        this.x5 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y5 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);

        this.aV1 = MathUtils.random(15f, 20f);
        this.aV2 = MathUtils.random(15f, 20f);
        this.aV3 = MathUtils.random(15f, 20f);
        this.aV4 = MathUtils.random(15f, 20f);
        this.aV5 = MathUtils.random(15f, 20f);

        this.targetAlpha = 0.25f;

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



        this.duration = this.startingDuration = 2f;
    }


    public void update() {
        this.rotation1 += this.aV1 * Gdx.graphics.getDeltaTime();
        this.rotation2 += this.aV2 * Gdx.graphics.getDeltaTime();
        this.rotation3 += this.aV3 * Gdx.graphics.getDeltaTime();
        this.rotation4 += this.aV4 * Gdx.graphics.getDeltaTime();
        this.rotation5 += this.aV5 * Gdx.graphics.getDeltaTime();
        if (this.startingDuration - this.duration < 1.0F) {
            this.color.a = Interpolation.fade.apply(0.0F, this.targetAlpha, this.startingDuration - this.duration);
        } else if (this.duration < 1.0F) {
            this.color.a = Interpolation.fade.apply(this.targetAlpha, 0.0F, 1.0F - this.duration);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, x1, y1, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX1, this.flipY1);
        sb.draw(this.img, x2, y2, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, 1f, 1f, rotation2, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX2, this.flipY2);
        sb.draw(this.img, x3, y3, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, 1f, 1f, rotation3, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX3, this.flipY3);
        sb.draw(this.img, x4, y4, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, 1f, 1f, rotation4, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX4, this.flipY4);
        sb.draw(this.img, x5, y5, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, 1f, 1f, rotation5, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipX5, this.flipY5);

    }

    public void dispose() {
    }
}
