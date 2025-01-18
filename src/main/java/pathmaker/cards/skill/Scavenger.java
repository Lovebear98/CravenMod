package pathmaker.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static pathmaker.util.otherutil.variables.UIText.Exhausttext;
import static pathmaker.util.otherutil.variables.Variables.p;

public class Scavenger extends AbstractBloomCard {

    public static final String ID = makeID(Scavenger.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Scavenger() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new SelectCardsInHandAction( magicNumber, Exhausttext(), false, false, card -> true, ToExhaust -> {
                for (AbstractCard c : ToExhaust) {
                    addToTop(new ExhaustSpecificCardAction(c, p().hand));
                }
            }));
            addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void onRetained() {
        super.onRetained();
    }

    @Override
    public void OnBloom(AbstractCard c) {
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Scavenger();
    }
}