package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import coyotle.util.CustomActions.MillAction;

import static coyotle.util.otherutil.DreamManager.EnterDream;

public class PostMillPatch {

    @SpirePatch(
            clz = MillAction.class,
            method = "update"
    )
    public static class MillDream {

        @SpirePostfixPatch
        public static void Dreamtime(MillAction ___instance) {
            if(___instance.isDone){
                EnterDream();
            }
        }
    }

    @SpirePatch(
            clz = com.megacrit.cardcrawl.actions.common.MillAction.class,
            method = "update"
    )
    public static class MillDream2 {

        @SpirePostfixPatch
        public static void Dreamtime(com.megacrit.cardcrawl.actions.common.MillAction ___instance) {
            if(___instance.isDone){
                EnterDream();
            }
        }
    }
}
