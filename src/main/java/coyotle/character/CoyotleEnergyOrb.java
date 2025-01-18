package coyotle.character;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import coyotle.util.TextureLoader;

import static coyotle.util.otherutil.variables.Variables.isInCombat;

public class CoyotleEnergyOrb extends CustomEnergyOrb {

    ///This corrects sizes to scale appropriately, don't ask questions.
    private static final float SizeCorrect = (1.4f* Settings.scale);



    ///Textures!
    private static final Texture Top = TextureLoader.getTexture("coyotle/images/character/orb/ABacking1.png");
    private static final Texture Mid = TextureLoader.getTexture("coyotle/images/character/orb/ABacking2.png");
    private static final Texture Bot = TextureLoader.getTexture("coyotle/images/character/orb/ABacking3.png");
    private static final Texture BG = TextureLoader.getTexture("coyotle/images/character/orb/Background.png");
    private static final Texture Case = TextureLoader.getTexture("coyotle/images/character/orb/Topping.png");

    private static final Texture Gem = TextureLoader.getTexture("coyotle/images/character/orb/Gem.png");

    private static final Texture FX = TextureLoader.getTexture("coyotle/images/character/orb/VFX.png");


    ///The size of any given UI element, since it's all large sized
    private static final float w = Top.getWidth() * SizeCorrect;
    private static final float h = Top.getHeight() * SizeCorrect;



    ///These control the current rotation speeds
    private static float Tickspeed = 120f;
    public static float SpinMod = 1f;


    private static boolean Spinning = true;
    private static float SlowRate = 0.25f;
    private static float StartRate = 0.25f;
    private static float MinSlowRate = 0.0f;
    private static float MaxStartRate = 1.25f;


    ///Position


    public CoyotleEnergyOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
        orbVfx = FX;
    }
    public CoyotleEnergyOrb(){
        this(null, null, null);
    }

    @Override
    public void updateOrb(int energyCount) {
        if(Spinning && energyCount == 0){
            Spinning = false;
        }else if(!Spinning && energyCount != 0){
            Spinning = true;
        }


        if(Spinning){
            SlowRate = MaxStartRate;
            StartRate += Gdx.graphics.getDeltaTime();
            if(StartRate >= MaxStartRate){
                StartRate = MaxStartRate;
            }
        }else{
            StartRate = MinSlowRate;
            SlowRate -= Gdx.graphics.getDeltaTime();
            if(SlowRate <= MinSlowRate){
                SlowRate = MinSlowRate;
            }
        }
    }
    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {


        float My_x = current_x - (w * 0.5f);
        float My_y = current_y - (h * 0.5f);
        if(isInCombat()){
            sb.end();
            sb.begin();

            if(!Spinning){
                sb.setColor(Color.LIGHT_GRAY.cpy());
            }


            if(Spinning){
                AdjustSpin(StartRate);
            }else{
                AdjustSpin(SlowRate);
            }
            drawSpinning(sb, BG, My_x, My_y, 0);
            drawSpinning(sb, Gem, My_x, My_y, Tickspeed * 1.3f);
            drawSpinning(sb, Bot, My_x, My_y, Tickspeed * 0.35f);
            drawSpinning(sb, Mid, My_x, My_y, -Tickspeed * 0.7f);
            drawSpinning(sb, Top, My_x, My_y, Tickspeed * 0.55f);

            drawSpinning(sb, Case, My_x, My_y, 0);
        }
        sb.setColor(Color.WHITE.cpy());
    }



    ///We control the speed of the adjustment so that we can cause a global slowdown
    private void AdjustSpin(float rate) {
        Tickspeed -= 0.8 * rate;
    }


    public static void drawSpinning(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    ///(TextureToDraw, LocationX, LocationY, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)

    public static boolean CloseEnough(float number, float scale){
        return number > scale * 0.95f || number < scale * 0.05f;
    }
}
