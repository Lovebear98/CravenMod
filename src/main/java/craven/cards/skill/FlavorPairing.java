package craven.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.OnDevouredInterface;
import craven.util.CardStats;
import craven.util.CustomActions.generic.BulkExhaustAction;

import java.util.ArrayList;

import static craven.util.otherutil.variables.UIText.ExhaustText;

public class FlavorPairing extends AbstractHungryCard implements OnDevouredInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(FlavorPairing.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 2;
    private static final int UPG_SECOND_MAGIC = -1;


    public FlavorPairing() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setExhaust(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber, ExhaustText(), false, false, card -> card != this, selectedcards -> {
            int Strength = 0;
            int Dexterity = 0;
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            for(AbstractCard c: selectedcards){
                if(c.type == CardType.ATTACK){
                    Strength += 1;
                }
                if(c.type == CardType.SKILL){
                    Dexterity += 1;
                }

                tmp.add(c);
            }

            if(!tmp.isEmpty()){
                addToBot(new BulkExhaustAction(tmp));
            }

            if(Strength >= 1){
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, secondMagic * Strength)));
                if(!upgraded){
                    addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, secondMagic * Strength)));
                }
            }
            if(Dexterity >= 1){
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, secondMagic * Dexterity)));
                if(!upgraded){
                    addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, secondMagic * Dexterity)));
                }
            }

        }));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new FlavorPairing();
    }

    @Override
    public void PostDevoured() {
    }
}