package craven.patches.visual;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static craven.util.otherutil.Wiz.DevourEnabled;


@SpirePatch(
        clz = EnergyManager.class,
        method = "prep"
)
public class FixEnergyPrepPatch {
    ///When our energy resets, just actively keep it at 0
    ///Don't let it reset to EnergyMaster at any point
    @SpirePrefixPatch
    public static SpireReturn FixPrep(EnergyManager __instance) {
            if (DevourEnabled()) {
                EnergyPanel.totalCount = 0;
                __instance.energy = 0;
                return SpireReturn.Return();
            }
        return SpireReturn.Continue();
    }
}