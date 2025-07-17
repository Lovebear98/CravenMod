package craven.relics;

import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;

public class Ribbon extends AtlasRelic {

    private static final String NAME = Ribbon.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.MAGICAL;
    public static final int RibbonRisKAmount = 2;

    public Ribbon() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        fixDescription();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + RibbonRisKAmount + DESCRIPTIONS[1];
    }
}
