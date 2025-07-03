package craven.patches.startexhausted;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
//Thank you, Jorbs/Wanderer, I couldn't figure out how to do this without
// causing a concurrent exception or cloning the cards...
@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class StartExhaustedField {

    public static SpireField<Boolean> startexhausted = new SpireField(() -> false);


}
