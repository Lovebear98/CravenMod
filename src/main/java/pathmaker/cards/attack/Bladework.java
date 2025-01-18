package pathmaker.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static pathmaker.util.CustomTags.Bloom;
import static pathmaker.util.otherutil.BloomManager.BloomReady;
import static pathmaker.util.otherutil.variables.Variables.p;

public class Bladework extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Bladework.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 6;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;
    private boolean DoBloomAction = false;

    public Bladework() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        setSecondDamage(10, 4);

        tags.add(Bloom);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!isMultiDamage){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void OnBloom(AbstractCard c) {
        if(isMultiDamage){
            addToBot(new DamageAllEnemiesAction(p(), customVarBase("D2"), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(BloomReady()){
            this.isMultiDamage = true;
            this.target = CardTarget.ALL_ENEMY;
        }else{
            this.isMultiDamage = false;
            this.target = CardTarget.ENEMY;
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Bladework();
    }
}