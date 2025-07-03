package craven.util.CustomActions.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static craven.util.otherutil.variables.Variables.p;

public class RandomWeakAction extends AbstractGameAction {

    public RandomWeakAction(int num){
        this.amount = num;
    }
    @Override
    public void update() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        addToTop(new ApplyPowerAction(m, p(), new WeakPower(m, amount, false)));
        isDone = true;
    }
}
