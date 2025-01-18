package coyotle.util.CustomActions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static coyotle.patches.TutorialPatch.LaunchTutorial;

public class StartTutorialAction extends AbstractGameAction {
    public StartTutorialAction(){
        this.duration = startDuration = 0.35f;
    }
    @Override
    public void update() {
        tickDuration();
    }

    @Override
    protected void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            LaunchTutorial();
            this.isDone = true;
        }
    }
}
