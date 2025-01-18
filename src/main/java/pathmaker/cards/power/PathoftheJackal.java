package pathmaker.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.powers.custompowers.OasisVigorPower;
import pathmaker.util.CardStats;

public class PathoftheJackal extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(PathoftheJackal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 3;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public PathoftheJackal() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new OasisVigorPower(p, magicNumber)));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
    }

    @Override
    public void OnBloom(AbstractCard c) {
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PathoftheJackal();
    }
}