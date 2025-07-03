package craven.patches.visual;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAndEnableControlsAction;

import static craven.util.otherutil.Wiz.DevourEnabled;
import static craven.util.otherutil.variables.Variables.p;

public class FixCombatStartPatch{
    @SpirePatch(clz = GainEnergyAndEnableControlsAction.class, method = "update")


    ///Turns out the game uses an 'addEnergy' on the first turn outside of prep, energy regen,
    // and setenergy to get your first turn ready. We just uh...need to tell it no.
    public static class Whoops {
        @SpirePrefixPatch
        public static void Postfix(GainEnergyAndEnableControlsAction __instance, @ByRef int[] ___energyGain) {
            if(p() != null){
                if(DevourEnabled()){
                    ___energyGain[0] = 0;
                }
            }
        }
    }
}