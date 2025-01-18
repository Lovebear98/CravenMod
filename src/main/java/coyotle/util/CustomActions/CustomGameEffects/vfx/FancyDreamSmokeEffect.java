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
import static coyotle.util.otherutil.ImageManager.*;

public class FancyDreamSmokeEffect extends AbstractGameEffect {
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;
    private float aV1;
    private float aV2;
    private float aV3;
    private float targetAlpha;
    private boolean flipX1;
    private boolean flipY1;
    private boolean flipX2;
    private boolean flipY2;
    private boolean flipX3;
    private boolean flipY3;

    private float rotation1 = 0.0F;
    private float rotation2 = 0.0F;
    private float rotation3 = 0.0F;

    private Texture img1;
    private Texture img2;
    private Texture img3;

    public FancyDreamSmokeEffect() {
        this.img1 = RandomDreamImage();
        this.img2 = RandomDreamImage();
        this.img3 = RandomDreamImage();


        this.renderBehind = true;

        this.color = new Color(MathUtils.random(100, 200)/255f, MathUtils.random(100, 200)/255f, MathUtils.random(100, 200)/255f, 0f);

        this.x1 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y1 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);

        this.x2 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y2 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);

        this.x3 = MathUtils.random((0-Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y3 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);

        this.aV1 = MathUtils.random(15f, 20f);
        this.aV2 = MathUtils.random(15f, 20f);
        this.aV3 = MathUtils.random(15f, 20f);

        this.targetAlpha = 0.25f;

        this.flipX1 = MathUtils.randomBoolean();
        this.flipY1 = MathUtils.randomBoolean();
        this.flipX2 = MathUtils.randomBoolean();
        this.flipY2 = MathUtils.randomBoolean();
        this.flipX3 = MathUtils.randomBoolean();
        this.flipY3 = MathUtils.randomBoolean();



        this.duration = this.startingDuration = 2f;
    }


    public void update() {
        this.rotation1 += this.aV1 * Gdx.graphics.getDeltaTime();
        this.rotation2 += this.aV2 * Gdx.graphics.getDeltaTime();
        this.rotation3 += this.aV3 * Gdx.graphics.getDeltaTime();
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
        sb.draw(this.img1, x1, y1, (this.img1.getWidth() * SizeCorrect * 0.5f), (this.img1.getHeight() * SizeCorrect * 0.5f), this.img1.getWidth()*SizeCorrect, this.img1.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, this.img1.getWidth(), this.img1.getHeight(), this.flipX1, this.flipY1);
        sb.draw(this.img2, x2, y2, (this.img1.getWidth() * SizeCorrect * 0.5f), (this.img1.getHeight() * SizeCorrect * 0.5f), this.img1.getWidth()*SizeCorrect, this.img1.getHeight()*SizeCorrect, 1f, 1f, rotation2, 0, 0, this.img1.getWidth(), this.img1.getHeight(), this.flipX2, this.flipY2);
        sb.draw(this.img3, x3, y3, (this.img1.getWidth() * SizeCorrect * 0.5f), (this.img1.getHeight() * SizeCorrect * 0.5f), this.img1.getWidth()*SizeCorrect, this.img1.getHeight()*SizeCorrect, 1f, 1f, rotation3, 0, 0, this.img1.getWidth(), this.img1.getHeight(), this.flipX3, this.flipY3);
    }

    public void dispose() {
    }


    private Texture RandomDreamImage() {
        int i = MathUtils.random(1, 6);
        switch(i){
            case 1:
                return DreamEye;
            case 2:
                return DreamHorse;
            case 3:
                return DreamTangle;
            case 4:
                return DreamInfinity;
            case 5:
                return DreamBird;
            default:
                return DreamCoyotle;
        }
    }

}
