package craven.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.Objects;

import static craven.util.otherutil.MechanicManager.CravingCap;
import static craven.util.otherutil.Wiz.DevourEnabled;


/// Turns out that NewQueueCardActions grab EnergyPanel.totalcount at all times,
/// so we had to fix that so X-Costs would actually do something.
/// Whoops.
public class XCostAutoplayPatch {
    @SpirePatch2(clz = NewQueueCardAction.class, method = "update")
    public static class FixXAuto {
        @SpireInstrumentPatch
        public static ExprEditor sob() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(EnergyPanel.class.getName()) && (Objects.equals(m.getMethodName(), "getCurrentEnergy"))) {
                        m.replace("$_ =  craven.patches.XCostAutoplayPatch.GetRealEnergy(this.card);");
                    }
                }
            };
        }
    }


    public static int GetRealEnergy(AbstractCard c){
        if(DevourEnabled() && c != null){
            return CravingCap(c);
        }
        return EnergyPanel.getCurrentEnergy();
    }
}