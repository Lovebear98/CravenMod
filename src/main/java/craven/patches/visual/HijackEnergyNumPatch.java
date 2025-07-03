package craven.patches.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static craven.util.otherutil.MechanicManager.Risk;
import static craven.util.otherutil.MechanicManager.TrueRiskCap;
import static craven.util.otherutil.Wiz.DevourEnabled;

;

public class HijackEnergyNumPatch {

    @SpirePatch(
            clz= EnergyPanel.class,
            method="render"
    )
    public static class HijackNumber {

        @SpireInsertPatch(
                loc = 147,
                localvars = {"energyMsg"}
        )
        public static void Yoink(EnergyPanel __instance, SpriteBatch sb,  @ByRef String[] s){
                if(DevourEnabled()){
                    s[0] = GetRiskText();
                }
        }

        private static String GetRiskText() {
            return Risk + "/" + TrueRiskCap();
        }
    }
}
