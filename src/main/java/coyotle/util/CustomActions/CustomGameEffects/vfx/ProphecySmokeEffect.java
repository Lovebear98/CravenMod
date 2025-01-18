package coyotle.util.CustomActions.CustomGameEffects.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_X;
import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_Y;
import static coyotle.ui.ProphecyPanel.SizeCorrect;
import static coyotle.util.otherutil.ImageManager.ProphecySmoke;

public class ProphecySmokeEffect extends AbstractGameEffect {

    public static boolean DrawProphecyFX = true;
    private final Texture img;
    private ArrayList<Float> OldxCoords;
    private ArrayList<Float> OldyCoords;
    private float x;
    private float y;

    private static final float tX = DRAW_PILE_X-((ProphecySmoke.getWidth() * SizeCorrect)*0.5f);
    private static final float tY = DRAW_PILE_Y-((ProphecySmoke.getHeight() * SizeCorrect)*0.5f);
    private final float targetAlpha;
    private final float targetscale = 1.25f;
    private boolean Pulsed = false;

    public ProphecySmokeEffect(float cX, float cY){
        this.x = cX;
        this.y = cY;

        DrawProphecyFX = false;

        this.targetAlpha = MathUtils.random(0.4f, 0.8f);
        this.duration = this.startingDuration = 2.75f;

        this.img = ProphecySmoke;

        this.color = Color.WHITE.cpy();
        this.color.a = 0;
        
        OldxCoords = new ArrayList<Float>();
        OldyCoords = new ArrayList<Float>();

    }

    @Override
    public void update() {
        if (this.startingDuration - this.duration < (this.startingDuration * 0.25f)) {
            this.color.a = Interpolation.fade.apply(0.0F, this.targetAlpha, this.startingDuration - this.duration);
        } else if (this.duration < (this.startingDuration * 0.25f)) {
            this.color.a = Interpolation.fade.apply(this.targetAlpha, 0.0F, (this.startingDuration * 0.25f) - this.duration);
        }

        OldxCoords.add(x);
        OldyCoords.add(y);
        if(this.duration >= (this.startingDuration * 0.5f)){
            this.rotation += Gdx.graphics.getDeltaTime() * 100f;
        }else{
            this.rotation += Gdx.graphics.getDeltaTime() * 150f;
        }

        this.x = Interpolation.exp10In.apply(x, tX, (this.startingDuration - 1.5f)-this.duration);
        this.y = Interpolation.exp10In.apply(y, tY, (this.startingDuration - 1.5f)-this.duration);

        if(OldxCoords.size() >= 9){
            OldxCoords.remove(0);
            OldyCoords.remove(0);
        }

        for(float f: OldyCoords){
            f = Interpolation.elastic.apply(f, f * 0.85f, this.startingDuration - this.duration);
        }

        if(this.duration <= this.startingDuration * 0.3f){
            if(!Pulsed){
                Pulsed = true;
                AbstractDungeon.topLevelEffectsQueue.add(new ProphecyDeckPulse());
            }
        }
        if(this.duration <= this.startingDuration * 0.15f){
            this.scale = Interpolation.elastic.apply(this.scale, targetscale, this.startingDuration-this.duration);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            DrawProphecyFX = true;
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, x, y, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale, this.scale, rotation, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
        if(!OldxCoords.isEmpty()){
            for(int i = 0; i < OldxCoords.size(); i += 1){
                Color NewColor = sb.getColor();
                NewColor.a -= (NewColor.a * ((float) i /8));
                sb.setColor(NewColor);
                sb.draw(this.img, OldxCoords.get(i), OldyCoords.get(i), (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale, this.scale, rotation, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
            }
        }

        if(this.duration <= this.startingDuration * 0.10f){
            sb.draw(this.img, x, y, (this.img.getWidth() * SizeCorrect * 0.5f), (this.img.getHeight() * SizeCorrect * 0.5f), this.img.getWidth()*SizeCorrect, this.img.getHeight()*SizeCorrect, this.scale, this.scale, rotation * 0.5f, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
        }
    }

    @Override
    public void dispose() {

    }
}