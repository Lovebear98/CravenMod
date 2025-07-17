package craven.cards.skill;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.util.CardStats;

public class SetTheTable extends AbstractHungryCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(SetTheTable.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public SetTheTable() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c;

        c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
        if(this.upgraded){
            c.upgrade();
        }
        this.addToBot(new MakeTempCardInHandAction(c, true));

        c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
        if(this.upgraded){
            c.upgrade();
        }
        this.addToBot(new MakeTempCardInHandAction(c, true));
    }



    @Override
    public AbstractCard makeCopy() { //Optional
        return new SetTheTable();
    }

}