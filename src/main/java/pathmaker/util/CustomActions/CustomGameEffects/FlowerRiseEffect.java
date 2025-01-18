package pathmaker.util.CustomActions.CustomGameEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import pathmaker.util.TextureLoader;

public class FlowerRiseEffect extends AbstractGameEffect {
    private Texture img;
    private Texture img2;

    private static final Texture FlowerTex = TextureLoader.getTexture("pathmaker/images/vfx/FlowerBack.png");
    private static final Texture StamenTex = TextureLoader.getTexture("pathmaker/images/vfx/FlowerFront.png");

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float vY2;
    private float vS;
    private float startingDuration;
    private boolean flipX = MathUtils.randomBoolean();
    private float delayTimer = MathUtils.random(0.15F);


    public FlowerRiseEffect(float x, float y) {
        this.setImg();
        this.startingDuration = MathUtils.random(0.6F, 1.5F);
        this.duration = this.startingDuration;
        float r = MathUtils.random(-13.0F, 13.0F) * MathUtils.random(-13.0F, 13.0F);
        this.x = x + r * Settings.scale - (float)this.img.getWidth() / 5.0F;
        this.y = y + MathUtils.random(-180.0F, 0.0F) * Settings.scale - (float)this.img.getHeight() / 2.0F;
        this.vX = MathUtils.random(-25.0F, 25.0F) * Settings.scale;
        r = MathUtils.random(3.0F, 30.0F);
        this.vY = r * r / this.startingDuration * Settings.scale;
        this.vY2 = MathUtils.random(-100.0F, 100.0F) * Settings.scale;
        this.scale = 0.33f;
        this.vS = MathUtils.random(-0.5F, 0.5F) * Settings.scale;

        ///The color starts white
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.color.r = MathUtils.random(0.1F, 1F);
        this.color.g = MathUtils.random(0.1F, 1F);
        this.color.b = MathUtils.random(0.1F, 1F);





        this.rotation = MathUtils.random(-10.0F, 10.0F);
        this.scale = Settings.scale * MathUtils.random(0.2F, 1.5F);
        this.renderBehind = MathUtils.randomBoolean(0.35F);
    }

    public void update() {
        if (this.delayTimer > 0.0F) {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        } else {
            this.x += this.vX * Gdx.graphics.getDeltaTime();
            this.y += this.vY * Gdx.graphics.getDeltaTime();
            this.vY += this.vY2 * Gdx.graphics.getDeltaTime();
            this.vY *= 59.0F * Gdx.graphics.getDeltaTime();
            this.scale += this.vS * Gdx.graphics.getDeltaTime();
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            } else if (this.duration > this.startingDuration / 2.0F) {
                this.color.a = Interpolation.fade.apply(0.0F, 0.5F, (this.startingDuration - this.duration) / (this.startingDuration / 2.0F));
            } else if (this.duration < this.startingDuration / 2.0F) {
                this.color.a = Interpolation.fade.apply(0.0F, 0.5F, this.duration / (this.startingDuration / 2.0F));
            }

        }
    }

    private void setImg() {
        this.img = FlowerTex;
        this.img2 = StamenTex;

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        if (this.flipX) {
            sb.draw(this.img, 0, 0, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F);
            sb.draw(this.img2, 0, 0, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F);
        } else if (!this.flipX) {
            sb.draw(this.img, 0, 0, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F);
            sb.draw(this.img2, 0, 0, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F);
        }

        sb.draw(this.img, this.x, this.y, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F);
        sb.draw(this.img2, this.x, this.y, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
