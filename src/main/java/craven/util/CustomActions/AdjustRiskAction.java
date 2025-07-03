package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static craven.util.otherutil.MechanicManager.AdjustRisk;

public class AdjustRiskAction extends AbstractGameAction {
    public AdjustRiskAction(int num) {
        this.amount = num;
    }

    @Override
    public void update() {
        AdjustRisk(-amount);
        isDone = true;
    }
}
