package craven.patches.visual;


import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import static craven.util.otherutil.Wiz.DevourEnabled;
import static craven.util.otherutil.variables.UIText.EnergyLabel;
import static craven.util.otherutil.variables.UIText.EnergyMsg;


///This is replacing the SMALL button in the custom screen, currently unconditionally
public class HijackEnergyTipPatch {
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Energy Panel Tip");// 24
    private static String HoldLabel;
    private static String HoldMsg;

    @SpirePatch2(clz = EnergyPanel.class, method = "render")
    public static class CustomModeGlow {
        @SpireInstrumentPatch
        public static ExprEditor sob() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(EnergyPanel.class.getName()) && m.getFieldName().equals("LABEL")) {
                        m.replace("$_ = craven.patches.visual.HijackEnergyTipPatch.NewLabel();");
                    }
                }
            };
        }
    }


    @SpirePatch2(clz = EnergyPanel.class, method = "render")
    public static class CustomModeGlow2 {
        @SpireInstrumentPatch
        public static ExprEditor sob() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(EnergyPanel.class.getName()) && m.getFieldName().equals("MSG")) {
                        m.replace("$_ = craven.patches.visual.HijackEnergyTipPatch.NewMsg();");
                    }
                }
            };
        }
    }


    public static String[] NewLabel(){
        if(HoldLabel == null){
            String[] LABEL = tutorialStrings.LABEL;
            HoldLabel = LABEL[0];
        }
        if(DevourEnabled()){
            return new String[]{EnergyLabel()};
        }else{
            return new String[]{HoldLabel};
        }
    }
    public static String[] NewMsg(){
        if(HoldMsg == null){

            String[] MSG = tutorialStrings.TEXT;
            HoldMsg = MSG[0];
        }
        if(DevourEnabled()){
            return new String[]{EnergyMsg()};
        }else{

            return new String[]{HoldMsg};
        }
    }
}