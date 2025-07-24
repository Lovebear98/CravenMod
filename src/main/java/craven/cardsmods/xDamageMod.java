package craven.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.MechanicManager.CravingCap;
import static craven.util.otherutil.Wiz.DevourEnabled;
import static craven.util.otherutil.variables.Variables.isInCombat;
import static craven.util.otherutil.variables.Variables.p;


public class xDamageMod extends AbstractCardModifier {

    public static final String ID = makeID(xDamageMod.class.getSimpleName());


    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(p() != null && isInCombat()){
            float f = damage;
            if(DevourEnabled()){
                 f += CravingCap(card);
            }else{
                 f += EnergyPanel.totalCount;
            }
            if(p().hasRelic(ChemicalX.ID)){
                f += 2;
            }
            return f;
        }else{
            float f = damage;
            f += CravingCap(card);
            return f;
        }
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
        return new xDamageMod();
    }
}
