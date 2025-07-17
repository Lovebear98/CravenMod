package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static craven.util.otherutil.MechanicManager.AdjustRisk;

public class IncreaseRiskAction extends AbstractGameAction {
    public IncreaseRiskAction(int num) {
        this.amount = num;
    }

    @Override
    public void update() {
        AdjustRisk(amount);
        isDone = true;
    }
}
