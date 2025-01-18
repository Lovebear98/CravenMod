package pathmaker.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;
import pathmaker.util.CustomActions.DelayGAction;

import static pathmaker.util.otherutil.variables.Variables.p;

public class RiddleoftheCobra extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(RiddleoftheCobra.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public RiddleoftheCobra() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        this.isMultiDamage = true;
        setExhaust(true);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p(), damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DelayGAction(new AbstractGameAction() {
            @Override
            public void update() {
                if(EnergyPanel.totalCount >= energyOnUse){
                    addToTop(new GainEnergyAction(magicNumber));
                }
                isDone = true;
            }
        }));

    }


    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RiddleoftheCobra();
    }
}