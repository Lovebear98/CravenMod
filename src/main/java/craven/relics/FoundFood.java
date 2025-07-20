package craven.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnSkipCardRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;

public class FoundFood extends AtlasRelic implements OnSkipCardRelic {

    private static final String NAME = FoundFood.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public static final int Increment = 1;

    public FoundFood() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
        fixDescription();
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
        fixDescription();
    }

    @Override
    public String getUpdatedDescription() {
        String s = DESCRIPTIONS[0] + Increment + DESCRIPTIONS[1];
        if(this.counter == 0){
            s += DESCRIPTIONS[2];
        }else{
            s += DESCRIPTIONS[3] + counter;
        }
        s += DESCRIPTIONS[4];
        return s;
    }

    @Override
    public void onSkipSingingBowl(RewardItem rewardItem) {
        this.flash();
        this.counter += 1;
        fixDescription();
    }

    @Override
    public void onSkipCard(RewardItem rewardItem) {
        this.flash();
        this.counter += 1;
        fixDescription();
    }
}
