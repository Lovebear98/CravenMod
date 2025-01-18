package coyotle.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static coyotle.CoyotleMod.ShardPreview;
import static coyotle.util.otherutil.Wiz.ValidForShards;
import static coyotle.util.otherutil.variables.Variables.isInCombat;


///The patch tht handles showing our UI
public class UIPatch {

    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch{
        public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
            if(isInCombat()){
                ///If the player exists
                if(ValidForShards()){
                        ShardPreview.render(sb);
                }
            }
        }
    }
    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch{
        public static void Prefix(){
            if(isInCombat()){
                if(ValidForShards()){
                    ShardPreview.update();
                }
            }
        }
    }
}