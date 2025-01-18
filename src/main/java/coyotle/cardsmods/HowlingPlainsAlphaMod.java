package coyotle.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.attack.HowlingPlainsAlpha;

import java.util.List;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.ValorManager.ValorsPlayed;

public class HowlingPlainsAlphaMod extends AbstractCardModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(HowlingPlainsAlphaMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));


    public HowlingPlainsAlphaMod(){
    }
    ///Name our cardmod
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    ///It's innateo n whatever it's applied to, because it's applied on EVERYTHING
    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }
    ///When applied, make sure we don't double up.
    @Override
    public boolean shouldApply(AbstractCard targetCard) {
        ///We only add a mod if it doesn't already have the mod
        List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(targetCard, identifier(null));
        return sameMod.isEmpty();
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(card instanceof HowlingPlainsAlpha){
                return ValorsPlayed;
        }
        return super.modifyBaseDamage(damage, type, card, target);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HowlingPlainsAlphaMod();
    }
}
