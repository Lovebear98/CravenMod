package pathmaker.orbs;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.variables.GeneralUtils.OrbPath;

public class AttackFlower extends AbstractFlowerOrb {

    //Standard ID/Description
    public static final String ORB_ID = makeID(AttackFlower.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    ///Orb amounts, they don't matter since the orb won't use numbers.
    private static final int PASSIVE_AMOUNT = 0;
    private static final int EVOKE_AMOUNT = 0;

    public AttackFlower() {
        super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTIONS[0], DESCRIPTIONS[0], OrbPath(AttackFlower.class.getSimpleName()));

        updateDescription();

        ///The starting angle
        angle = MathUtils.random(360.0f);
        ///And the time the channel animation takes
        channelAnimTimer = 0.5f;

        this.type = AbstractCard.CardType.ATTACK;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = OrbDesc();
    }

    @Override
    public void applyFocus() {
        super.applyFocus();
    }




    @Override
    public void updateAnimation() {
        super.updateAnimation();

    }

    @Override
    String OrbDesc() {
        return orbString.DESCRIPTION[0];
    }


    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation();
    }
    @Override
    public void playChannelSFX() {
        super.playChannelSFX();
    }
    @Override
    public void onEvoke() {
        super.onEvoke();
    }


    @Override
    public AbstractOrb makeCopy() {
        return new AttackFlower();
    }
}
