package craven.patches.restbuttons;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import craven.relics.FoundFood;
import craven.util.CustomActions.CustomGameEffects.ScavengeEffect;
import craven.util.TextureLoader;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.ScavengeCount;
import static craven.util.otherutil.variables.Variables.p;


public class ScavengeButton extends AbstractCampfireOption {

    public static final String[] TEXT;
    private static final UIStrings UI_STRINGS;
    private float WaitTimer = 0.3f;
    /** Any reusable campfire actions we make will set this to be free */
    private boolean Used = false;

    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString(makeID(ScavengeButton.class.getSimpleName()));
        TEXT = UI_STRINGS.TEXT;
    }


    ///This builds the button for us to use, taking in whether it should be active or not (which your patch determines)
    public ScavengeButton(boolean active) {
        this.label = TEXT[0];

        this.usable = active;
        if (active) {
            this.description = TEXT[1]+ ScavengeCount() +TEXT[2];
            this.img = TextureLoader.getTexture("craven/images/ui/ScavengeButton.png");
        } else {
            this.description = TEXT[3];
            this.img = TextureLoader.getTexture("craven/images/ui/GScavengeButton.png");
        }
    }

    ///When we click the button
    @Override
    public void useOption() {
        if (this.usable) {
            Used = true;
            AbstractDungeon.effectsQueue.add(new ScavengeEffect(ScavengeCount()));

        }
    }


    @Override
    public void update() {
        if(Used){
            this.img = TextureLoader.getTexture("craven/images/ui/GScavengeButton.png");
            this.usable = false;
            this.description = TEXT[3];
        }

        float hackScale = (float) ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");
        if (this.hb.hovered) {
            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
        }
        super.update();
    }
}