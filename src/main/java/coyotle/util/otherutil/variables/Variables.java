package coyotle.util.otherutil.variables;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static coyotle.CoyotleMod.makeID;

public class Variables {

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(Variables.class.getSimpleName()));

    public static float VFXDuration(){
        if(Settings.FAST_MODE){
            return 0.1f;
        }else{
            return 0.4f;
        }
    }


    public static final String SecondMagicKey = makeID("M2");
    public static final String DiscardSizeKey = makeID("DiS");

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

    public static String PrintEnergy(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e-= 1){
            s.append(uiStrings.TEXT[0]);
        }
        return s.toString();
    }
    public static String PrintBlood(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e-= 1){
            s.append(uiStrings.TEXT[1]);
        }
        return s.toString();
    }
    public static String PrintDiamond(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e-= 1){
            s.append(uiStrings.TEXT[2]);
        }
        return s.toString();
    }

    public static String PrintRuby(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e-= 1){
            s.append(uiStrings.TEXT[3]);
        }
        return s.toString();
    }

    public static String PrintSapphire(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e-= 1){
            s.append(uiStrings.TEXT[4]);
        }
        return s.toString();
    }

    public static String PrintWild(int i){
        StringBuilder s = new StringBuilder();
        for(int e = i; e > 0; e-= 1){
            s.append(uiStrings.TEXT[5]);
        }
        return s.toString();
    }


    public static boolean InHand(AbstractCard c){
        return p().hand.contains(c);
    }

    public static AbstractPlayer p(){
        return AbstractDungeon.player;
    }

    public static boolean RandomBoolean(){
        if(p() != null){
            return AbstractDungeon.miscRng.random(0, 1) == 1;
        }
        return MathUtils.random(0, 1) == 1;
    }

    public static int DiscardPileSizeNum(){
        if(p() != null && isInCombat()){
            return p().discardPile.size();
        }
        return 0;
    }
}
