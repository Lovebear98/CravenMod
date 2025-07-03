package craven.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import craven.cards.generated.Mechanics.Rations;
import craven.util.CustomActions.cardmanip.MakeTempCardInExhaustAction;

import static craven.util.otherutil.Wiz.DevourEnabled;

@SpirePatch(clz = AbstractPlayer.class, method = "gainEnergy")
    public class GainEnergyPatch {
        @SpirePrefixPatch
        public static SpireReturn FixPrep(AbstractPlayer __instance, int e) {
            if (DevourEnabled() && e > 0) {
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInExhaustAction(new Rations(), e));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }