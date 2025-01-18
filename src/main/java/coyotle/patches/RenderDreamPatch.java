package coyotle.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static coyotle.util.otherutil.DreamManager.inDream;
import static coyotle.util.otherutil.DreamManager.renderInDream;
import static coyotle.util.otherutil.variables.Variables.isInCombat;
import static coyotle.util.otherutil.variables.Variables.p;

public class RenderDreamPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class DreamDraw {
        @SpirePrefixPatch
        public static void DoTheThing(AbstractPlayer ___instance, SpriteBatch sb) {
            if(p() != null && isInCombat() && inDream){
                renderInDream();
            }
        }
    }
}
