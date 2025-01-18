package pathmaker.util.otherutil.variables;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static pathmaker.PathmakerMod.makeID;

public class UIText {
    public static UIStrings UIText = CardCrawlGame.languagePack.getUIString(makeID(UIText.class.getSimpleName()));


    public static String SHOWTUTORIALTEXT(){
        return UIText.TEXT[0];
    }
    public static String ENABLESOUNDSTEXT(){
        return UIText.TEXT[1];
    }
    public static String ALTUITEXT(){
        return UIText.TEXT[2];
    }
    public static String ALLBLOOMTEXT(){
        return UIText.TEXT[3];
    }

    public static String Discardtext(){
        return UIText.EXTRA_TEXT[0];
    }
    public static String Exhausttext(){
        return UIText.EXTRA_TEXT[1];
    }
    public static String Growtext(){
        return UIText.EXTRA_TEXT[2];
    }

    public static String Recovertext(){
        return UIText.EXTRA_TEXT[3];
    }
}
