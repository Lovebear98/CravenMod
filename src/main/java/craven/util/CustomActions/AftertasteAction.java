package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import craven.powers.custompowers.RavenousPower;

import static craven.util.otherutil.variables.Variables.p;

public class AftertasteAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public AftertasteAction(){
        this.p = p();
    }

    @Override
    public void update() {
        if(p.hasPower(RavenousPower.POWER_ID)){
            AbstractPower pow = p.getPower(RavenousPower.POWER_ID);
            int i = pow.amount/2;
            if(i < 1){
                i = 1;
            }
            if(i > pow.amount){
                i = pow.amount;
            }
            pow.flash();
            addToTop(new ReducePowerAction(p, p, pow, i));
        }
        isDone = true;
    }
}
