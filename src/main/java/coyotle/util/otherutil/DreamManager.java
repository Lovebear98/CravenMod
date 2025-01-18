package coyotle.util.otherutil;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import coyotle.cards.generated.Mechanics.TheDream;
import coyotle.patches.interfaces.DreamInterface;
import coyotle.util.CustomActions.CustomGameEffects.vfx.DreamSmokeEffect;
import coyotle.util.CustomActions.CustomGameEffects.vfx.FancyDreamSmokeEffect;

import static coyotle.util.otherutil.ConfigManager.EnableEarCandy;
import static coyotle.util.otherutil.ConfigManager.EnableEyeCandy;
import static coyotle.util.otherutil.SoundManager.*;
import static coyotle.util.otherutil.Wiz.ValidForDream;
import static coyotle.util.otherutil.variables.Variables.p;

public class DreamManager {

    public static boolean inDream = false;
    public static int DreamTimer = 0;
    public static int DreamTimerCap = 15;

    public static void EnterDream(){
        EnterDream(new TheDream());
    }

    public static void EnterDream(AbstractCard card){
        if(!inDream){
            if(p() != null && p().drawPile.isEmpty()){
                setDream(true);
            }
        }
    }
    public static void ExitDream(){
        if(inDream){
            if(p() != null && !p().hand.isEmpty()){
                setDream(false);
            }
        }
    }


    public static void AdminEnterDream(){
            setDream(true);
    }
    public static void AdminExitDream(){
            setDream(false);
    }

    public static void setDream(boolean b) {
        if(!ValidForDream()){
            inDream = false;
            return;
        }
            boolean StartDream = inDream;
            inDream = b;
            if(!b && (StartDream || Settings.isDebug)){
                DreamTimer = 0;
                ExitDreamTrigger();
            }else if(b && (!StartDream || Settings.isDebug)){
                if(EnableEarCandy){
                    PlaySound(ENTERDREAMSOUNDKEY);
                }
                EnterDreamTrigger();
            }
    }

    public static void renderInDream(){
        if(EnableEyeCandy){
            if(inDream){
                if(DreamTimer >= DreamTimerCap){
                    DreamTimer = 0;
                    AbstractDungeon.effectsQueue.add(new DreamSmokeEffect());
                    AbstractDungeon.effectsQueue.add(new FancyDreamSmokeEffect());
                }else{
                    DreamTimer += 1;
                }
            }
        }
    }



    private static void EnterDreamTrigger() {
        BeginDreamAudioLoop();
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c instanceof DreamInterface){
                ((DreamInterface) c).onEnterDream();
            }
        }
        for(AbstractCard c: AbstractDungeon.player.drawPile.group){
            if(c instanceof DreamInterface){
                ((DreamInterface) c).onEnterDream();
            }
        }
        for(AbstractCard c: AbstractDungeon.player.discardPile.group){
            if(c instanceof DreamInterface){
                ((DreamInterface) c).onEnterDream();
            }
        }

        for(AbstractRelic r: AbstractDungeon.player.relics){
            if(r instanceof DreamInterface){
                ((DreamInterface) r).onEnterDream();
            }
        }

        for(AbstractPower p: AbstractDungeon.player.powers){
            if(p instanceof DreamInterface){
                ((DreamInterface) p).onEnterDream();
            }
        }
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            for(AbstractPower p: m.powers){
                if(p instanceof DreamInterface){
                    ((DreamInterface) p).onEnterDream();
                }
            }
        }
    }

    private static void ExitDreamTrigger() {
        EndDreamAudioLoop();
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c instanceof DreamInterface){
                ((DreamInterface) c).onExitDream();
            }
        }
        for(AbstractCard c: AbstractDungeon.player.drawPile.group){
            if(c instanceof DreamInterface){
                ((DreamInterface) c).onExitDream();
            }
        }
        for(AbstractCard c: AbstractDungeon.player.discardPile.group){
            if(c instanceof DreamInterface){
                ((DreamInterface) c).onExitDream();
            }
        }

        for(AbstractRelic r: AbstractDungeon.player.relics){
            if(r instanceof DreamInterface){
                ((DreamInterface) r).onExitDream();
            }
        }

        for(AbstractPower p: AbstractDungeon.player.powers){
            if(p instanceof DreamInterface){
                ((DreamInterface) p).onExitDream();
            }
        }
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            for(AbstractPower p: m.powers){
                if(p instanceof DreamInterface){
                    ((DreamInterface) p).onExitDream();
                }
            }
        }
    }
}
