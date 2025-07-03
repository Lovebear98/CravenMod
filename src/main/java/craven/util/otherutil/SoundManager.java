package craven.util.otherutil;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static craven.CravenMod.makeID;

public class SoundManager {

    public static final String GLASSSOUNDKEY = makeID("GLASSSOUNDKEY");


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
