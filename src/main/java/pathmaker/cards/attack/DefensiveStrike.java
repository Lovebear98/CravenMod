package pathmaker.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF;

public class DefensiveStrike extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(DefensiveStrike.class.getSimpleName());

    private boolean BlockMode = false;
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public DefensiveStrike() {
        this(true);
    }

    public DefensiveStrike(boolean makepreview) {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        if(makepreview){
            AbstractCard c = new DefensiveStrike(false);
            c.baseMagicNumber += 1;
            this.cardsToPreview = c;
        }

        this.returnToHand = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(BlockMode){
            addToBot(new GainBlockAction(p, block));
            this.baseBlock = 0;
        }else{
            addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, deeps ->
            {
                this.baseBlock = deeps;
            }));
        }
        this.baseMagicNumber += 1;
        cardsToPreview.baseMagicNumber += 1;
    }

    @Override
    public void OnBloom(AbstractCard c) {
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(BlockMode && baseMagicNumber%2 != 0){
            this.BlockMode = false;
            this.target = CardTarget.ENEMY;
        }else if(!BlockMode && baseMagicNumber%2 == 0){
            this.BlockMode = true;
            this.target = SELF;
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DefensiveStrike();
    }
}