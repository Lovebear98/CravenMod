package pathmaker.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static pathmaker.util.CustomTags.Bloom;
import static pathmaker.util.otherutil.BloomManager.Growth;
import static pathmaker.util.otherutil.variables.Variables.p;

public class WallOfVines extends AbstractBloomCard {

    public static final String ID = makeID(WallOfVines.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 9;
    private static final int UPG_BLOCK = 5;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public WallOfVines() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(Bloom);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            Growth(magicNumber);
    }

    @Override
    public void onRetained() {
        super.onRetained();
    }

    @Override
    public void OnBloom(AbstractCard c) {
        addToBot(new GainBlockAction(p(), block));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WallOfVines();
    }
}