package pathmaker.cards.generated;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractOasisCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;
@NoCompendium
public class HiddenOasis extends AbstractOasisCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(HiddenOasis.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 6;
    private static final int UPG_SECOND_MAGIC = -3;

    public HiddenOasis() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setSelfRetain(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
    }

    @Override
    public void OnBloom(AbstractCard c) {
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HiddenOasis();
    }
}