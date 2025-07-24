package craven.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import craven.cards.AbstractHungryCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.OnDevouredInterface;
import craven.util.CardStats;
import craven.util.CustomActions.IncreaseRiskAction;
import craven.util.CustomActions.NibbleAction;
import craven.util.CustomActions.generic.BulkDevourAction;
import craven.util.CustomActions.generic.BulkExhaustAction;

import java.util.ArrayList;

import static craven.util.otherutil.ConfigManager.EnableEarCandy;
import static craven.util.otherutil.MechanicManager.Risk;
import static craven.util.otherutil.MechanicManager.TrueRiskCap;
import static craven.util.otherutil.variables.UIText.ExhaustText;
import static craven.util.otherutil.variables.Variables.p;

public class Caffeine extends AbstractHungryCard implements OnDevouredInterface {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(Caffeine.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CravenCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final int SECOND_MAGIC = 3;
    private static final int UPG_SECOND_MAGIC = -2;


    public Caffeine() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        PurgeField.purge.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber, ExhaustText(), true, true, card -> card != this, selectedcards -> {
            int i = 0;
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            for(AbstractCard c: selectedcards){
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
                addToBot(new BulkDevourAction(tmp));
            }
            if(i > 0){
                if(upgraded){
                    addToBot(new ApplyPowerAction(p, p, new RegenPower(p, i * secondMagic)));
                }else{
                    addToBot(new HealAction(p, p, i * secondMagic));
                }
            }
        }));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Caffeine();
    }

    @Override
    public void PostDevoured() {
    }
}