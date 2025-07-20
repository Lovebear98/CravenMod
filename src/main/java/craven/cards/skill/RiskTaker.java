package craven.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractSecondsCard;
import craven.character.CravenCharacter;
import craven.powers.custompowers.LoseRiskCapPower;
import craven.powers.custompowers.RiskCapPower;
import craven.util.CardStats;

public class RiskTaker extends AbstractSecondsCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(RiskTaker.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 1;
    private static final int UPG_SECOND_MAGIC = 0;


    public RiskTaker() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RiskCapPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseRiskCapPower(p, magicNumber)));

    }


    @Override
    public int BaseSecondsCount() {
        return secondMagic;
    }

    @Override
    public int SecondsCount() {
        return Math.max(BaseSecondsCount() - SecondsUsed, 0);
    }




    @Override
    public AbstractCard makeCopy() {
        RiskTaker c = new RiskTaker();
        c.SecondsUpgraded = this.SecondsUpgraded;
        return c;
    }
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        RiskTaker c = (RiskTaker) super.makeStatEquivalentCopy();
        c.SecondsUsed = this.SecondsUsed;
        return c;
    }
}