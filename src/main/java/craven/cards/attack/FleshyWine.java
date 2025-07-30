package craven.cards.attack;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.visual.AttackEffectEnum;
import craven.powers.custompowers.RavenousPower;
import craven.util.CardStats;

import static craven.util.otherutil.variables.Variables.p;

public class FleshyWine extends AbstractHungryCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(FleshyWine.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public FleshyWine() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!(p == null) && p.hasPower(RavenousPower.POWER_ID)){
            if(magicNumber > 0){
                for(int i = magicNumber; i > 0; i -= 1){
                    addToBot(new DamageAction(m, new DamageInfo(p, damage, damageType), AttackEffectEnum.WINE));
                }
            }
        }else{
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageType), AttackEffectEnum.WINE));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(p()!=null && p().hasPower(RavenousPower.POWER_ID)){
            this.glowColor = GlowColor();
        }
    }


    @Override
    public Color GlowColor() {
        return Color.RED.cpy();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FleshyWine();
    }
}