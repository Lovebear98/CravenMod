package craven.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.OnDevouredInterface;
import craven.util.CardStats;
import craven.util.CustomActions.NibbleAction;
import craven.util.CustomActions.generic.BulkExhaustAction;

import java.util.ArrayList;

import static craven.util.otherutil.variables.UIText.ExhaustText;
import static craven.util.otherutil.variables.Variables.p;

public class Nibble extends AbstractHungryCard implements OnDevouredInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Nibble.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Nibble() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber, ExhaustText(), false, false, card -> card != this, selectedcards -> {

            ArrayList<AbstractCard> tmp = new ArrayList<>();

            for(AbstractCard c: selectedcards){
                addToBot(new ExhaustSpecificCardAction(c, p().hand));
                tmp.add(c);
                AbstractCard c2 = c.makeStatEquivalentCopy();
                addToBot(new NibbleAction(c2));
            }

            addToBot(new DrawCardAction(magicNumber));
        }));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Nibble();
    }

    @Override
    public void PostDevoured() {
    }
}