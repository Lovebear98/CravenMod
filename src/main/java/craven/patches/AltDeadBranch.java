package craven.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.DeadBranch;
import craven.character.CravenCharacter;
import craven.relics.OliveBranch;

import static craven.util.otherutil.ConfigManager.BranchNerf;
import static craven.util.otherutil.Wiz.DevourEnabled;

public class AltDeadBranch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeRelicList"
    )
    public static class MoreAliveBranch
    {
        @SpirePrefixPatch
        public static void PrefixPatch(AbstractDungeon __instance)
        {
            if ((CardCrawlGame.chosenCharacter == CravenCharacter.Meta.CRAVEN_CHARACTER) || DevourEnabled())
                {
                    if(BranchNerf){
                        AbstractDungeon.relicsToRemoveOnStart.add(DeadBranch.ID);
                    }else{
                        AbstractDungeon.relicsToRemoveOnStart.add(OliveBranch.ID);
                    }

        }
        }
    }
}