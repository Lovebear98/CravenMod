package craven.cards.skill;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.OnDevouredInterface;
import craven.util.CardStats;

import static craven.util.otherutil.variables.Variables.p;

public class AmbrosiaSalad extends AbstractHungryCard implements OnDevouredInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(AmbrosiaSalad.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            -2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public AmbrosiaSalad() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }



    @Override
    public AbstractCard makeCopy() {
        return new AmbrosiaSalad();
    }

    @Override
    public void PostDevoured() {
        addToBot(new HealAction(p(), p(), magicNumber));
    }
}