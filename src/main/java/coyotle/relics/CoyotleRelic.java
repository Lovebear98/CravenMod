package coyotle.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class CoyotleRelic extends BaseRelic{
    ///An easy way to identify if relics are from the mod
    public CoyotleRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(id, imageName, pool, tier, sfx);
    }
}
