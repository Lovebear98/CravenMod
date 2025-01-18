package pathmaker.util.CustomActions.CustomGameEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_X;
import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_Y;
import static pathmaker.util.otherutil.ConfigManager.EnableSounds;
import static pathmaker.util.otherutil.SoundManager.BLOOMSOUNDKEY;
import static pathmaker.util.otherutil.SoundManager.PlaySound;

public class FlowerSproutEffect extends AbstractGameEffect {
    private final boolean ActivateSound;
    float x = DRAW_PILE_X;
    float y = DRAW_PILE_Y;

    public FlowerSproutEffect(boolean b) {
        this.duration = this.startingDuration = 0.01f;
        ///0.5f
        this.ActivateSound = b;
    }

    public void update() {
        if(this.duration <= 0.0f){
            if(EnableSounds && ActivateSound){
                PlaySound(BLOOMSOUNDKEY,-0.3f, 0.3f);
            }

            int i;
            for(i = 0; i < 30; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new FlowerRiseEffect(this.x, this.y));
            }
            this.isDone = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
