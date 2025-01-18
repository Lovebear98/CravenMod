package pathmaker.character;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import pathmaker.util.TextureLoader;

import static pathmaker.util.otherutil.variables.Variables.isInCombat;

public class PathmakerOrb extends CustomEnergyOrb {

    ///This corrects sizes to scale appropriately, don't ask questions.
    private static final float SizeCorrect = (1.4f* Settings.scale);



    ///Textures!
    private static final Texture Top = TextureLoader.getTexture("pathmaker/images/character/orb/ABacking1.png");
    private static final Texture Mid = TextureLoader.getTexture("pathmaker/images/character/orb/ABacking2.png");
    private static final Texture Bot = TextureLoader.getTexture("pathmaker/images/character/orb/ABacking3.png");
    private static final Texture BG = TextureLoader.getTexture("pathmaker/images/character/orb/Background.png");

    private static final Texture Gem = TextureLoader.getTexture("pathmaker/images/character/orb/Gem.png");

    private static final Texture FX = TextureLoader.getTexture("pathmaker/images/character/orb/VFX.png");


    ///The size of any given UI element, since it's all large sized
    private static final float w = Top.getWidth() * SizeCorrect;
    private static final float h = Top.getHeight() * SizeCorrect;



    ///These control the current rotation speeds
    private static float Tickspeed = 120f;
    public static float SpinMod = 1f;


    ///Position


    public PathmakerOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
        orbVfx = FX;
    }
    public PathmakerOrb(){
        this(null, null, null);
    }

    @Override
    public void updateOrb(int energyCount) {
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {

        float My_x = current_x - (w * 0.5f);
        float My_y = current_y - (h * 0.5f);
        if(isInCombat()){
            sb.end();
            sb.begin();



            AdjustSpin(SpinMod);
            drawSpinning(sb, BG, My_x, My_y, 0);
            drawSpinning(sb, Gem, My_x, My_y, Tickspeed * 1.3f);
            drawSpinning(sb, Bot, My_x, My_y, Tickspeed * 0.35f);
            drawSpinning(sb, Mid, My_x, My_y, -Tickspeed * 0.7f);
            drawSpinning(sb, Top, My_x, My_y, Tickspeed * 0.55f);
        }
    }



    ///We control the speed of the adjustment so that we can cause a global slowdown
    private void AdjustSpin(float rate) {
        Tickspeed -= 0.8 * rate;
    }


    public static void drawSpinning(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * 0.5f), (texture.getHeight() * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public static boolean CloseEnough(float number, float scale){
        return number > scale * 0.95f || number < scale * 0.05f;
    }
}
