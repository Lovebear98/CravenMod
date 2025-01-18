package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static coyotle.util.otherutil.DreamManager.ExitDream;

public class EndDreamCheckAction extends AbstractGameAction {
    public EndDreamCheckAction(){
    }
    @Override
    public void update() {
        ExitDream();
        ///this.duration -= Gdx.graphics.getDeltaTime();
        ///if(this.duration <= 0){
        ///    ExitDream();
        ///}
        isDone = true;
    }
}
