package craven.cards.attack;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.visual.AttackEffectEnum;
import craven.util.CardStats;

public class Feast extends AbstractHungryCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Feast.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 1;
    private static final int UPG_SECOND_MAGIC = 0;

    public Feast() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AttackEffectEnum.WINE));
        int i = magicNumber;
        for(AbstractMonster m2: AbstractDungeon.getMonsters().monsters){
            if(!m2.isDeadOrEscaped()){
                i += secondMagic;
            }
        }
        if(i > 0){
            addToBot(new DrawCardAction(p, i));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Feast();
    }
}