package coyotle.cards.generated;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.AbstractProphecyCard;
import coyotle.cardsmods.ValorCardMod;
import coyotle.character.CoyotleCharacter;
import coyotle.util.CardStats;

import static coyotle.util.CustomTags.Valorous;
import static coyotle.util.otherutil.ValorManager.*;

@NoCompendium
public class Valor extends AbstractProphecyCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Valor.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 1;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Valor() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        
        tags.add(Valorous);
        setExhaust(true);

        CardModifierManager.addModifier(this, new ValorCardMod());
        if(ValorsPlayed >= ValorUpgradeThreshold){
            this.upgrade();
        }
        FixDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), ValorEffect()));
        if(ValorsPlayed >= ValorDrawThreshold){
            addToBot(new DrawCardAction(magicNumber));
        }
        if(ValorsPlayed >= ValorBlockThreshold){
            addToBot(new GainBlockAction(p, block));
        }
    }

    private static AbstractGameAction.AttackEffect ValorEffect() {
        if(ValorsPlayed < 5){
            return AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        }
        if(ValorsPlayed < 25){
            int i = MathUtils.random(1, 3);
            if(i == 1){
                return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
            }
            if(i == 2){
                return AbstractGameAction.AttackEffect.SLASH_VERTICAL;
            }
            if(i == 3){
                return AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
            }
        }
        return AbstractGameAction.AttackEffect.SLASH_HEAVY;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        FixDescription();
    }

    private void FixDescription() {
        String s = cardStrings.EXTENDED_DESCRIPTION[0];
        if(ValorsPlayed >= ValorDrawThreshold){
            s += cardStrings.EXTENDED_DESCRIPTION[1];
        }
        if(ValorsPlayed >= ValorBlockThreshold){
            s += cardStrings.EXTENDED_DESCRIPTION[2];
        }
        s += cardStrings.EXTENDED_DESCRIPTION[3];
        this.rawDescription = s;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Valor();
    }
}