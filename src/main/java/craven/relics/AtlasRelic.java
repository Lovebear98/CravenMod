package craven.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AtlasRelic extends BaseRelic{
    ///An easy way to identify if relics are from the mod
    public AtlasRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(id, imageName, pool, tier, sfx);
    }
}
