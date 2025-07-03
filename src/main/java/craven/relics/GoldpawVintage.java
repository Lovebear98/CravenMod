package craven.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class GoldpawVintage extends AtlasRelic {
    ///This relic's RiskBonus is checked for in the MechanicsManager.

    public static final int DrawOnExhaust = 1;

    private static final String NAME = GoldpawVintage.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.SOLID;
    public GoldpawVintage() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }


    @Override
    public void atTurnStart() {
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.unusedUp();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.unusedUp();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        if(!usedUp){
            this.usedUp();
            addToBot(new RelicAboveCreatureAction(p(), this));
            addToBot(new DrawCardAction(DrawOnExhaust));
        }
    }

    @Override
    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;
    }

    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
    }

    @Override
    public String getUpdatedDescription() {
        String s = DESCRIPTIONS[0] + DrawOnExhaust + DESCRIPTIONS[1];
        if(DrawOnExhaust != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        return s;
    }
}
