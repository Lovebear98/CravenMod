package craven.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import craven.util.CustomActions.StartTutorialAction;
import craven.util.TextureLoader;

import static craven.CravenMod.TipUI;
import static craven.CravenMod.makeID;
import static craven.character.RiskGauge.SizeCorrect;
import static craven.util.otherutil.Wiz.DevourEnabled;
import static craven.util.otherutil.variables.Variables.p;


@SuppressWarnings("unused")
public class TipPanel extends ClickableUIElement{
    public static float ClickLock = 0f;
    private static final Texture TipUITex = TextureLoader.getTexture("craven/images/ui/UIBit.png");
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(TipPanel.class.getSimpleName()));


    public TipPanel(){
        super(TipUITex,x,y,w,h);
        setClickable(true);


        UpdateHitbox();
    }

    private void UpdateHitbox() {
        this.hitbox.translate(x, y);
        this.hitbox.resize(w, h);
    }

    @Override
    public void render(SpriteBatch sb,Color color){
        ///Start rendering
        sb.end();
        Gdx.gl.glColorMask(true,true,true,true);
        sb.begin();


        UpdateHitbox();
        sb.draw(TipUITex, x, y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.rotation, 0, 0, 128, 128, false, false);

        if(Settings.isControllerMode){
            sb.draw(CInputActionSet.cancel.getKeyImg(), x + 0.0F * Settings.scale, y - 32.0F + 25f * Settings.scale, 32.0F, 32.0F, 32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            if(HoldTimer >= (BaseHoldTimer * 0.25f)){
                FontHelper.blockInfoFont.getData().setScale(1.0F);
                FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, String.valueOf(HoldTime()),x+32f*Settings.scale, y + 25f * Settings.scale, Color.WHITE.cpy());
            }
        }

        this.renderFlash(sb, true);
        this.hitbox.render(sb);
    }

    private float HoldTime() {
        float f = (BaseHoldTimer - HoldTimer);
        f = (float) Math.floor(f * 100) / 100;;
        return f;
    }


    @Override
    protected void onHover(){
        if(!AbstractDungeon.isScreenUp){
            RenderTooltip(uiStrings.TEXT[0], uiStrings.TEXT[1]);
        }
    }



    @Override
    public void update() {
        super.update();
        updateFlash();

        if(!AbstractDungeon.isScreenUp  && CInputActionSet.cancel.isPressed() && !CInputActionSet.cancel.justPressed){
            HoldTimer += Gdx.graphics.getDeltaTime();
            if(HoldTimer > BaseHoldTimer){
                HoldTimer = BaseHoldTimer;
            }
        }else{
            if(HoldTimer != 0){
                HoldTimer = 0;
            }
        }

        if(ClickLock <= 0){
            if(!AbstractDungeon.isScreenUp && ((hitbox.hovered && InputHelper.justReleasedClickLeft) || HoldTimer >= BaseHoldTimer)){
                HoldTimer = 0;
                p().hoveredCard = null;
                ResetClickLock();
                AbstractDungeon.actionManager.addToBottom(new StartTutorialAction());
            }
        }

        ClickLock -= Gdx.graphics.getDeltaTime();
        if(ClickLock < 0){
            ClickLock = 0;
        }
    }

    private static float HoldTimer = 3f;
    private static float BaseHoldTimer = 3f;

    @Override
    protected void onUnhover(){
    }
    private void RenderTooltip(String Name, String Desc){
        TipHelper.renderGenericTip(x - w - Settings.WIDTH * 0.1f, y+ ( h * 0.5f), Name, Desc);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();
    }

    @Override
    protected void onClick(){
    }

    public static void drawSpinning(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    ///The size of the UI
    private static final float w = TipUITex.getWidth() * SizeCorrect;
    private static final float h = TipUITex.getHeight() * SizeCorrect;

    ///Locations
    private static final float x = (Settings.WIDTH)-(w * 1.1f);
    private static final float y = (Settings.HEIGHT * 0.2f)+h;


    public float flashTimer = 0F;
    private void updateFlash() {
        if (this.flashTimer != 0.0F) {// 504
            this.flashTimer -= Gdx.graphics.getDeltaTime();// 505
            if (this.flashTimer < 0.0F) {// 506
                this.flashTimer = 0.0F;// 510
            }
        }

    }
    public void renderFlash(SpriteBatch sb, boolean inTopPanel) {
        float tmp = Interpolation.exp10In.apply(0.0F, 4.0F, this.flashTimer / 2.0F);
        sb.setBlendFunction(770, 1);// 1259
        this.flashColor.a = this.flashTimer * 0.2F;// 1260
        sb.setColor(this.flashColor);// 1261

        sb.draw(TipUITex, x, y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale + tmp, Settings.scale + tmp, this.rotation, 0, 0, 128, 128, false, false);
        sb.draw(TipUITex, x, y- 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale + tmp * 0.66F, Settings.scale + tmp * 0.66F, this.rotation, 0, 0, 128, 128, false, false);
        sb.draw(TipUITex, x, y- 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale + tmp / 3.0F, Settings.scale + tmp / 3.0F, this.rotation, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 771);
    }
    public void flash() {
        this.flashTimer = 2.0F;
    }
    private Color flashColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private static float rotation = 0f;

    public void ResetClickLock() {
         ClickLock = 0.35f;
    }
}