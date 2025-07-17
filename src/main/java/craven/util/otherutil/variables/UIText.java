package craven.util.otherutil.variables;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.TrueRiskCap;

public class UIText {
    public static final UIStrings UIText = CardCrawlGame.languagePack.getUIString(makeID(UIText.class.getSimpleName()));

    /** Text used in config options */
    public static String EYECANDYTEXT(){
        return UIText.TEXT[0];
    }
    public static String EARCANDYTEXT(){
        return UIText.TEXT[1];
    }
    public static String SHOWTIPTEXT(){
        return UIText.TEXT[2];
    }
    public static String ENNABLEEVENTSTEXT(){
        return UIText.TEXT[4];
    }
    public static String SHOWTUTORIALTEXT(){
        return UIText.TEXT[3];
    }
    public static String ALLDEVOURTEXT(){
        return UIText.TEXT[5];
    }
    public static String CantDrawText(){
        return UIText.TEXT[6];
    }
    public static String STARTERRATIONSTEXT(){
        return UIText.TEXT[7];
    }
    public static String BRANCHNERFTEXT(){
        return UIText.TEXT[8];
    }




    /** Labels for the Energy Panel patch */
    public static String EnergyLabel() {
        return UIText.EXTRA_TEXT[0];
    }
    public static String EnergyMsg() {
        return UIText.EXTRA_TEXT[1] + TrueRiskCap() + UIText.EXTRA_TEXT[2];
    }
    /** Text used in UI prompts */
    public static String DiscardText(){
        return UIText.EXTRA_TEXT[3];
    }
    public static String ExhaustText(){
        return UIText.EXTRA_TEXT[4];
    }
    public static String RecoverText(){
        return UIText.EXTRA_TEXT[5];
    }
    public static String ReturnText(){
        return UIText.EXTRA_TEXT[6];
    }
    public static String AddText(){
        return UIText.EXTRA_TEXT[7];
    }
    public static String DevourText(){
        return UIText.EXTRA_TEXT[8];
    }
    public static String ChooseACardToText(){
        return  ChooseACardToText(1);
    }
    public static String ChooseACardToText(int i){
        if(i == 1){
            return UIText.EXTRA_TEXT[9];
        }
        return UIText.EXTRA_TEXT[10] + i + UIText.EXTRA_TEXT[11];
    }
}
