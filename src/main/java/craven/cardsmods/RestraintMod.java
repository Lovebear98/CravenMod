package craven.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;


public class RestraintMod extends AbstractCardModifier {

    public static final String ID = makeID(RestraintMod.class.getSimpleName());


    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(p() != null){
            return damage + p().exhaustPile.size();
        }
        return super.modifyDamage(damage, type, card, target);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }



    @Override
    public AbstractCardModifier makeCopy() {
        return new RestraintMod();
    }
}
