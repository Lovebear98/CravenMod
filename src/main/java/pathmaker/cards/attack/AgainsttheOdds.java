package pathmaker.cards.attack;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;
import pathmaker.util.CustomActions.AgainsttheOddsAction;

public class AgainsttheOdds extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(AgainsttheOdds.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 4;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public AgainsttheOdds() {
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
        addToBot(new AgainsttheOddsAction(m,  new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), magicNumber));
    }

    @Override
    public void OnBloom(AbstractCard c) {
    }


    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && !m.hasPower(VulnerablePower.POWER_ID) && !(m.hasPower(WeakPower.POWER_ID))) {
                this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }

    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new AgainsttheOdds();
    }
}