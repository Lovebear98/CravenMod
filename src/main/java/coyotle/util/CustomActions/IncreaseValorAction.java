package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static coyotle.util.otherutil.ValorManager.IncreaseValor;


public class IncreaseValorAction extends AbstractGameAction {

    public IncreaseValorAction(){
        this(1);
    }
    public IncreaseValorAction(int i){
        this.amount = i;
    }
    @Override
    public void update() {
        IncreaseValor(amount);
        isDone = true;
    }
}
