package craven.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.CravingInterface;
import craven.util.CardStats;
import craven.util.CustomActions.ExhaustTopCardAction;

public class Impatience extends AbstractHungryCard implements CravingInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Impatience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            -1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Impatience() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int e = GetXEnergy();
        addToBot(new DamageAction(m, new DamageInfo(p, damage * e, damageType), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ExhaustTopCardAction(e));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Impatience();
    }

    @Override
    public int CravingBonus() {
        return magicNumber;
    }
}