package pathmaker.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.util.CardStats;

import static pathmaker.util.CustomTags.Bloom;
import static pathmaker.util.otherutil.BloomManager.BloomReady;
import static pathmaker.util.otherutil.variables.Variables.p;

public class GazeoftheFalcon extends AbstractBloomCard {

    public static final String ID = makeID(GazeoftheFalcon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;
    private boolean ApplyBloom = false;


    public GazeoftheFalcon() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(Bloom);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!ApplyBloom){
            for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false)));
            }
        }
    }

    @Override
    public void onRetained() {
        super.onRetained();
    }

    @Override
    public void OnBloom(AbstractCard c) {
        if(ApplyBloom){
            for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                addToBot(new ApplyPowerAction(mo, p(), new VulnerablePower(mo, magicNumber, false)));
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        ApplyBloom = BloomReady();
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new GazeoftheFalcon();
    }
}