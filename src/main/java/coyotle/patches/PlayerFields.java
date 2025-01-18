package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static coyotle.util.otherutil.variables.Variables.p;

@SpirePatch(
        clz= AbstractPlayer.class,
        method=SpirePatch.CLASS)
public class PlayerFields {
    public static SpireField<Integer> WindsingerEncounters = new SpireField<>(() -> 0);
    public static SpireField<Integer> MysticNextDraw = new SpireField<>(() -> 0);
    public static SpireField<Integer> MysticNextEnergy = new SpireField<>(() -> 0);
    public static SpireField<Boolean> WindsingerNeutral = new SpireField<>(() -> true);
    public static SpireField<Boolean> WindsingerFriend = new SpireField<>(() -> false);
    public static SpireField<Boolean> MetMystic = new SpireField<>(() -> false);

    public static void IncreaseEncounters(){
        WindsingerEncounters.set(p(), (WindsingerEncounters.get(p()) + 1));
    }
    public static int WindsingerEncounters(){
        return WindsingerEncounters.get(p());
    }

    public static void SetWindsingerFriend(boolean b){
        WindsingerNeutral.set(p(), false);
        WindsingerFriend.set(p(), b);
    }

    public static boolean WindsingerIsNeutral(){
        if(p() != null){
            return WindsingerNeutral.get(p());
        }
        return true;
    }
    public static boolean WindsingerIsFriend(){
        if(p() != null){
            return WindsingerFriend.get(p());
        }
        return false;
    }


    public static void AddMysticNextDraw(){
        if(p() != null){
            MysticNextDraw.set(p(), (MysticNextDraw.get(p()) + 1));
        }
    }
    public static void ClearMysticNextDraw(){
        if(p() != null){
            MysticNextDraw.set(p(), 0);
        }
    }
    public static int MysticNextDraw(){
        if(p() != null){
            return MysticNextDraw.get(p());
        }
        return 0;
    }


    public static void AddMysticNextEnergy(){
        if(p() != null){
            MysticNextEnergy.set(p(), (MysticNextEnergy.get(p()) + 1));
        }
    }
    public static void ClearMysticNextEnergy(){
        if(p() != null){
            MysticNextEnergy.set(p(), 0);
        }
    }
    public static int MysticNextEnergy(){
        if(p() != null){
            return MysticNextEnergy.get(p());
        }
        return 0;
    }

    public static void EncounterMystic(){
        if(p() != null){
            MetMystic.set(p(), true);
        }
    }
}
