package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import coyotle.util.CustomActions.ReplaceShardAction;

import static coyotle.util.otherutil.Wiz.ValidForShards;

public class RelocateShardPatch {

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="onCardDrawOrDiscard"
    )
    public static class ShardMake {
        @SpirePostfixPatch
        public static void PermanentShard() {
            if(ValidForShards()){
                AbstractDungeon.actionManager.addToTop(new ReplaceShardAction());
            }
        }
    }
}
