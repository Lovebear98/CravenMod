package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static craven.util.otherutil.MechanicManager.AdjustRisk;

public class ReduceRiskAction extends AbstractGameAction {
    public ReduceRiskAction(int num) {
        this.amount = num;
    }

    @Override
    public void update() {
        AdjustRisk(-amount);
        isDone = true;
    }
}
