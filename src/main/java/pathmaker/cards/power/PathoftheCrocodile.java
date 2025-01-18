package pathmaker.cards.power;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.cards.generated.Choice.*;
import pathmaker.character.PathmakerCharacter;
import pathmaker.powers.custompowers.PathoftheCrocodilePower;
import pathmaker.util.CardStats;

import java.util.ArrayList;

public class PathoftheCrocodile extends AbstractBloomCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(PathoftheCrocodile.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public PathoftheCrocodile() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        CardType check = null;
        AbstractCard c;

        if(p.hasPower(PathoftheCrocodilePower.POWER_ID)){
            AbstractPower pow = p.getPower(PathoftheCrocodilePower.POWER_ID);
            if(pow instanceof PathoftheCrocodilePower){
                check = ((PathoftheCrocodilePower) pow).GrowType;
            }
        }

        if(check == null || check == CardType.ATTACK){
            c = new CrocPathAttack();
            ConditionalUpgrade(c);
            FixMagicNumber(c);
            list.add(c);
        }

        if(check == null || check == CardType.SKILL){
            c = new CrocPathSkill();
            ConditionalUpgrade(c);
            FixMagicNumber(c);
            list.add(c);
        }

        if(check == null || check == CardType.POWER){
            c = new CrocPathPower();
            ConditionalUpgrade(c);
            FixMagicNumber(c);
            list.add(c);
        }
        if(upgraded){
            if(check != null && check != CardType.STATUS){
                c = new CrocPathStatus();
                ConditionalUpgrade(c);
                FixMagicNumber(c);
                list.add(c);
            }
            if(check == null || check == CardType.CURSE){
                c = new CrocPathCurse();
                ConditionalUpgrade(c);
                FixMagicNumber(c);
                list.add(c);
            }
        }
        addToBot(new ChooseOneAction(list));
    }

    private void FixMagicNumber(AbstractCard c) {
        c.magicNumber = this.magicNumber;
    }

    private void ConditionalUpgrade(AbstractCard c) {
        if(this.upgraded){
            c.upgrade();
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
    }

    @Override
    public void OnBloom(AbstractCard c) {
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PathoftheCrocodile();
    }
}