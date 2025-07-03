package craven.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static craven.util.otherutil.MechanicManager.AttemptNoDrawText;
import static craven.util.otherutil.MechanicManager.RemainingDraws;
import static craven.util.otherutil.Wiz.DevourEnabled;

///This allows us to manually lock card draw when Risk is capped, even mid-draw-action.
@SpirePatch(clz = AbstractPlayer.class, method = "draw", paramtypez = {int.class})
    public class DrawLockPatch {
        @SpirePrefixPatch
        public static SpireReturn FixPrep(AbstractPlayer __instance, int e) {
            if (DevourEnabled()) {
                if(RemainingDraws() < e){
                    __instance.draw(RemainingDraws());
                    AttemptNoDrawText();
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }