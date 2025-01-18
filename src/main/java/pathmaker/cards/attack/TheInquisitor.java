package pathmaker.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.cards.generated.OasisOfTears;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

public class TheInquisitor extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(TheInquisitor.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 3;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public TheInquisitor() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        this.cardsToPreview = new OasisOfTears();
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new MakeTempCardInDrawPileAction(new OasisOfTears(), 1, true, true));
    }

    @Override
    public void OnOasisReached(AbstractCard c) {
        super.OnOasisReached(c);

        this.baseDamage += magicNumber;
    }

    @Override
    public void OnBloom(AbstractCard c) {
        addToBot(new MakeTempCardInDrawPileAction(new OasisOfTears(), magicNumber, true, true));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TheInquisitor();
    }
}