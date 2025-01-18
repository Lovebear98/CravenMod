package pathmaker.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static pathmaker.util.otherutil.variables.UIText.Exhausttext;

public class SearchingSands extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(SearchingSands.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;
    private static final int SECOND_MAGIC = 1;
    private static final int UPG_SECOND_MAGIC = 1;

    public SearchingSands() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        if(!p.hand.isEmpty()){
            addToBot(new SelectCardsInHandAction(secondMagic, Exhausttext(), false, false, card -> true, ToExhaust -> {
                for (AbstractCard c : ToExhaust) {
                    addToTop(new ExhaustSpecificCardAction(c, p.hand));
                }
            }));
        }
    }

    @Override
    public void OnBloom(AbstractCard c) {

    }



    @Override
    public void applyPowers() {
        super.applyPowers();
        setSecondMagic(secondMagic);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SearchingSands();
    }
}