package pathmaker.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.BloomManager.*;
import static pathmaker.util.otherutil.ImageManager.*;


@SuppressWarnings("unused")
public class BloomPanel extends ClickableUIElement{
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(BloomPanel.class.getSimpleName()));


    public BloomPanel(){
        super(GrayPetals,x,y,w,h);
        setClickable(false);

        this.hitbox.resize(w, h);
        UpdateHitbox();

        ///Give us a random color on generation
        FixColor();
    }

    private void UpdateHitbox() {
        this.hitbox.translate(Tempx, y);
    }

    @Override
    public void render(SpriteBatch sb,Color color){

        float My_x = Tempx;
        float My_y = y;

        ///Start rendering
        sb.end();
        Gdx.gl.glColorMask(true,true,true,true);
        sb.begin();

        //Spin the flower a little bit
        AdjustSpin(SpinMod);

        ///Update our TempX and GoalX relation
        Tempx = Interpolation.bounce.apply(Tempx, Goalx, 0.2f);





        if(BloomCount >= 1){
            sb.setColor(RandomColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        drawSpinning(sb, TPetal, My_x, My_y, Tickspeed);


        if(BloomCount >= 2){
            sb.setColor(RandomColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        drawSpinning(sb, TRPetal, My_x, My_y, Tickspeed);


        if(BloomCount >= 3){
            sb.setColor(RandomColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        drawSpinning(sb, BRPetal, My_x, My_y, Tickspeed);


        if(BloomCount >= 4){
            sb.setColor(RandomColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        drawSpinning(sb, BPetal, My_x, My_y, Tickspeed);


        if(BloomCount >= 5){
            sb.setColor(RandomColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        drawSpinning(sb, BLPetal, My_x, My_y, Tickspeed);


        if(BloomCount >= 6){
            sb.setColor(RandomColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        drawSpinning(sb, TLPetal, My_x, My_y, Tickspeed);

        sb.setColor(Color.WHITE.cpy());




        ///Write the page number
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, String.valueOf(BloomCount), My_x+(w*0.5f), My_y+(h*0.5f), Color.WHITE.cpy());



        UpdateHitbox();
        this.hitbox.render(sb);
    }

    public static void FixColor() {
        Color c = new Color();
        RandomColor = new Color(MathUtils.random(0.6f, 1.0f), MathUtils.random(0.2f, 0.7f), MathUtils.random(0.2f, 0.7f), 1.0f);
    }


    @Override
    protected void onHover(){
        if(!AbstractDungeon.isScreenUp){
            RenderTooltip(uiStrings.TEXT[0], uiStrings.TEXT[1]+((BloomCap() - BloomCount)+1)+uiStrings.TEXT[2]);
        }
    }



    @Override
    public void update() {
        super.update();

    }

    @Override
    protected void onUnhover(){
    }
    private void RenderTooltip(String Name, String Desc){
        TipHelper.renderGenericTip(Tempx + w, y+ ( h * 0.5f), Name, Desc);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();
    }

    @Override
    protected void onClick(){
    }



    ///Reset the UI's position and give it another random color
    public static void SnapBloomPanelSpot(){
        Tempx = Startx;
        FixColor();
    }

    private void AdjustSpin(float rate) {
        Tickspeed -= 0.8 * rate;
        if(BloomReady()){
            Tickspeed -= 1.6 * rate;
        }
    }
    public static void drawSpinning(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * 0.5f), (texture.getHeight() * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    ///Declare these down here for ease of reading
    private static float Tickspeed = 0f;
    public static float SpinMod = 0.15f;
    private static Color RandomColor;
    ///Fixes sizes
    private static final float SizeCorrect = (1.4f*Settings.scale);
    ///The size of the UI
    private static final float w = GrayPetals.getWidth() * SizeCorrect;
    private static final float h = GrayPetals.getHeight() * SizeCorrect;

    ///Locations
    private static final float x = (Settings.WIDTH * 0.625f)-w;
    private static final float y = (Settings.HEIGHT * 0.10f)+h;
    private static final float Startx = 0 - (w * 1.5f);
    private static float Tempx = Startx;
    private static float Goalx = 0 + (w * 0.25f);



}