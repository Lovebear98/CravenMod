package craven.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.powers.custompowers.BrazenPower;
import craven.powers.custompowers.RavenousPower;
import craven.util.CardStats;

import static craven.util.otherutil.variables.Variables.p;

public class Brazen extends AbstractHungryCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Brazen.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            4
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Brazen() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        setSelfRetain(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p().hasPower(BrazenPower.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new BrazenPower(p)));
        }else{
            if(AbstractDungeon.player.hasPower(RavenousPower.POWER_ID)){
                RavenousPower pow = (RavenousPower) p().getPower(RavenousPower.POWER_ID);
                pow.Trigger(false);
            }
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Brazen();
    }
}