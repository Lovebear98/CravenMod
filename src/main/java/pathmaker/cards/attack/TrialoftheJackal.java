package pathmaker.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractOasisCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

public class TrialoftheJackal extends AbstractOasisCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(TrialoftheJackal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 16;
    private static final int UPG_DAMAGE = 5;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 5;
    private static final int UPG_SECOND_MAGIC = -1;

    public TrialoftheJackal() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
                    isDone = true;
                }
            });
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new TrialoftheJackal();
    }
}