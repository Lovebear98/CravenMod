package pathmaker.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static pathmaker.util.otherutil.variables.UIText.Recovertext;

public class Sacrifice extends AbstractBloomCard {

    public static final String ID = makeID(Sacrifice.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Sacrifice() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        this.cardsToPreview = new Wound();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.exhaustPile.isEmpty()){
            addToBot(new SelectCardsAction(p.exhaustPile.group, magicNumber, Recovertext(), false, card -> true, ToExhaust -> {
                for (AbstractCard c : ToExhaust) {
                    AbstractCard d = c.makeStatEquivalentCopy();
                    if(upgraded){
                        d.upgrade();
                    }
                    addToTop(new MakeTempCardInDrawPileAction(d, 1, true, true));
                }
            }));


            addToBot(new MakeTempCardInDrawPileAction(new Wound(), magicNumber, true, true));
        }
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
        return new Sacrifice();
    }
}