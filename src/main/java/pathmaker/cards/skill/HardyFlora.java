package pathmaker.cards.skill;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cards.AbstractBloomCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.orbs.AttackFlower;
import pathmaker.orbs.SkillFlower;
import pathmaker.util.CardStats;

import static pathmaker.util.CustomTags.Bloom;
import static pathmaker.util.otherutil.BloomManager.BloomReady;

public class HardyFlora extends AbstractBloomCard {

    public static final String ID = makeID(HardyFlora.class.getSimpleName());
    private static final CardStats info = new CardStats(
            PathmakerCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;
    private boolean ApplyBloom = false;


    public HardyFlora() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(Bloom);

        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!ApplyBloom){
            for(int i = magicNumber; i > 0; i -= 1){
                addToBot(new ChannelAction(new AttackFlower()));
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
            for(int i = magicNumber; i > 0; i -= 1){
                addToBot(new ChannelAction(new SkillFlower()));
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
        return new HardyFlora();
    }
}