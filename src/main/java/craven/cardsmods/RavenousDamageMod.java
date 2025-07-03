package craven.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.powers.custompowers.RavenousPower;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;


public class RavenousDamageMod extends AbstractCardModifier {

    public static final String ID = makeID(RavenousDamageMod.class.getSimpleName());


    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(p() != null){
            if(p().hasPower(RavenousPower.POWER_ID)){
                return damage + p().getPower(RavenousPower.POWER_ID).amount;
            }
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
        return new RavenousDamageMod();
    }
}
