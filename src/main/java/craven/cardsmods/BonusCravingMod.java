package craven.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.powers.custompowers.RavenousPower;

import java.util.List;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;


public class BonusCravingMod extends AbstractCardModifier {
    public static final String ID = makeID(BonusCravingMod.class.getSimpleName());

    public int bonus;
    public BonusCravingMod(int i){
        this.bonus = i;
    }

    public boolean shouldApply(AbstractCard targetCard) {
        ///We only add a mod if it doesn't already have the mod
        List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(targetCard, identifier(null));
        if(!sameMod.isEmpty()){
            BonusCravingMod Original = (BonusCravingMod) sameMod.get(0);
            Original.bonus += this.bonus;
        }
        return sameMod.isEmpty();
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }



    @Override
    public AbstractCardModifier makeCopy() {
        return new BonusCravingMod(bonus);
    }
}