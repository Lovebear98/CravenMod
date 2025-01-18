package coyotle.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.AbstractDreamingCard;
import coyotle.character.CoyotleCharacter;
import coyotle.powers.custompowers.DreamsmokeMysticPower;
import coyotle.util.CardStats;
import coyotle.util.CustomActions.VulnAllAction;
import coyotle.util.CustomActions.WeakAllAction;

import static coyotle.util.otherutil.DreamManager.inDream;

///Dreaming
public class DreamsmokeMystic extends AbstractDreamingCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(DreamsmokeMystic.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public DreamsmokeMystic(){
        this(true);
    }
    public DreamsmokeMystic(boolean makePreview) {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setDreamType(CardType.SKILL);
        setDreamTarget(CardTarget.ALL_ENEMY);

        setExhaust(true);

        if(makePreview){
            AbstractDreamingCard c = new DreamsmokeMystic(false);
            c.onEnterDream();
            this.cardsToPreview = c;
        }

        if(inDream){
            onEnterDream();
        }



    }

    @Override
    protected void standardUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DreamsmokeMysticPower(p, magicNumber)));
    }

    @Override
    protected void dreamUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WeakAllAction(p, magicNumber));
        addToBot(new VulnAllAction(p, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DreamsmokeMystic();
    }
}