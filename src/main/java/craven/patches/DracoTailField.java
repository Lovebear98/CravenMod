package craven.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class DracoTailField {

    public static SpireField<Boolean> inDracoTailField = new SpireField<>(() -> false);

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {

        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {

            inDracoTailField.set(result, inDracoTailField.get(self));
            return result;
        }
    }
}