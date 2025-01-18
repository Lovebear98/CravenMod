package coyotle.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.CoyotleMod;

import java.util.List;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.Wiz.isStrikeOrDefend;

public class WindsingerMod extends AbstractCardModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(WindsingerMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));
    private int NewCost = -99;

    public WindsingerMod(){
    }
    ///Name our cardmod
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        CoyotleMod.logger.info(card.name + " has been marked.");
        if(card.cost > 0){
            card.cost = card.cost * 2;
            if (card.cost < 0) {
                card.cost = 0;
            }
            card.costForTurn = card.cost;
        }
    }

    @Override
    public boolean onBattleStart(AbstractCard card) {
        return super.onBattleStart(card);
    }


    @Override
    public boolean isInherent(AbstractCard card) {
        return false;
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
        if(isStrikeOrDefend(card)){
            return damage * 2;
        }
        return super.modifyBaseDamage(damage, type, card, target);
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if(isStrikeOrDefend(card)){
            return block * 2;
        }
        return super.modifyBaseBlock(block, card);
    }

    private void ModifyCost(AbstractCard card) {
        if(NewCost == -99){
            NewCost = card.cost * 2;
            card.cost = NewCost;
            if(!card.freeToPlay()){
                card.costForTurn = card.costForTurn * 2;
            }
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new WindsingerMod();
    }
}
