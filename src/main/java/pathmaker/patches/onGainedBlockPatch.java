package pathmaker.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import pathmaker.patches.interfaces.EnemyGainBlockPower;

import java.util.Iterator;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "addBlock",
        paramtypez={
                int.class,
        }

)
// A patch from Koishi that allows checking for on-enemy-block powers
public class onGainedBlockPatch {
    @SpireInsertPatch(locator = onGainedBlockPatch.Locator.class, localvars = {"tmp"})
    public static void TriggerOnGainedBlock(AbstractCreature instance, int blockAmount, @ByRef float[] tmp) {
        if (!instance.isPlayer) {
            if (tmp[0] > 0.0F) {
                Iterator iterator = instance.powers.iterator();
                while(iterator.hasNext()) {
                    AbstractPower p = (AbstractPower)iterator.next();
                    if (p instanceof EnemyGainBlockPower) {
                        tmp[0] = ((EnemyGainBlockPower)p).onThisEnemyBlock(blockAmount);
                    }
                }
            }
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}