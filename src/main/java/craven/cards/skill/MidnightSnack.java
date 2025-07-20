package craven.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractSecondsCard;
import craven.character.CravenCharacter;
import craven.util.CardStats;

import static craven.util.CustomTags.Food;

public class MidnightSnack extends AbstractSecondsCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(MidnightSnack.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;



    public MidnightSnack() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        
        tags.add(Food);
        setEthereal(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public int BaseSecondsCount() {
        return magicNumber;
    }

    @Override
    public int SecondsCount() {
        return Math.max(BaseSecondsCount() - SecondsUsed, 0);
    }


    @Override
    public AbstractCard makeCopy() {
        MidnightSnack c = new MidnightSnack();
        c.SecondsUpgraded = this.SecondsUpgraded;
        return c;
    }
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        MidnightSnack c = (MidnightSnack) super.makeStatEquivalentCopy();
        c.SecondsUsed = this.SecondsUsed;
        return c;
    }

}