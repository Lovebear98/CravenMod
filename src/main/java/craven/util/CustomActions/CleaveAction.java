//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class CleaveAction extends AbstractGameAction {
    public int[] damage;
    private int baseDamage;
    private boolean firstFrame;
    private boolean utilizeBaseDamage;
    private final int EnNum;

    public CleaveAction(AbstractCreature source, int energy, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect, boolean isFast) {
        this.firstFrame = true;
        this.utilizeBaseDamage = false;
        this.source = source;
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;

        this.EnNum = energy;

        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }

    }
    public CleaveAction(AbstractCreature source, int energy, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this(source, energy, amount, type, effect, false);
    }

    public CleaveAction(AbstractPlayer player, int energy, int baseDamage, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this(player, energy, (int[])null, type, effect, false);
        this.baseDamage = baseDamage;
        this.utilizeBaseDamage = true;
    }

    public void update() {
        int e = 0;
        int i;
        if (this.firstFrame) {
            boolean playedMusic = false;

            if (this.utilizeBaseDamage) {
                this.damage = DamageInfo.createDamageMatrix(this.baseDamage);
            }

            for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!m.isDying && m.currentHealth > 0 && !m.isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));
                    }
                }

            }
            this.firstFrame = false;
        }

        this.tickDuration();

        if (this.isDone) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.damage);
            }

            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

            for(i = 0; i < temp; ++i) {
                if (!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDeadOrEscaped()) {
                    AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                    mo.damage(new DamageInfo(this.source, this.damage[i], this.damageType));
                    if ((mo.isDying || mo.currentHealth <= 0) && !mo.halfDead && !mo.hasPower("Minion")) {
                        e += 1;
                    }
                }
            }

            if(e > 0){
                addToBot(new GainEnergyAction(e * EnNum));
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

///            for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
//                if (m.isDeadOrEscaped()) {
//                    m.damage(new DamageInfo(this.source, this.damage[AbstractDungeon.getCurrRoom().monsters.monsters.indexOf(m)], this.damageType));
//                    if ((m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion")) {
//                        addToBot(new MakeTempCardInExhaustAction(new Rations(), 1));
//                    }
//                }
//            }
