package craven.patches.visual;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static craven.util.otherutil.Wiz.DevourEnabled;


@SpirePatch(
        clz = EnergyPanel.class,
        method = "setEnergy"
)
public class FixEnergyRegenPatch {
    ///When they try to set our energy to any fixed amount
    ///Tell them no. We don't have energy.
    @SpirePrefixPatch
    public static SpireReturn FixRegen() {
            if (DevourEnabled()) {
                return SpireReturn.Return();
            }
        return SpireReturn.Continue();
    }
}