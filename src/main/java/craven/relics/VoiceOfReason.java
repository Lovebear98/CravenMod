package craven.relics;

import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;

public class VoiceOfReason extends AtlasRelic {

    /// This relic does nothing on its own, and is instead checked for in the Ravenous Power
    private static final String NAME = VoiceOfReason.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.SOLID;

    public VoiceOfReason() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        fixDescription();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
