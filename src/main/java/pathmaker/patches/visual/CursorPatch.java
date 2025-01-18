package pathmaker.patches.visual;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rewards.chests.*;
import pathmaker.util.TextureLoader;

import static pathmaker.util.otherutil.ConfigManager.EnableAltUI;

public class CursorPatch {

    private static final Texture PathCursor = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/Cursor.png");
    private static final Texture ORIG_CURSOR = ImageMaster.loadImage("images/ui/cursors/gold2.png");

    private static boolean CursorFixed = false;

    @SpirePatch(
            clz = GameCursor.class,
            method = "changeType"
    )
    public static class ChestReskinPatch {
        @SpirePrefixPatch
        public static void Postfix(GameCursor __instance, GameCursor.CursorType type) {
            if(!CursorFixed){
                if(EnableAltUI){
                    CursorFixed = true;
                    ReflectionHacks.setPrivate(__instance, GameCursor.class, "img", PathCursor);
                }
            }else{
                if(!EnableAltUI){
                        CursorFixed = false;
                        ReflectionHacks.setPrivate(__instance, GameCursor.class, "img", ORIG_CURSOR);
                }
            }
        }
    }






    @SpirePatch(
            clz= AbstractChest.class,
            method=SpirePatch.CLASS)
    public static class ChestReskinField {
        public static SpireField<Boolean> Reskinned = new SpireField<>(() -> false);

    }


}
