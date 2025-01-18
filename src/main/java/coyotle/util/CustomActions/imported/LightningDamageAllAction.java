package coyotle.util.CustomActions.imported;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static coyotle.util.otherutil.variables.Variables.VFXDuration;

public class LightningDamageAllAction extends AbstractGameAction {
    private final DamageInfo info;
    private final AbstractGameAction action;

    ///If we want to do something per enemy, do it here
    public LightningDamageAllAction(DamageInfo damageInfo, AbstractGameAction action) {
        this.info = damageInfo;
        this.action = action;
    }
    ///Otherwise, we have a null
    public LightningDamageAllAction(DamageInfo damageInfo) {
        this(damageInfo, null);
    }
    @Override
    public void update() {
        ///Damage all the enemies at once
        this.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.info.base, false, false), DamageInfo.DamageType.NORMAL, AttackEffect.NONE));// 52 55
        ///Then for each one
        for (AbstractMonster m3 : AbstractDungeon.getMonsters().monsters) {
            ///If it's alive
            if (!m3.isDeadOrEscaped() && !m3.halfDead) {// 60
                ///Animate on it
                addToTop(new VFXAction(new SoundLightningEffect(m3.hb.cX, m3.hb.cY), VFXDuration()));
                ///And if we have an action
                if(action != null){
                    ///Fire it
                    addToBot(action);
                }
            }
        }
        isDone = true;
    }
}
