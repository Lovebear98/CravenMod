package craven.util.CustomActions.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StrengthAllAction extends AbstractGameAction {
    private final boolean Temporary;

    public StrengthAllAction(AbstractCreature source, int num) {
        this(source, num, false);
    }
    public StrengthAllAction(AbstractCreature source, int num, boolean Temp) {
        this.source = source;
        this.amount = num;
        this.Temporary = Temp;
    }

    @Override
    public void update() {
        boolean Gaining = (amount > 0);
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                if(Temporary){
                    if(Gaining){
                        addToTop(new ApplyPowerAction(m, source, new LoseStrengthPower(m, amount)));
                    }else{
                        addToTop(new ApplyPowerAction(m, source, new GainStrengthPower(m, -amount)));
                    }
                }
                addToTop(new ApplyPowerAction(m, source, new StrengthPower(m, amount)));
            }
        }
        isDone = true;
    }
}
