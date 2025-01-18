package pathmaker.util.otherutil.variables;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;

public class GeneralUtils {

    public static void FlashCard(AbstractCard card, Color color){
        if(AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)){
            AbstractDungeon.topLevelEffectsQueue.add(new CardFlashVfx(card, color));
        }
    }


    public static String OrbPath(String text){
        return "pathmaker/images/orbs/" + text + ".png";
    }

    public static Color OasisColor(){
        return Color.GOLDENROD.cpy();
    }
}
