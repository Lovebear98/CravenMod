package craven.cards.attack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.cardsmods.xDamageMod;
import craven.character.CravenCharacter;
import craven.patches.interfaces.CravingInterface;
import craven.util.CardStats;
import craven.util.CustomActions.IncreaseRiskAction;
import craven.util.CustomActions.ReduceRiskAction;
import craven.util.CustomActions.CustomGameEffects.vfx.SavageryEffect;

import static craven.util.otherutil.MechanicManager.Risk;
import static craven.util.otherutil.MechanicManager.TrueRiskCap;

public class Savagery extends AbstractHungryCard implements CravingInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Savagery.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            -1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 2;
    private static final int UPG_SECOND_MAGIC = 0;

    public Savagery() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        CardModifierManager.addModifier(this, new xDamageMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int e = GetXEnergy();
        int Remainder = TrueRiskCap() - Risk;
        addToBot(new IncreaseRiskAction(Remainder));
        if(Remainder >= 0){
            for(int i = Remainder; i > 0; i -= 1){
                addToBot(new VFXAction(new SavageryEffect(m)));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Savagery();
    }

    @Override
    public int CravingBonus() {
        return magicNumber;
    }
}