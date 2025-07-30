package craven.util.CustomActions.CustomGameEffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;

import java.util.ArrayList;

public class DestroyRewardsEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {

    private final ArrayList<RewardItem> List;

    public DestroyRewardsEffect(){

        this.List = new ArrayList<>();

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

                for(RewardItem r: AbstractDungeon.combatRewardScreen.rewards){
                    ///Add card rewards to a list
                    if(r.type == RewardItem.RewardType.CARD){
                        List.add(r);
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void update() {
        if(!List.isEmpty()){

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

                    RewardItem r = List.get(0);

                    if(r.type == RewardItem.RewardType.CARD){
                        AbstractDungeon.combatRewardScreen.rewards.remove(r);
                    }

                    List.remove(r);

                }
            }

        }else{
            isDone = true;
        }
    }
}
