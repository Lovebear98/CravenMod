package pathmaker.orbs;


import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static pathmaker.util.otherutil.SoundManager.*;

public abstract class AbstractFlowerOrb extends CustomOrb {


    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    protected float vfxTimer = 1.0f;
    protected float vfxIntervalMin = 1.25f;
    protected float vfxIntervalMax = 2.25f;

    public AbstractCard.CardType type;

    public AbstractFlowerOrb(String orbId, String NAME, int passiveAmount, int evokeAmount, String description, String s, String orbPath) {
        super(orbId, NAME, passiveAmount, evokeAmount, description, s, orbPath);

        updateDescription();

        ///The starting angle
        angle = MathUtils.random(360.0f);
        ///And the time the channel animation takes
        channelAnimTimer = 0.5f;

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void updateDescription() {
        applyFocus();
        this.description = OrbDesc();
    }

    @Override
    public void applyFocus() {
        super.applyFocus();

        ///Lock the amount, they don't do anything but replace costs
        evokeAmount = baseEvokeAmount;
        passiveAmount = basePassiveAmount;
    }




    @Override
    public void updateAnimation() {
        super.updateAnimation();
        ///Rotate based on time
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        ///Tick down the timer
        vfxTimer -= Gdx.graphics.getDeltaTime();
        ///If the timer ticked down
        if (vfxTimer < 0.0f) {
            ///Create a new effect

            ///???

            ///And reset the timer semi-randomly
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    @Override
    protected void renderText(SpriteBatch sb) {
    }

    abstract String OrbDesc();

    @Override
    public void triggerEvokeAnimation() {
    }

    @Override
    public void playChannelSFX() {
        PlaySound(FLOWERSHANNELSOUNDKEY, -0.2F, 0.2F);
    }



    @Override
    public void onEvoke() {
        PlaySound(FLOWEREVOKESOUNDKEY, -0.2F, 0.2F);
    }

}
