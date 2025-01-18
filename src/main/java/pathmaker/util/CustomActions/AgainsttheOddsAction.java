//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pathmaker.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class AgainsttheOddsAction extends AbstractGameAction {
    private DamageInfo info;

    public AgainsttheOddsAction(AbstractCreature target, DamageInfo info, int amount) {
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.info = info;
        this.amount = amount;
    }

    public void update() {
        if (this.target != null && !this.target.hasPower(VulnerablePower.POWER_ID) && !this.target.hasPower(WeakPower.POWER_ID)) {
            ////this.addToTop(new DrawCardAction(AbstractDungeon.player, amount));
            this.addToTop(new GainEnergyAction(amount));
        }

        this.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }
}
