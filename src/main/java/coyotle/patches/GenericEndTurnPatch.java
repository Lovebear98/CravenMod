package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import coyotle.CoyotleMod;

public class GenericEndTurnPatch {

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "applyEndOfTurnTriggers"
    )
    public static class EndTurn {
        @SpirePostfixPatch
        public static void WispMurder(AbstractCreature ___instance) {
            ///This needs to happen BEFORE discarding cards
            if(___instance instanceof AbstractPlayer){
                CoyotleMod.receiveOnPlayerTurnEnd();
            }
        }
    }
}
