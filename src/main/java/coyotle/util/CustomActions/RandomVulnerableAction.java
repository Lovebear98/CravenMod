package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static coyotle.util.otherutil.variables.Variables.p;

public class RandomVulnerableAction extends AbstractGameAction {

    public RandomVulnerableAction(int num){
        this.amount = num;
    }
    @Override
    public void update() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        addToTop(new ApplyPowerAction(m, p(), new VulnerablePower(m, amount, false)));
        isDone = true;
    }
}
