package pathmaker.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

public class DistantJourney extends AbstractBloomCard {

    public static final String ID = makeID(DistantJourney.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public DistantJourney() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        setSelfRetain(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(secondMagic));
    }

    @Override
    public void OnOasisReached(AbstractCard c) {
        super.OnOasisReached(c);
        setSecondMagic(secondMagic + magicNumber);
    }

    @Override
    public void OnBloom(AbstractCard c) {

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DistantJourney();
    }
}