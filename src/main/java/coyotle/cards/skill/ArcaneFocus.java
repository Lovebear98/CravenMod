package coyotle.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.AbstractDreamingCard;
import coyotle.character.CoyotleCharacter;
import coyotle.util.CardStats;
import coyotle.util.CustomActions.MillAction;

import static coyotle.util.otherutil.DreamManager.inDream;

///Dreaming
public class ArcaneFocus extends AbstractDreamingCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(ArcaneFocus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 3;
    private static final int UPG_SECOND_MAGIC = 1;

    public ArcaneFocus(){
        this(true);
    }
    public ArcaneFocus(boolean makePreview) {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setDreamType(CardType.SKILL);
        setDreamTarget(CardTarget.NONE);

        if(makePreview){
            AbstractDreamingCard c = new ArcaneFocus(false);
            c.onEnterDream();
            this.cardsToPreview = c;
        }

        if(inDream){
            onEnterDream();
        }

    }

    @Override
    protected void standardUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(magicNumber));
        addToBot(new DrawCardAction(1));
        addToBot(new MillAction(secondMagic));
    }

    @Override
    protected void dreamUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(secondMagic));
        addToBot(new MillAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ArcaneFocus();
    }
}