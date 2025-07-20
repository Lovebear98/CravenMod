package craven.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractSecondsCard;
import craven.character.CravenCharacter;
import craven.util.CardStats;

import static craven.util.CustomTags.Food;

public class Grazing extends AbstractSecondsCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Grazing.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 1;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Grazing() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(Food);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageType), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
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
        Grazing c = new Grazing();
        c.SecondsUpgraded = this.SecondsUpgraded;
        return c;
    }
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        Grazing c = (Grazing) super.makeStatEquivalentCopy();
        c.SecondsUsed = this.SecondsUsed;
        return c;
    }
}