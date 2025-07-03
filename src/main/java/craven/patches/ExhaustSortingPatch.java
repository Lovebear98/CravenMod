package craven.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;

import static craven.util.otherutil.Wiz.DevourEnabled;

@SpirePatch(
        clz = ExhaustPileViewScreen.class,
        method = "open"
)

public class ExhaustSortingPatch {
    ///Thank you StSMariMod!
    ///https://github.com/iryan0702/StSMariMod/blob/c322864487f82c16e96bc9fd4d3a05c8f691a406/src/main/java/mari_mod/patches/MariExhaustPileViewOrderPatch.java
    private static CardGroup orderedGroup;

    @SpirePostfixPatch
    public static void ShowInOrder(ExhaustPileViewScreen ___instance) {
        if(DevourEnabled()){

            if(orderedGroup == null){
                orderedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            }
            orderedGroup.clear();
            for(AbstractCard c: AbstractDungeon.player.exhaustPile.group){
                AbstractCard toAdd = c.makeStatEquivalentCopy();
                toAdd.setAngle(0.0F, true);
                toAdd.targetDrawScale = 0.75F;
                toAdd.drawScale = 0.75F;
                toAdd.current_x = Settings.WIDTH/2f;
                toAdd.current_y = Settings.HEIGHT/2f;
                toAdd.lighten(true);
                orderedGroup.addToTop(toAdd);
            }

            ///Update the temp deck to be our re-sorted list
            ReflectionHacks.setPrivate(___instance, ExhaustPileViewScreen.class, "exhaustPileCopy", orderedGroup);
        }
    }
}

