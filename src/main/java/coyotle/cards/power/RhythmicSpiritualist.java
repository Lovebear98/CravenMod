package coyotle.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import coyotle.cards.AbstractDreamingCard;
import coyotle.character.CoyotleCharacter;
import coyotle.powers.custompowers.RhythmicSpiritualistPower;
import coyotle.util.CardStats;

import static coyotle.util.otherutil.DreamManager.inDream;

///Prophecy-Dreaming crossup
public class RhythmicSpiritualist extends AbstractDreamingCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(RhythmicSpiritualist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 2;
    private static final int UPG_SECOND_MAGIC = 1;


    public RhythmicSpiritualist(){
        this(true);
    }
    public RhythmicSpiritualist(boolean makePreview) {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setDreamType(CardType.POWER);
        setDreamTarget(CardTarget.SELF);

        if(makePreview){
            AbstractDreamingCard c = new RhythmicSpiritualist(false);
            c.onEnterDream();
            this.cardsToPreview = c;
        }

        if(inDream){
            onEnterDream();
        }
    }

    @Override
    protected void standardUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RhythmicSpiritualistPower(p, magicNumber, secondMagic)));
    }

    @Override
    protected void dreamUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new GainEnergyAction(secondMagic));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, secondMagic)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RhythmicSpiritualist();
    }
}