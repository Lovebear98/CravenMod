package coyotle.cards.starter;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.AbstractDreamingCard;
import coyotle.cardsmods.StarfireTotemistMod;
import coyotle.character.CoyotleCharacter;
import coyotle.util.CardStats;
import coyotle.util.CustomActions.MillAction;
import coyotle.util.MyAttackEffects;

import static coyotle.util.otherutil.DreamManager.inDream;

public class StarfireTotemist extends AbstractDreamingCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(StarfireTotemist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 1;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public StarfireTotemist(){
        this(true);
    }
    public StarfireTotemist(boolean makePreview) {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setDreamType(CardType.ATTACK);
        setDreamTarget(CardTarget.ENEMY);

        CardModifierManager.addModifier(this, new StarfireTotemistMod());

        if(makePreview){
            AbstractDreamingCard c = new StarfireTotemist(false);
            c.onEnterDream();
            this.cardsToPreview = c;
        }

        if(inDream){
            onEnterDream();
        }

    }

    @Override
    protected void standardUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), MyAttackEffects.Galaxy));
        addToBot(new MillAction(magicNumber));
    }

    @Override
    protected void dreamUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), MyAttackEffects.Galaxy));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new StarfireTotemist();
    }
}