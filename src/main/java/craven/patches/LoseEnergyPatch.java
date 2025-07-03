package craven.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static craven.util.otherutil.Wiz.*;
import static craven.util.otherutil.variables.Variables.p;

@SpirePatch(clz = AbstractPlayer.class, method = "loseEnergy")
    public class LoseEnergyPatch {
        @SpirePrefixPatch
        public static SpireReturn FixPrep(AbstractPlayer __instance, int e) {
            if (DevourEnabled()) {
                ///Get as many cards as is feasible up to our cost
                int Cards = Math.min(p().exhaustPile.size(), e);
                ///Devour that many cards if it's more than 0
                if(Cards > 0){
                    DevourCards(Cards);
                }
                ///Then check how much energy is left to pay
                int Ravenous = e - Cards;
                ///And suffer that much Ravenous if it's more than 0
                if(Ravenous > 0){
                    GainRavenous(Ravenous);
                }


                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }