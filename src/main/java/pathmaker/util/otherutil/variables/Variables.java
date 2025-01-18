package pathmaker.util.otherutil.variables;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import pathmaker.character.PathmakerCharacter;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.BloomManager.BloomCount;
import static pathmaker.util.otherutil.BloomManager.GrowthMode;
import static pathmaker.util.otherutil.ConfigManager.AllBloom;

public class Variables {

    public static final String SecondMagicKey = makeID("M2");

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
            s.append(" [E]");
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

    public static boolean ShouldBloom(){
        if(p() != null){
            ///If we're the druid, All Bloom is on, we've caused a Growth, or we've already grown, enable
            return p() instanceof PathmakerCharacter || AllBloom || GrowthMode ||BloomCount > 1;
        }
        return AllBloom || GrowthMode || BloomCount > 1;
    }
}
