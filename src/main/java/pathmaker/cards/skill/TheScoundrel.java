package pathmaker.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.orbs.AttackFlower;
import pathmaker.orbs.PowerFlower;
import pathmaker.orbs.SkillFlower;
import pathmaker.util.CardStats;

public class TheScoundrel extends AbstractBloomCard {

    public static final String ID = makeID(TheScoundrel.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            3
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public TheScoundrel() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));

        addToBot(new ChannelAction(new AttackFlower()));
        addToBot(new ChannelAction(new SkillFlower()));
        addToBot(new ChannelAction(new PowerFlower()));
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
        return new TheScoundrel();
    }
}