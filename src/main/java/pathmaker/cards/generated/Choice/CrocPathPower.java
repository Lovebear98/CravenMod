package pathmaker.cards.generated.Choice;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractOasisCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.powers.custompowers.PathoftheCrocodilePower;
import pathmaker.util.CardStats;

import static pathmaker.util.otherutil.variables.Variables.p;

@NoCompendium
public class CrocPathPower extends AbstractOasisCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(CrocPathPower.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public CrocPathPower() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onChoseThisOption() {
        super.onChoseThisOption();
        addToTop(new ApplyPowerAction(p(), p(), new PathoftheCrocodilePower(p(), magicNumber, this.type)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new CrocPathPower();
    }
}