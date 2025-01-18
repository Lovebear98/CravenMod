package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import coyotle.cards.AbstractShardCard;
import coyotle.util.CustomActions.CreateShardAction;
import coyotle.util.CustomActions.ShardsToBottomAction;

import static coyotle.util.otherutil.Wiz.ValidForShards;
import static coyotle.util.otherutil.variables.Variables.p;

public class GenerateShardPatch {

    @SpirePatch(
            clz= EmptyDeckShuffleAction.class,
            method="update"
    )
    public static class WispMurder {
        @SpirePostfixPatch
        public static void WispMurder(EmptyDeckShuffleAction ___instance) {
                if(___instance.isDone){
                    if(ValidForShards()){
                        boolean MakeShard = true;
                        for(AbstractCard c: p().drawPile.group){
                            if(c instanceof AbstractShardCard){
                                MakeShard = false;
                                break;
                            }
                        }
                        if(MakeShard){
                            AbstractDungeon.actionManager.addToTop(new CreateShardAction());
                        }else{
                            AbstractDungeon.actionManager.addToTop(new ShardsToBottomAction());
                        }
                    }
                }
        }
    }
}
