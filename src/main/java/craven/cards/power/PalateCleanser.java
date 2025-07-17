package craven.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.powers.custompowers.PalateCleanserPower;
import craven.powers.custompowers.RiskCapPower;
import craven.util.CardStats;
import craven.util.CustomActions.ReduceRiskAction;

import static craven.util.otherutil.MechanicManager.Risk;

public class PalateCleanser extends AbstractHungryCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(PalateCleanser.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 3;
    private static final int SECOND_MAGIC = 2;
    private static final int UPG_SECOND_MAGIC = 0;


    public PalateCleanser() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReduceRiskAction(Risk));

        if(upgraded){
            addToBot(new ApplyPowerAction(p, p, new RiskCapPower(p, magicNumber)));
            addToBot(new ApplyPowerAction(p, p, new PalateCleanserPower(p, true)));
        }else{
            addToBot(new ApplyPowerAction(p, p, new PalateCleanserPower(p, secondMagic)));
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new PalateCleanser();
    }
}