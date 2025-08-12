package craven.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;

public class SeedOfChange extends AtlasRelic {

    private static final String NAME = SeedOfChange.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.MAGICAL;
    public static final int MiracleAmount = 1;

    public SeedOfChange() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        AbstractCard c = new Miracle();
        c.upgrade();
        this.tips.add(new CardPowerTip(c));
        fixDescription();
    }

    @Override
    public void atTurnStart() {
        this.flash();
        AbstractCard c = new Miracle();
        c.upgrade();
        addToBot(new MakeTempCardInHandAction(c, MiracleAmount));
        super.atTurnStart();
    }

    @Override
    public String getUpdatedDescription() {
        String s = DESCRIPTIONS[0] + MiracleAmount + DESCRIPTIONS[1];
        if(MiracleAmount != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        return s;
    }
}
