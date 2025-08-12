package craven.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.visual.AttackEffectEnum;
import craven.util.CardStats;
import craven.util.CustomActions.generic.BulkDevourAction;

import java.util.ArrayList;

import static craven.util.otherutil.ConfigManager.EnableEarCandy;
import static craven.util.otherutil.variables.UIText.DevourText;

public class Banquet extends AbstractHungryCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Banquet.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 4;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Banquet() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(999, DevourText(), false, false, card -> card != this, selectedcards -> {
            int i = 0;
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            for (AbstractCard c : selectedcards) {
                tmp.add(c);
                i += 1;
            }
            if(!tmp.isEmpty()){
                if(EnableEarCandy){
                    addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
                            isDone = true;
                        }
                    });
                }
                addToTop(new BulkDevourAction(tmp));
                for(int e = i; e > 0; e -= 1){
                    addToBot(new AttackDamageRandomEnemyAction(this, AttackEffectEnum.WINE));
                }
            }
        }));

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Banquet();
    }
}