package craven.patches.visual;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import craven.character.CravenCharacter;
import craven.util.TextureLoader;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;


///This is replacing the SMALL button in the custom screen, currently unconditionally
public class ButtonGlowPatch {
    @SpirePatch2(clz = CustomModeCharacterButton.class, method = "renderOptionButton")
    public static class CustomModeGlow {
        @SpireInstrumentPatch
        public static ExprEditor sob() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(ImageMaster.class.getName()) && m.getFieldName().equals("FILTER_GLOW_BG")) {
                        m.replace("$_ =  craven.patches.visual.ButtonGlowPatch.CustomModeButtGlow(this);");
                    }
                }
            };
        }
    }


    @SpirePatch2(clz = CharacterOption.class, method = "renderOptionButton")
    public static class SelectGlow {
        @SpireInstrumentPatch
        public static ExprEditor sob() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(ImageMaster.class.getName()) && m.getFieldName().equals("CHAR_OPT_HIGHLIGHT")) {
                        m.replace("$_ = craven.patches.visual.ButtonGlowPatch.SelectButtGlow(this);");
                    }
                }
            };
        }
    }

    public static final Texture CustomModeButtGlow = TextureLoader.getTexture("craven/images/character/select/CustomGlow.png");
    public static final Texture SelectButtGlow = TextureLoader.getTexture("craven/images/character/select/SelectGlow.png");
    public static Texture CustomModeButtGlow(CustomModeCharacterButton b){
        if(b.c instanceof CravenCharacter){
            return CustomModeButtGlow;
        }
        return ImageMaster.FILTER_GLOW_BG;
    }
    public static Texture SelectButtGlow(CharacterOption b){
        if(b.c instanceof CravenCharacter){
            return SelectButtGlow;
        }
        return ImageMaster.CHAR_OPT_HIGHLIGHT;
    }
}