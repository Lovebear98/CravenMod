package coyotle.cards.starter;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.AbstractDreamingCard;
import coyotle.character.CoyotleCharacter;
import coyotle.util.CardStats;
import coyotle.util.CustomActions.BulkDiscardAction;

import java.util.ArrayList;

import static coyotle.util.otherutil.DreamManager.inDream;
import static coyotle.util.otherutil.variables.UIText.Discardtext;

public class Mutate extends AbstractDreamingCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Mutate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 1;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Mutate(){
        this(true);
    }
    public Mutate(boolean makePreview) {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setDreamType(CardType.SKILL);
        setDreamTarget(CardTarget.SELF);

        if(makePreview){
            AbstractDreamingCard c = new Mutate(false);
            c.onEnterDream();
            this.cardsToPreview = c;
        }

        if(inDream){
            onEnterDream();
        }
    }

    @Override
    protected void standardUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new DiscardAction(p, p, magicNumber, false));
    }

    @Override
    protected void dreamUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(999, Discardtext(), true, true, card -> card != this, selectedcards -> {
            int i = 0;
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            for (AbstractCard c : selectedcards) {
                tmp.add(c);
                i += 1;
            }
            if(!tmp.isEmpty()){
                addToTop(new BulkDiscardAction(tmp));
                addToBot(new GainBlockAction(p, i * block));
            }
        }));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Mutate();
    }
}