package pathmaker.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.cards.generated.HiddenOasis;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static pathmaker.util.CustomTags.Bloom;

public class Sanctuary extends AbstractBloomCard {

    public static final String ID = makeID(Sanctuary.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Sanctuary() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        this.cardsToPreview = new HiddenOasis();
        tags.add(Bloom);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void onRetained() {
        super.onRetained();
    }

    @Override
    public void OnBloom(AbstractCard c) {
        HiddenOasis c2 = new HiddenOasis();
        if(upgraded){
            c2.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(c2, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Sanctuary();
    }
}