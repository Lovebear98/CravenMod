package craven.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import craven.util.CustomActions.cardmanip.EndStartingHandAction;

import static craven.util.otherutil.Wiz.DrawingStartingHand;

public class StartingHandPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnPostDrawRelics"
    )
    public static class HungryFix {
        public static void Prefix(AbstractPlayer __instance) {
            DrawingStartingHand = true;
            AbstractDungeon.actionManager.addToBottom(new EndStartingHandAction());
            }

        }
    }
