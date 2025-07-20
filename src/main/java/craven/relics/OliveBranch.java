package craven.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;

public class OliveBranch extends AtlasRelic {

    private static final String NAME = OliveBranch.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.FLAT;
    private static final int MaxNum = 7;

    public OliveBranch() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = MaxNum;
        fixDescription();
    }


    @Override
    public void onExhaust(AbstractCard card) {
        tickCounter();
        super.onExhaust(card);
    }

    private void tickCounter() {
        this.counter -= 1;
        if(this.counter <= 0){
            this.flash();
            this.counter = MaxNum;
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new MakeTempCardInDiscardAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), 1));
        }
        fixDescription();
    }

    @Override
    public String getUpdatedDescription() {
        String s = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
        if(counter != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        return s;
    }
}
