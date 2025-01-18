package coyotle.relics;

import basemod.helpers.CardPowerTip;
import coyotle.cards.AbstractShardCard;
import coyotle.cards.generated.Mechanics.HowlingBluegrass;
import coyotle.character.CoyotleCharacter;

import static coyotle.CoyotleMod.makeID;

public class BrightmoonBrave extends CoyotleRelic {
    ///This relic doesn't do anything here. It's checked for in the ShardManager.

    private static final String NAME = BrightmoonBrave.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.MAGICAL;
    public BrightmoonBrave() {
        super(ID, NAME, CoyotleCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        AbstractShardCard c = new HowlingBluegrass();
        this.tips.add(new CardPowerTip(c));
    }




    @Override
    public void atBattleStart() {
        super.atBattleStart();
        unusedUp();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        unusedUp();
    }


    @Override
    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;
    }

    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
