    package craven.cards.power;

    import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
    import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
    import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
    import com.megacrit.cardcrawl.cards.AbstractCard;
    import com.megacrit.cardcrawl.cards.curses.Shame;
    import com.megacrit.cardcrawl.characters.AbstractPlayer;
    import com.megacrit.cardcrawl.localization.CardStrings;
    import com.megacrit.cardcrawl.monsters.AbstractMonster;
    import craven.cards.AbstractHungryCard;
    import craven.character.CravenCharacter;
    import craven.patches.interfaces.CravingInterface;
    import craven.util.CardStats;

public class Craven extends AbstractHungryCard implements CravingInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Craven.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 3;
    private static final int UPG_SECOND_MAGIC = 1;


    public Craven() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        this.cardsToPreview = new Shame();
        this.UpgradePreviewCard = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int e = GetXEnergy();
        if(upgraded){
            addToBot(new MakeTempCardInDrawPileAction(new Shame(), e, true, true));
        }else{
            addToBot(new MakeTempCardInHandAction(new Shame(), e));
        }
        addToBot(new GainEnergyAction(e * secondMagic));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Craven();
    }

    @Override
    public int CravingBonus() {
        return magicNumber;
    }
}