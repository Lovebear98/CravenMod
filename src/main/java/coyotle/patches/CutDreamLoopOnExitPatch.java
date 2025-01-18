package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

import static coyotle.util.otherutil.SoundManager.CutDreamAudioLoop;

public class CutDreamLoopOnExitPatch {
    @SpirePatch(
            clz = MainMenuScreen.class,
            method = "update"
    )
    public static class CutThat {

        @SpirePostfixPatch
        public static void ShitOut(MainMenuScreen ___instance) {
            CutDreamAudioLoop();
        }
    }
}
