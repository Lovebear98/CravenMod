package craven.cards.attack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import craven.cards.AbstractHungryCard;
import craven.cardsmods.RavenousDamageMod;
import craven.character.CravenCharacter;
import craven.patches.interfaces.CravingInterface;
import craven.util.CardStats;
import craven.util.CustomActions.ConsumeAction;

public class Consume extends AbstractHungryCard implements CravingInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Consume.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 9;
    private static final int UPG_MAGIC = 5;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Consume() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(CardTags.HEALING);

        CardModifierManager.addModifier(this, new RavenousDamageMod());
        setPurge(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ///         AbstractDungeon.actionManager.addToBottom(new VFXAction(new SavageryEffect(m), 0.1f));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.RED_TEXT_COLOR.cpy()), 0.1F));
        } else {
            this.addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.RED_TEXT_COLOR.cpy()), 0.2F));
        }
        addToBot(new ConsumeAction(m, new DamageInfo(p, damage, damageType), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Consume();
    }

    @Override
    public int CravingBonus() {
        return magicNumber;
    }
}