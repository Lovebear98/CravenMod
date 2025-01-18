package coyotle.cards.generated.Mechanics;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.AbstractShardCard;
import coyotle.character.CoyotleCharacter;
import coyotle.util.CardStats;
@NoCompendium
public class HowlingBluegrass extends AbstractShardCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(HowlingBluegrass.class.getSimpleName());

    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;


    public HowlingBluegrass() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setShards(1);
        FixDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.FixDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractShardCard c = new HowlingBluegrass();
        c.BloodNum = BloodNum;
        c.DiamondNum = DiamondNum;
        c.RubyNum = RubyNum;
        c.SapphireNum = SapphireNum;
        c.WildNum = WildNum;

        c.Hitcount = Hitcount;
        c.BlockCount = BlockCount;

        c.XCost = XCost;
        c.Continue = Continue;

        c.HitAll = HitAll;
        c.Random = Random;
        c.Vuln = Vuln;
        c.VulnAll = VulnAll;
        c.Vigor = Vigor;

        c.BlockNext = BlockNext;
        c.Thorns = Thorns;
        c.Weak = Weak;
        c.WeakAll = WeakAll;
        c.Blur = Blur;

        c.Draw = Draw;
        c.Energy = Energy;
        c.Mill = Mill;

        c.ValorDraw = ValorDraw;
        c.ValorHand = ValorHand;

        c.NormalBlock = NormalBlock;

        return c;
    }
}