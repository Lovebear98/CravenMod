package coyotle.util.CustomActions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayAction extends AbstractGameAction {

    private final AbstractGameAction action;

    public DelayAction(AbstractGameAction act){
        this(0, act);
    }
    public DelayAction(float time, AbstractGameAction act){
        this.action = act;
        this.duration = this.startDuration = time;
    }
    @Override
    public void update() {
        if(this.duration >= 0f){
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        if(this.duration <= 0f){
            addToBot(action);
            isDone = true;
        }
    }
}
