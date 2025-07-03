package craven.patches.startexhausted;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import static craven.util.CustomTags.Food;

public class StartExhaustedPatch {
    public static boolean isEntombed(AbstractCard card) {
        return card.hasTag(Food);
    }

    // Remove Entombed cards from draw pile
    @SpirePatch(
            clz = CardGroup.class,
            method = "initializeDeck"
    )
    public static class CardGroup_initializeDeck {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall methodCall) throws CannotCompileException {
                    if (methodCall.getClassName().equals(CardGroup.class.getName()) && methodCall.getMethodName().equals("addToTop")) {
                        methodCall.replace(String.format("{ if (!%1$s.isEntombed($1)) { $_ = $proceed($$); } }", StartExhaustedPatch.class.getName()));
                    }
                }
            };
        }
    }

    // Add Entombed cards to exhaust pile
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class AbstractPlayer_preBattlePrep {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractPlayer __this) {
            for (AbstractCard c : __this.masterDeck.group) {
                if (isEntombed(c)) {
                    if (c instanceof StartInExhaustSubscriber) {
                        ((StartInExhaustSubscriber) c).onCardEntombed();
                    }
                    __this.exhaustPile.addToTop(c.makeSameInstanceOf());
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "isEndingTurn");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
}
