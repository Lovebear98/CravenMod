package craven.character;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import craven.util.TextureLoader;

import static craven.util.otherutil.MechanicManager.RiskLevel;
import static craven.util.otherutil.variables.Variables.isInCombat;

public class RiskGauge extends CustomEnergyOrb {

    ///This corrects sizes to scale appropriately, don't ask questions.
    private static final float SizeCorrect = (1.4f* Settings.scale);



    ///Textures!
    private static final Texture NoRisk = TextureLoader.getTexture("craven/images/character/orb/NoRisk.png");
    private static final Texture LowRisk = TextureLoader.getTexture("craven/images/character/orb/LowRisk.png");
    private static final Texture MidRisk = TextureLoader.getTexture("craven/images/character/orb/MidRisk.png");
    private static final Texture HighRisk = TextureLoader.getTexture("craven/images/character/orb/HighRisk.png");
    private static final Texture MaxRisk = TextureLoader.getTexture("craven/images/character/orb/MaxRisk.png");

    private static final Texture Berries = TextureLoader.getTexture("craven/images/character/orb/Berries.png");

    private static final Texture FX = TextureLoader.getTexture("craven/images/character/orb/VFX.png");


    ///The size of any given UI element, since it's all large sized
    private static final float w = NoRisk.getWidth() * SizeCorrect;
    private static final float h = NoRisk.getHeight() * SizeCorrect;



    ///These control the current rotation speeds
    private static float Tickspeed = 0.6f;
    public static int BerryCheck = 0;


    ///Position


    public RiskGauge(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
        orbVfx = FX;
    }
    public RiskGauge(){
        this(null, null, null);
    }

    @Override
    public void updateOrb(int energyCount) {
        AdjustSpin(0.3f * (4-RiskLevel()));

    }
    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        float My_x = current_x - (w * 0.5f);
        float My_y = current_y - (h * 0.5f);


        if(isInCombat()){
            sb.end();
            sb.begin();
            sb.setColor(Color.WHITE.cpy());

            if(RiskLevel() < 4){
                drawSpinning(sb, Berries, My_x, My_y, Tickspeed * 1f);
                drawSpinning(sb, Berries, My_x, My_y, Tickspeed * -1f);
            }

            drawSpinning(sb, RiskStateTex(), My_x, My_y, 0);

            BerryCheck = RiskLevel();
        }

        sb.setColor(Color.WHITE.cpy());
    }

    private Texture RiskStateTex() {
        if(RiskLevel() >= 4){
            return MaxRisk;
        }
        if(RiskLevel() == 3){
            return HighRisk;
        }
        if(RiskLevel() == 2){
            return MidRisk;
        }
        if(RiskLevel() == 1){
            return LowRisk;
        }
        return NoRisk;
    }


    ///We control the speed of the adjustment so that we can cause a global slowdown
    private void AdjustSpin(float rate) {
        Tickspeed -= rate;
    }


    public static void drawSpinning(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    ///(TextureToDraw, LocationX, LocationY, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)

    public static boolean CloseEnough(float number, float scale){
        return number > scale * 0.95f || number < scale * 0.05f;
    }
}
