package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static coyotle.util.otherutil.ValorManager.DecreaseValor;


public class DecreaseValorAction extends AbstractGameAction {

    public DecreaseValorAction(){
        this(1);
    }
    public DecreaseValorAction(int i){
        this.amount = i;
    }
    @Override
    public void update() {
        DecreaseValor(amount);
        isDone = true;
    }
}
