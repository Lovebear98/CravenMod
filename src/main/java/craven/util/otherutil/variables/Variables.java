package craven.util.otherutil.variables;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static craven.CravenMod.makeID;

public class Variables {

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(Variables.class.getSimpleName()));

    public static final String SecondMagicKey = makeID("M2");
    public static final String SpecialVarKey = makeID("SV");
    public static final String SecondsCountKey = makeID("S");
    public static String OrbPath(String text){
        return "craven/images/orbs/" + text + ".png";
    }


    /** Simple, Common-Use Variables */
    public static AbstractPlayer p(){
        return AbstractDungeon.player;
    }
    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
    public static boolean MonsterIsAlone(AbstractMonster mo){
        if(mo != null && isInCombat()){
            for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                if(m != mo){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    /** Random Boolean */
    public static boolean RandomBoolean(){
        if(p() != null){
            return AbstractDungeon.miscRng.random(0, 1) == 1;
        }
        return MathUtils.random(0, 1) == 1;
    }
    /** Specialized Text */
    public static String PrintEnergy(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e-= 1){
            s.append(uiStrings.TEXT[0]);
        }
        return s.toString();
    }
}
