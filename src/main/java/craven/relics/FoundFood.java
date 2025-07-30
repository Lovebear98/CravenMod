package craven.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnSkipCardRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import craven.character.CravenCharacter;
import craven.util.CustomActions.CustomGameEffects.DestroyRewardsEffect;

import static craven.CravenMod.makeID;

public class FoundFood extends AtlasRelic implements OnSkipCardRelic{

    private static final String NAME = FoundFood.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public static final int Increment = 1;
    private boolean Enabled = true;

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
    public void justEnteredRoom(AbstractRoom room) {
        this.Enabled = true;
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
        Trigger();
    }


    @Override
    public void onSkipCard(RewardItem rewardItem) {
        Trigger();
    }


    private void Trigger() {
        if(Enabled){
            if((AbstractDungeon.getCurrRoom() != null)){
                if((
                        AbstractDungeon.getCurrRoom().event == null || ///If the room isn't an event room
                        AbstractDungeon.getCurrRoom().event != null && ///OR it IS an event room -
                        !AbstractDungeon.getCurrRoom().event.noCardsInRewards) && //AND it has card rewards
                        !(AbstractDungeon.getCurrRoom() instanceof TreasureRoom) && ///AND it's not a treasure room
                        !(AbstractDungeon.getCurrRoom() instanceof RestRoom) && ///AND it's not a rest room
                        !(AbstractDungeon.getCurrRoom() instanceof ShopRoom) && ///AND it's not a shop room
                        !((AbstractDungeon.getCurrRoom()) instanceof MonsterRoomBoss) &&
                        !AbstractDungeon.combatRewardScreen.rewards.isEmpty() ///AND rewards aren't empty somehow
                ){
                    this.Enabled = false;
                    this.flash();
                    this.counter += 1;
                    fixDescription();

                    ///Yeet
                AbstractDungeon.effectsQueue.add(new DestroyRewardsEffect());
                }
            }
        }
    }
}
