package coyotle.util.CustomActions.imported;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static coyotle.util.otherutil.variables.Variables.VFXDuration;

public class LightningDamageAction extends AbstractGameAction {
    private final DamageInfo info;
    private final AbstractMonster m;

    public LightningDamageAction(AbstractMonster m, DamageInfo damageInfo) {
        this.m = m;
        this.info = damageInfo;
    }

    @Override
    public void update() {
        if(m != null){
            addToTop(new DamageAction(m, info));
            addToTop(new VFXAction(new SoundLightningEffect(m.hb.cX, m.hb.cY), VFXDuration()));
        }
        isDone = true;
    }
}
