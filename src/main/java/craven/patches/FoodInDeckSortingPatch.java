package craven.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static craven.util.CustomTags.NoUse;

@SpirePatch(
        clz = MasterDeckViewScreen.class,
        method = "updatePositions"

)

public class FoodInDeckSortingPatch {
    private static Comparator<AbstractCard> FoodCompare;

    @SpireInsertPatch(rloc = 233, localvars = {"cards"})
    public static void SinkRations(MasterDeckViewScreen ___instance, ArrayList<AbstractCard> cards) {
        ///Set our comparator - once and only once
        MakeComparator();
        ///If the comparator is set (just in case), then sort all "Blank Food" to the bottom
        if(FoodCompare != null){
            Collections.sort(cards, FoodCompare);
        }
        ///Update the temp deck to be our re-sorted list
        ReflectionHacks.setPrivate(___instance, MasterDeckViewScreen.class, "tmpSortedDeck", cards);
    }

    ///Set up the Comparator
    private static void MakeComparator(){
        if(FoodCompare == null){
            FoodCompare = new FoodComprator();
        }
    }

    private static class FoodComprator implements Comparator<AbstractCard> {
        @Override
        public int compare(AbstractCard o1, AbstractCard o2) {
            ///If the card we're testing doesn't have the tag and we do, put it above us
            if(o1.hasTag(NoUse) && !o2.hasTag(NoUse)){
                return 1;
            }
            ///If the card we're testing has the tag and we don't, put it below us
            if(o2.hasTag(NoUse) && !o1.hasTag(NoUse)){
                return -1;
            }

            ///Otherwise, we don't care - leave it as we found it
            return 0;
        }
    }
}

