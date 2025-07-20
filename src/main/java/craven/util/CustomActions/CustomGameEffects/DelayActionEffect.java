package craven.util.CustomActions.CustomGameEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DelayActionEffect extends AbstractGameEffect {
    private final AbstractGameAction Action;

    public DelayActionEffect(AbstractGameAction action, float t) {
        this.Action = action;
        duration = startingDuration = t;
        this.color = Color.WHITE.cpy();
    }

    @Override
    public void update() {
        super.update();
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0){
            AbstractDungeon.actionManager.addToBottom(Action);
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
