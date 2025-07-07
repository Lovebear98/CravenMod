package craven.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;

public abstract class AtlasRelic extends BaseRelic{
    ///An easy way to identify if relics are from the mod
    public AtlasRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(id, imageName, pool, tier, sfx);
    }

    public void fixDescription(){
        description = this.getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }
}
