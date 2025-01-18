package coyotle.util.otherutil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static coyotle.ui.ProphecyPanel.SizeCorrect;
import static coyotle.util.otherutil.ImageManager.*;

public class PreloadDreamEffect extends AbstractGameEffect {

    private final float x1;
    private final float y1;
    private final boolean flipX1;
    private final boolean flipY1;
    protected float rotation1 = 0.0f;
    private static Texture img1;
    private static Texture img2;
    private static Texture img3;
    private static Texture img4;
    private static Texture img5;
    private static Texture img6;
    private static Texture img7;

    public PreloadDreamEffect(){

        img1 = DreamSmoke;
        img2 = DreamEye;
        img3 = DreamHorse;
        img4 = DreamTangle;
        img5 = DreamInfinity;
        img6 = DreamBird;
        img7 = DreamCoyotle;



        this.renderBehind = true;

        this.color = new Color(MathUtils.random(15, 200)/255f, MathUtils.random(15, 200)/255f, MathUtils.random(15, 200)/255f, 0f);

        this.x1 = MathUtils.random((0- Settings.WIDTH * 0.25f), Settings.WIDTH);
        this.y1 = MathUtils.random((0-Settings.HEIGHT * 0.25f), Settings.HEIGHT);

        this.flipX1 = MathUtils.randomBoolean();
        this.flipY1 = MathUtils.randomBoolean();

        this.duration = this.startingDuration = 0.1f;
    }
    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0){
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(img1, x1, y1, (img1.getWidth() * SizeCorrect * 0.5f), (img1.getHeight() * SizeCorrect * 0.5f), img1.getWidth()*SizeCorrect, img1.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, img1.getWidth(), img1.getHeight(), this.flipX1, this.flipY1);
        sb.draw(img2, x1, y1, (img2.getWidth() * SizeCorrect * 0.5f), (img2.getHeight() * SizeCorrect * 0.5f), img2.getWidth()*SizeCorrect, img2.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, img2.getWidth(), img2.getHeight(), this.flipX1, this.flipY1);
        sb.draw(img3, x1, y1, (img3.getWidth() * SizeCorrect * 0.5f), (img3.getHeight() * SizeCorrect * 0.5f), img3.getWidth()*SizeCorrect, img3.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, img3.getWidth(), img3.getHeight(), this.flipX1, this.flipY1);
        sb.draw(img4, x1, y1, (img4.getWidth() * SizeCorrect * 0.5f), (img4.getHeight() * SizeCorrect * 0.5f), img4.getWidth()*SizeCorrect, img4.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, img4.getWidth(), img4.getHeight(), this.flipX1, this.flipY1);
        sb.draw(img5, x1, y1, (img5.getWidth() * SizeCorrect * 0.5f), (img5.getHeight() * SizeCorrect * 0.5f), img5.getWidth()*SizeCorrect, img5.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, img5.getWidth(), img5.getHeight(), this.flipX1, this.flipY1);
        sb.draw(img6, x1, y1, (img6.getWidth() * SizeCorrect * 0.5f), (img6.getHeight() * SizeCorrect * 0.5f), img6.getWidth()*SizeCorrect, img6.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, img6.getWidth(), img6.getHeight(), this.flipX1, this.flipY1);
        sb.draw(img7, x1, y1, (img7.getWidth() * SizeCorrect * 0.5f), (img7.getHeight() * SizeCorrect * 0.5f), img7.getWidth()*SizeCorrect, img7.getHeight()*SizeCorrect, 1f, 1f, rotation1, 0, 0, img7.getWidth(), img7.getHeight(), this.flipX1, this.flipY1);
    }

    @Override
    public void dispose() {

    }
}
