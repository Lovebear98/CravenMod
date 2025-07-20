package craven.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.util.CardStats;
import craven.util.CustomActions.AppetizerAction;
import craven.util.CustomActions.CustomGameEffects.DelayActionEffect;
import craven.util.CustomActions.imported.PhantomPlayCardAction;

public class Appetizer extends AbstractHungryCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Appetizer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            4
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public AbstractCard StoredCard = null;

    public Appetizer() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        UpgradePreviewCard = false;

        setCostUpgrade(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(StoredCard != null){
            Appetizer c2 = this;

            /// The issue is that the game wants to resolve the whole action queue, THEN the card queue, so
            /// we have to be weird and make an effect that queues an action after *just* long enough
            /// to let the card queue get priority back
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {

                    addToTop(new PhantomPlayCardAction(StoredCard.makeStatEquivalentCopy(), m));

                    StoredCard = null;
                    cardsToPreview = null;

                    AbstractDungeon.effectsQueue.add(new DelayActionEffect(new AppetizerAction(c2), 0.5f));

                    isDone = true;
                }
            });

        }else{
            addToBot(new AppetizerAction(this));
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Appetizer();
    }
}