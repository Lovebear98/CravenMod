package pathmaker.relics;

import pathmaker.character.PathmakerCharacter;

import static pathmaker.PathmakerMod.makeID;

public class DesertNectar extends BaseRelic {

    ///This relic doesn't do anything of its own, and is instead checked for in the
    // AttemptBloom(AbstractCard c, int i) method of BloomManager

    private static final String NAME = DesertNectar.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public DesertNectar() {
        super(ID, NAME, PathmakerCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
