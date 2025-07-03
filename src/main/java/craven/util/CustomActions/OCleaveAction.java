//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import craven.cards.generated.Mechanics.Rations;
import craven.util.CustomActions.cardmanip.MakeTempCardInExhaustAction;

import static craven.util.otherutil.variables.Variables.p;

public class OCleaveAction extends AbstractGameAction {

    public OCleaveAction(int damage) {
        this.amount = damage;
        this.actionType = ActionType.DAMAGE;
        this.duration = startDuration = 0.1F;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                if(!mo.isDying && !mo.isDeadOrEscaped() && !mo.halfDead){
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(mo.hb.cX, mo.hb.cY, AttackEffect.SLASH_HEAVY));
                    mo.damage(new DamageInfo(p(), amount, DamageInfo.DamageType.NORMAL));
                    if ((mo.isDying || mo.currentHealth <= 0) && !mo.halfDead && !mo.hasPower("Minion")) {
                        addToBot(new MakeTempCardInExhaustAction(new Rations(), 1));
                    }
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
