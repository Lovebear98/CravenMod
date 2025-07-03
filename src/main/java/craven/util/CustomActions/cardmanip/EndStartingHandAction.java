package craven.util.CustomActions.cardmanip;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static craven.util.otherutil.Wiz.DrawingStartingHand;
import static java.lang.Boolean.TRUE;

public class EndStartingHandAction extends AbstractGameAction {
    @Override
    public void update() {
        DrawingStartingHand = false;
        isDone = TRUE;
    }
}
