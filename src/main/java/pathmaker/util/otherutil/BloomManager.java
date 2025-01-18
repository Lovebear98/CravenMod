package pathmaker.util.otherutil;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import pathmaker.cards.status.GrowthCard;
import pathmaker.relics.DesertNectar;
import pathmaker.ui.BloomPanel;
import pathmaker.util.CustomActions.GrowthAction;
import pathmaker.util.CustomActions.ResolveBloomAction;

import static pathmaker.ui.BloomPanel.FixColor;
import static pathmaker.util.CustomTags.Barren;
import static pathmaker.util.otherutil.ConfigManager.EnableSounds;
import static pathmaker.util.otherutil.SoundManager.*;
import static pathmaker.util.otherutil.variables.Variables.ShouldBloom;
import static pathmaker.util.otherutil.variables.Variables.p;

public class BloomManager {

    ///Our base Bloom Count, and where it starts
    public static int BloomCount = 1;

    ///We only trigger when EXCEEDING the cap, to make us do all 6 steps
    public static int BloomCap(){
        return 6;
    }
    ///Is our next card going to Bloom?
    public static boolean BloomReady(){
        return BloomCount >= (BloomCap());
    }
    ///Did we play a card this combat to Grow?
    public static boolean GrowthMode = false;
    ///The number we go back to each time we Bloom
    public static int BloomResetNum(){
        return 1;
    }

    ///Attempt a generic Bloom with a card
    public static void AttemptBloom (AbstractCard c){
        AttemptBloom(c, 1);
    }

    ///Attempt a Bloom for more than 1 with a card
    public static void AttemptBloom (AbstractCard c, int i){
        if(ShouldBloom()){
            ///Bloom by the given amount
            BloomCount += i;

            ///Then if we overcapped
            if(BloomCount > BloomCap()){
                ///If it was via Growth and we DON'T have the relic that makes it count
                ///OR if the card has the Barren tag
                if((c instanceof GrowthCard && !p().hasRelic(DesertNectar.ID)) || c.hasTag(Barren)){
                    ///Just push it back to match the cap
                    BloomCount = BloomCap();
                    ///otherwise
                }else{
                    if(p().hasRelic(DesertNectar.ID)){
                        AbstractRelic r = p().getRelic(DesertNectar.ID);
                        r.flash();
                    }
                    ///Reset
                    BloomCount = BloomResetNum();
                    ///Get a new color
                    FixColor();
                    ///And resolve the Bloom
                    AbstractDungeon.actionManager.addToBottom(new ResolveBloomAction(c));
                }
            }
        }
    }




    ///Pretend we played a blank card
    public static void Growth(int i){
        ///SOUND!
        if(EnableSounds){
            PlaySound(GROWSOUNDKEY, -0.2f, 0.2f);
        }
        ///Do them in separate instances so things can 'tick' internally
        for(int e = i; e >0; e -= 1){
            AbstractDungeon.actionManager.addToBottom(new GrowthAction());
        }
    }

    ///ResetBloom
    public static void ResetBloom(){
        ///Reset the Bloom Number
        BloomCount = BloomResetNum();

        ///Move th Bloom Panel back fof-screen so it slides in
        BloomPanel.SnapBloomPanelSpot();

        ///At the end or start of combat, we turn off Bloom Mode again
        GrowthMode = false;
    }
}
