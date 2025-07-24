package craven.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.CravingInterface;
import craven.patches.visual.AttackEffectEnum;
import craven.util.CardStats;
import craven.util.CustomActions.generic.BulkDevourAction;
import craven.util.CustomActions.generic.BulkExhaustAction;

import java.util.ArrayList;

import static craven.util.otherutil.ConfigManager.EnableEarCandy;
import static craven.util.otherutil.variables.UIText.DevourText;
import static craven.util.otherutil.variables.UIText.ExhaustText;

public class Silverware extends AbstractHungryCard implements CravingInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Silverware.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Silverware() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        this.cardsToPreview = new Shiv();
        UpgradePreviewCard = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AttackEffectEnum.GILDED));
        addToBot(new SelectCardsInHandAction(999, ExhaustText(), true, true, card -> card != this, selectedcards -> {
            int i = 0;
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            for (AbstractCard c : selectedcards) {
                tmp.add(c);
                i += 1;
            }
            if(!tmp.isEmpty()){
                addToTop(new BulkExhaustAction(tmp));
            }
            if(i > 0){
                AbstractCard c = new Shiv();
                if(this.upgraded){
                    c.upgrade();
                }
                addToBot(new MakeTempCardInHandAction(c, i));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Silverware();
    }

    @Override
    public int CravingBonus() {
        return magicNumber;
    }
}