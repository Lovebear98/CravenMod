package coyotle.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Consume;
import com.megacrit.cardcrawl.cards.blue.Darkness;
import com.megacrit.cardcrawl.cards.blue.Recursion;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.cards.green.DodgeAndRoll;
import com.megacrit.cardcrawl.cards.green.LegSweep;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.cards.purple.InnerPeace;
import com.megacrit.cardcrawl.cards.purple.LikeWater;
import com.megacrit.cardcrawl.cards.red.Bloodletting;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.cards.red.Rupture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import coyotle.cards.attack.HowlingPlainsAlpha;
import coyotle.cards.attack.SoaringTalon;
import coyotle.cards.skill.AutumnSpiritualist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static coyotle.util.otherutil.variables.Variables.p;

public class SynergisticCardBundle {

    private static final int BaseBundleSize = 3;
    private static String ValorTagKey = "BundleValorKey";
    public final ArrayList<AbstractCard> cardlist;
    public SynergisticCardBundle(int i){
        this(GenerateCards(p().chosenClass, i));
    }

    public SynergisticCardBundle(){
        this(GenerateCards(p().chosenClass, BaseBundleSize));
    }
    public SynergisticCardBundle(AbstractCard...cards){
        this(ListToArrayList(cards));
    }

    public SynergisticCardBundle(ArrayList<AbstractCard> cards){
        this.cardlist = cards;
    }

    public SynergisticCardBundle(String tag){
        this(GenerateTagCards(tag, BaseBundleSize));
    }
    public SynergisticCardBundle(String tag, int i){
        this(GenerateTagCards(tag, i));
    }





    private static ArrayList<AbstractCard> ListToArrayList(AbstractCard[] cards) {
        ArrayList<AbstractCard> tmp = new ArrayList<>();
        Collections.addAll(tmp, cards);
        return tmp;
    }
    private static ArrayList<AbstractCard> GenerateCards(AbstractPlayer.PlayerClass pClass, int i) {
        ArrayList<AbstractCard> tmp = new ArrayList<>();


        if(pClass == AbstractPlayer.PlayerClass.IRONCLAD){

            for(int x = i; i > 0; i -= 3){
                if(i >= 1){
                    tmp.add(new HeavyBlade());
                }
                if(i >= 2){
                    tmp.add(new Bloodletting());
                }
                if(i >= 3){
                    tmp.add(new Rupture());
                }
            }

            return tmp;
        }
        if(pClass == AbstractPlayer.PlayerClass.THE_SILENT){

            for(int x = i; i > 0; i -= 3){
                if(i >= 1){
                    tmp.add(new DodgeAndRoll());
                }
                if(i >= 2){
                    tmp.add(new Blur());
                }
                if(i >= 3){
                    tmp.add(new LegSweep());
                }
            }

            return tmp;
        }
        if(pClass == AbstractPlayer.PlayerClass.DEFECT){

            for(int x = i; i > 0; i -= 3){
                if(i >= 1){
                    tmp.add(new Recursion());
                }
                if(i >= 2){
                    tmp.add(new Darkness());
                }
                if(i >= 3){
                    tmp.add(new Consume());
                }
            }

            return tmp;
        }
        if(pClass == AbstractPlayer.PlayerClass.WATCHER){


            for(int x = i; i > 0; i -= 3){
                if(i >= 1){
                    tmp.add(new EmptyFist());
                }
                if(i >= 2){
                    tmp.add(new InnerPeace());
                }
                if(i >= 3){
                    tmp.add(new LikeWater());
                }
            }

            return tmp;
        }

        for(int x = i; i > 0; i -= 3){
            if(i >= 1){
                tmp.add(new AutumnSpiritualist());
            }
            if(i >= 2){
                tmp.add(new SoaringTalon());
            }
            if(i >= 3){
                tmp.add(new HowlingPlainsAlpha());
            }
        }

        if(tmp.isEmpty()){
            tmp.add(new Madness());
        }
        return tmp;
    }

    private static ArrayList<AbstractCard> GenerateTagCards(String tag, int i){
        ArrayList<AbstractCard> tmp = new ArrayList<>();


        if(Objects.equals(tag, ValorTagKey)){

            for(int x = i; i > 0; i -= 3){
                if(i >= 1){
                    tmp.add(new AutumnSpiritualist());
                }
                if(i >= 2){
                    tmp.add(new SoaringTalon());
                }
                if(i >= 3){
                    tmp.add(new HowlingPlainsAlpha());
                }
            }

            return tmp;
        }

        for(int x = i; i > 0; i -= 3){
            if(i >= 1){
                tmp.add(new AutumnSpiritualist());
            }
            if(i >= 2){
                tmp.add(new SoaringTalon());
            }
            if(i >= 3){
                tmp.add(new HowlingPlainsAlpha());
            }
        }

        if(tmp.isEmpty()){
            tmp.add(new Madness());
        }
        return tmp;
    }






    public void obtain(){
        int Size = cardlist.size();
        int Spot = 0;


        ///f((Spot * (float) Settings.WIDTH) / Size)
        for(AbstractCard c: cardlist){
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, PositionX(Spot, Size), (float) Settings.HEIGHT/2.0F));
            Spot += 1;
        }
    }


    private float PositionX(int Spot, int Size){
        return (float) (Settings.WIDTH * 0.15 + (Spot * (Settings.WIDTH * 0.85 - Settings.WIDTH * 0.15) / (Size - 1)));
    }

}
