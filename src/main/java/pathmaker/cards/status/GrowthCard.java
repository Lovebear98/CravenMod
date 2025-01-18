package pathmaker.cards.status;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

@NoCompendium
public class GrowthCard extends AbstractBloomCard {
    public static final String ID = makeID(GrowthCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            0
    );


    public GrowthCard() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void OnBloom(AbstractCard c) {
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GrowthCard();
    }
}