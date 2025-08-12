package craven.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.UnceasingTop;

import static craven.util.otherutil.MechanicManager.*;
import static craven.util.otherutil.Wiz.DevourEnabled;

///This allows us to manually lock card draw when Risk is capped, even mid-draw-action.
@SpirePatch(clz = UnceasingTop.class, method = "onRefreshHand")
    public class UnceasingTopPatch {
        @SpirePrefixPatch
        public static SpireReturn FixPrep() {
            if (DevourEnabled()) {
                if(Risk >= TrueRiskCap()){
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }