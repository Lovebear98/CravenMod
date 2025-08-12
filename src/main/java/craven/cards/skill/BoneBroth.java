package craven.cards.skill;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractSecondsCard;
import craven.character.CravenCharacter;
import craven.powers.custompowers.RavenousPower;
import craven.util.CardStats;
import craven.util.CustomActions.imported.DrawCallbackAction;

import java.util.Objects;

import static craven.util.otherutil.variables.Variables.p;

public class BoneBroth extends AbstractSecondsCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(BoneBroth.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 1;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 2;
    private static final int UPG_SECOND_MAGIC = 0;


    public BoneBroth() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setExhaust(true);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(secondMagic));
        addToBot(new DrawCallbackAction(secondMagic, Objects::nonNull, cards -> {
            if(p.hasPower(RavenousPower.POWER_ID)){
                for(AbstractCard c: cards){
                    c.freeToPlayOnce = true;
                }
            }
        }));
    }



    @Override
    public int BaseSecondsCount() {
        return magicNumber;
    }

    @Override
    public int SecondsCount() {
        return Math.max(BaseSecondsCount() - SecondsUsed, 0);
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
    public AbstractCard makeCopy() {
        BoneBroth c = new BoneBroth();
        c.SecondsUpgraded = this.SecondsUpgraded;
        return c;
    }
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        BoneBroth c = (BoneBroth) super.makeStatEquivalentCopy();
        c.SecondsUsed = this.SecondsUsed;
        return c;
    }

    @Override
    public void upgrade() {
        this.SecondsUpgraded = true;
        super.upgrade();
    }

    @Override
    public boolean SecondsUpgraded() {
        return super.SecondsUpgraded();
    }

}