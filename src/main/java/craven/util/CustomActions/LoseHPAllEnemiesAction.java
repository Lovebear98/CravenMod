package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class LoseHPAllEnemiesAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;

    public LoseHPAllEnemiesAction(AbstractCreature source, int amount) {
        this(source, amount, AttackEffect.NONE);
    }

    public LoseHPAllEnemiesAction(AbstractCreature source, int amount, AbstractGameAction.AttackEffect effect) {
        this.source = source;
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33F;
    }

    public void update() {
        this.tickDuration();
        if (this.duration == 0.33F && this.target.currentHealth > 0) {
            for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));
                }
            }
        }
        if (this.isDone) {
            for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    m.damage(new DamageInfo(this.source, this.amount, DamageType.HP_LOSS));
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }

    }
}
