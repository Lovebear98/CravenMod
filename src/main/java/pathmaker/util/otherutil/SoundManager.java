package pathmaker.util.otherutil;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static pathmaker.PathmakerMod.makeID;

public class SoundManager {

    public static final String BLOOMSOUNDKEY = makeID("BLOOMSOUNDKEY");
    public static final String OASISREACHEDKEY = makeID("OASISDRAWSOUNDKEY");
    public static final String OASISDRAWKEY = makeID("OASISPLAYABLESOUNDKEY");
    public static final String FLOWERSHANNELSOUNDKEY = makeID("FLOWERSHANNELSOUNDKEY");
    public static final String FLOWEREVOKESOUNDKEY = makeID("FLOWEREVOKESOUNDKEY");
    public static final String GROWSOUNDKEY = makeID("GROWSOUNDKEY");

    public static void PlaySound(String key, Float pitch){
        CardCrawlGame.sound.play(key, pitch);
    }
    public static void PlaySound(String key, Float pitchmin, Float pitchmax){
        PlaySound(key, MathUtils.random(pitchmin, pitchmax));
    }

    public static void PlaySound(String key){
        CardCrawlGame.sound.play(key);
    }
}
