package coyotle.util.otherutil;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.ConfigManager.EnableEarCandy;

public class SoundManager {

    public static final String COYOTLESHARDSOUNDKEY = makeID("DREAMSOUNDKEY");
    public static final String BLOODSHARDSOUNDKEY = makeID("BLOODSHARDSOUNDKEY");
    public static final String DIAMONDSHARDSOUNDKEY = makeID("DIAMONDSHARDSOUNDKEY");
    public static final String RUBYSHARDSOUNDKEY = makeID("RUBYSHARDSOUNDKEY");
    public static final String SAPPHIRESHARDSOUNDKEY = makeID("SAPPHIRESHARDSOUNDKEY");
    public static final String WILDSHARDSOUNDKEY = makeID("WILDSHARDSOUNDKEY");
    public static final String FATESHARDSOUNDKEY = makeID("FATESHARDSOUNDKEY");
    public static final String PROPHECYSOUNDKEY = makeID("PROPHECYSOUNDKEY");
    public static final String ENTERDREAMSOUNDKEY = makeID("ENTERDREAMSOUNDKEY");
    public static final String DREAMLOOPSOUNDKEY = makeID("DREAMLOOPSOUNDKEY");
    public static final String GALAXYATTACKKEY = makeID("GALAXYATTACKKEY");
    public static final String SMOKEATTACKKEY = makeID("SMOKEATTACKKEY");
    public static final String VALORSOUNDKEY = makeID("VALORSOUNDKEY");

    public static void PlaySound(String key, Float pitch){
        CardCrawlGame.sound.play(key, pitch);
    }
    public static void PlaySound(String key, Float pitchmin, Float pitchmax){
        PlaySound(key, MathUtils.random(pitchmin, pitchmax));
    }

    public static void PlaySound(String key){
        CardCrawlGame.sound.play(key);
    }





    private static long sfxId;

    private static int DreamAudioTimer = 0;
    private static final int DreamAudioTimerBreak = 60;
    public static void BeginDreamAudioLoop(){
        if(EnableEarCandy){
            if (sfxId != -1L) {// 52
                EndDreamAudioLoop();
            }

            MusicMaster m = CardCrawlGame.music;
            if(m != null){
                m.silenceBGM();
            }
            sfxId = CardCrawlGame.sound.playAndLoop(DREAMLOOPSOUNDKEY);
        }
    }

    public static void EndDreamAudioLoop() {
        if(EnableEarCandy){
            if (sfxId != -1L) {
                CardCrawlGame.sound.fadeOut(DREAMLOOPSOUNDKEY, sfxId);
                MusicMaster m = CardCrawlGame.music;
                if(m != null){
                    m.unsilenceBGM();
                }
                sfxId = -1L;
            }
        }
    }





    public static void CutDreamAudioLoop(){
        if (sfxId != -1L) {
            CardCrawlGame.sound.fadeOut(DREAMLOOPSOUNDKEY, sfxId);
            sfxId = -1L;
        }
    }
}
