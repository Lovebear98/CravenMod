package craven.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static craven.CravenMod.TipUI;
import static craven.util.otherutil.Wiz.DevourEnabled;
import static craven.util.otherutil.variables.Variables.isInCombat;


///The patch that handles showing our UI
public class UIPatch {

    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch{
        public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
            if(isInCombat()){
                if(DevourEnabled()){
                        TipUI.render(sb);
                }
            }
        }
    }
    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch{
        public static void Prefix(){
            if(isInCombat()){
                if(DevourEnabled()){
                        TipUI.update();
                }
            }
        }
    }
}