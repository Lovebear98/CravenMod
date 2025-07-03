package craven.util.CustomActions.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class VulnAllAction extends AbstractGameAction {
    public VulnAllAction(AbstractCreature source, int num) {
        this.source = source;
        this.amount = num;
    }

    @Override
    public void update() {
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                addToTop(new ApplyPowerAction(m, source, new VulnerablePower(m, amount, source instanceof AbstractMonster)));
            }
        }
        isDone = true;
    }
}
