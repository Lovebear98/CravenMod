package craven.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.OnDevouredInterface;
import craven.patches.visual.AttackEffectEnum;
import craven.util.CardStats;
import craven.util.CustomActions.NibbleAction;
import craven.util.CustomActions.generic.BulkExhaustAction;

import java.util.ArrayList;

import static craven.util.otherutil.variables.UIText.ExhaustText;
import static craven.util.otherutil.variables.Variables.p;

public class Shot extends AbstractHungryCard implements OnDevouredInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Shot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public Shot() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AttackEffectEnum.WINE));
        addToBot(new SelectCardsInHandAction(magicNumber, ExhaustText(), false, false, card -> card != this, selectedcards -> {

            ArrayList<AbstractCard> tmp = new ArrayList<>();
            int i = 0;

            for(AbstractCard c: selectedcards){
                tmp.add(c);
                i += 1;
            }

            if(!tmp.isEmpty()){
                addToTop(new BulkExhaustAction(tmp));
            }
            if(i > 0){
                for(int e = i; e > 0; e -= 1){
                    addToBot(new GainBlockAction(p, block));
                }
            }
        }));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Shot();
    }

    @Override
    public void PostDevoured() {
    }
}