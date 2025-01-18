package coyotle.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import coyotle.cards.AbstractShardCard;
import coyotle.cards.generated.Mechanics.TheDream;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.ImageManager.UIMeasure;
import static coyotle.util.otherutil.variables.Variables.isInCombat;
import static coyotle.util.otherutil.variables.Variables.p;


@SuppressWarnings("unused")
public class ProphecyPanel extends ClickableUIElement{
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(ProphecyPanel.class.getSimpleName()));
    private boolean ShowKeywords = false;


    public static boolean FixPreviewInstantly = false;
    public ProphecyPanel(){
        super(UIMeasure,x,y,w,h);
        setClickable(false);

        this.hitbox.resize(w, h);
        UpdateHitbox();

        Fix();
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
        sb.setColor(Color.WHITE.cpy());

        if(preview != null){
            preview.render(sb);
            if(ShowKeywords){
                TipHelper.renderTipForCard(preview, sb, preview.keywords);
            }
        }
        sb.setColor(Color.WHITE.cpy());



        UpdateHitbox();
        this.hitbox.render(sb);
    }


    @Override
    protected void onHover(){
        if(!AbstractDungeon.isScreenUp){
            ShowKeywords = true;
        }
    }
    @Override
    protected void onUnhover(){
        ShowKeywords = false;
    }



    @Override
    public void update() {
        if(FixPreviewInstantly){
            Fix();
            FixPreviewInstantly = false;
        }
        if(preview != null){
            preview.update();
        }
        super.update();
    }


    private void RenderTooltip(String Name, String Desc){
        TipHelper.renderGenericTip(Tempx + this.hitbox.width * 1.05f, y+ ( this.hitbox.height * 0.5f), Name, Desc);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();
    }

    @Override
    protected void onClick(){
    }

    private void AdjustSpin(float rate) {
        Tickspeed -= 0.8 * rate;
    }
    public static void drawSpinning(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    ///Declare these down here for ease of reading
    private static float Tickspeed = 0f;
    public static float SpinMod = 0.15f;
    private static Color RandomColor;
    ///Fixes sizes
    public static final float SizeCorrect = (1.4f*Settings.scale);
    ///The size of the UI
    private static final float w = UIMeasure.getWidth() * SizeCorrect;
    private static final float h = UIMeasure.getHeight() * SizeCorrect;

    ///Locations
    private static final float x = (Settings.WIDTH * 0.625f)-w;
    private static final float y = (Settings.HEIGHT * 0.175f)+h;
    private static final float Startx = 0 - (w * 1.5f);
    private static float Goalx = 0 + (w * 0.25f);
    private static float Tempx = Goalx;


    AbstractCard preview = new TheDream();

    private boolean HBFixed = false;
    public void Fix() {
        AbstractCard OldCard = preview;
        if(p() != null && isInCombat() && !p().drawPile.isEmpty()){
            preview = p().drawPile.getBottomCard().makeSameInstanceOf();
        }else{
            preview = new TheDream();
        }
        preview.drawScale = 0.65f;
        preview.targetDrawScale = 0.65f;
        if(!HBFixed){
            this.hitbox.resize(preview.hb.width, preview.hb.height);
            HBFixed = true;
        }

        if(p() != null){
            preview.applyPowers();
        }
        preview.target_x = Tempx+(this.hitbox.width/2);
        preview.target_y = y+(this.hitbox.height/2);
        preview.current_x = Tempx+(this.hitbox.width/2);
        preview.current_y = y+(this.hitbox.height/2);

        if(preview.uuid != OldCard.uuid && !(preview instanceof TheDream && OldCard instanceof TheDream)){
            AbstractDungeon.topLevelEffectsQueue.add(new CardFlashVfx(preview, Color.WHITE.cpy()));
        }
    }


    private String GetName(){
        if(preview != null){
            return preview.name;
        }
        return uiStrings.EXTRA_TEXT[0];
    }

    private String GetType(){
        if(preview != null){
            if(preview.type == AbstractCard.CardType.ATTACK){
                return uiStrings.TEXT[1];
            }
            if(preview.type == AbstractCard.CardType.SKILL){
                return uiStrings.TEXT[2];
            }
            if(preview.type == AbstractCard.CardType.POWER){
                return uiStrings.TEXT[3];
            }
            if(preview.type == AbstractCard.CardType.STATUS){
                return uiStrings.TEXT[4];
            }
            if(preview.type == AbstractCard.CardType.CURSE){
                return uiStrings.TEXT[5];
            }
        }
        return uiStrings.TEXT[6];
    }

    private String GetCost(){
        if(preview != null){
            if(preview.costForTurn >= 0){
                return String.valueOf(preview.costForTurn);
            }
            if(preview.costForTurn == -1){
                return "X";
            }
            if(preview.costForTurn == -2){
                return " ";
            }
        }
        return String.valueOf(-2);
    }

    private String GetDescription(){
        String s = uiStrings.EXTRA_TEXT[1];
        if(preview != null){
            s = preview.rawDescription;
            if(preview instanceof AbstractShardCard){
                s = s.replace("!${modID}:M2!", String.valueOf(((AbstractShardCard) preview).secondMagic));
            }
            s = s.replace("!D!", String.valueOf(preview.damage));
            s = s.replace("!B!", String.valueOf(preview.block));
            s = s.replace("!M!", String.valueOf(preview.magicNumber));
            s = s.replace("${modID}:", "#y");
            s = s.replace("_", "");
            /// = s.replace("the dream", "#yThe #yDream");
        }
        return s;
    }

    private String GetDesc(){
        return "#y<"+GetCost()+"> " + GetType() + " NL " + GetDescription();
    }
}