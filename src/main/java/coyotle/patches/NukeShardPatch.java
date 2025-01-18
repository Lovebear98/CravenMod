///package prophecy.patches;
///
///import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
///import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
///import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
///import com.megacrit.cardcrawl.cards.AbstractCard;
///import com.megacrit.cardcrawl.cards.CardGroup;
///import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
///import prophecy.cards.AbstractShardCard;
///import prophecy.util.CustomActions.ReplaceShardAction;
///
///import static prophecy.util.otherutil.variables.Variables.p;
///
///public class NukeShardPatch {
///
///    @SpirePatch(
///            clz= CardGroup.class,
///            method="moveToDiscardPile"
///    )
///    public static class ShardMurder {
///        @SpirePrefixPatch
///        public static SpireReturn NukeShard(CardGroup ___instance, AbstractCard c) {
///            if(c instanceof AbstractShardCard){
///                p().drawPile.removeCard(c);
///                p().hand.removeCard(c);
///                return SpireReturn.Return();
///            }
///
///            AbstractDungeon.actionManager.addToBottom(new ReplaceShardAction());
///            return SpireReturn.Continue();
///        }
///    }
///}
