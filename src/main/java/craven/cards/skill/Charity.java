package craven.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.OnDevouredInterface;
import craven.util.CardStats;
import craven.util.CustomActions.IncreaseRiskAction;

import static craven.util.otherutil.MechanicManager.Risk;
import static craven.util.otherutil.MechanicManager.TrueRiskCap;

public class Charity extends AbstractHungryCard implements OnDevouredInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Charity.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Charity() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int Remainder = Math.min(TrueRiskCap() - Risk, magicNumber);
        if(Remainder != 0){
            addToBot(new IncreaseRiskAction(Remainder));
            addToBot(new GainBlockAction(p, block * Remainder));
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Charity();
    }

    @Override
    public void PostDevoured() {
    }
}