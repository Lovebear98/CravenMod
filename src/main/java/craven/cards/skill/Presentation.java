package craven.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.OnDevouredInterface;
import craven.util.CardStats;
import craven.util.CustomActions.IncreaseRiskAction;

import static craven.util.otherutil.MechanicManager.Risk;
import static craven.util.otherutil.MechanicManager.TrueRiskCap;

public class Presentation extends AbstractHungryCard implements OnDevouredInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Presentation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Presentation() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setExhaust(true);
        setSelfRetain(false, true);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int Remainder = Math.min(TrueRiskCap() - Risk, magicNumber);
        if(Remainder != 0){
            addToBot(new IncreaseRiskAction(Remainder));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, Remainder)));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, Remainder)));
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Presentation();
    }

    @Override
    public void PostDevoured() {
    }
}