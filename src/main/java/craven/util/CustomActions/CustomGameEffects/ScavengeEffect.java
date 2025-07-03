package craven.util.CustomActions.CustomGameEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import craven.cards.generated.Mechanics.Rations;

public class ScavengeEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private int amount;

    public ScavengeEffect(int i) {
        this.amount = i;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void update() {
        if(amount > 0){
            amount -= 1;
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Rations(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }else{
            if(duration >= 0f){
                duration -= Gdx.graphics.getDeltaTime();
            }else{
                if (CampfireUI.hidden) {
                    AbstractRoom.waitTimer = 0.0F;
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
                }
            }
        }
    }
}
