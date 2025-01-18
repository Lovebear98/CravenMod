package pathmaker.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayGAction extends AbstractGameAction {
    private final AbstractGameAction action;

    public DelayGAction(AbstractGameAction abstractGameAction) {
        this.action = abstractGameAction;
    }

    @Override
    public void update() {
        addToBot(action);
        isDone = true;
    }
}
