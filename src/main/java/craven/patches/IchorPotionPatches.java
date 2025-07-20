package craven.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import craven.potions.custompotions.IchorBordeaux;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

///This allows us to manually lock card draw when Risk is capped, even mid-draw-action.
@SpirePatch(clz = TopPanel.class, method = "destroyPotion", paramtypez = {int.class})
    public class IchorPotionPatches {
        @SpirePrefixPatch
        public static SpireReturn FixPrep(TopPanel __instance, int slot) {
            AbstractPotion p = AbstractDungeon.player.potions.get(slot);
            if(p instanceof IchorBordeaux){
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }

    @SpirePatch2(clz = PotionPopUp.class, method = "render")
    public static class FixDiscardColor {

        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if(f.getFieldName().equals("RED_TEXT_COLOR")){
                        f.replace("$_ = craven.patches.IchorPotionPatches.TextColor(this.potion);");
                    }
                }
            };
        }
    }

    public static Color TextColor(AbstractPotion p){
            if(p instanceof IchorBordeaux){
                return Color.GRAY.cpy();
            }
            return Settings.RED_TEXT_COLOR;
        }
}