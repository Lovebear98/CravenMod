package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import coyotle.cards.generated.Mechanics.TheDream;
import coyotle.util.CustomActions.EnterDreamCheckAction;
import coyotle.util.CustomActions.ReplaceShardAction;

public class PostSeekPatch {

    @SpirePatch(
            clz = BetterDrawPileToHandAction.class,
            method = "update"
    )
    public static class SeekDream {

        @SpirePostfixPatch
        public static void SeekCheck(BetterDrawPileToHandAction ___instance) {
            if(___instance.isDone){
                AbstractDungeon.actionManager.addToTop(new ReplaceShardAction());
                AbstractDungeon.actionManager.addToBottom(new EnterDreamCheckAction(new TheDream()));
            }
        }
    }
}
