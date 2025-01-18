package coyotle.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import coyotle.character.CoyotleCharacter;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.variables.Variables.p;

public class MemoryoftheDream extends CoyotleRelic {
    ///This relic doesn't do anything here. It's checked for in the ShardManager.

    private static final String NAME = MemoryoftheDream.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.MAGICAL;
    private static final int SeekNum = 1;
    private static final int DiscardNum = 2;

    public MemoryoftheDream() {
        super(ID, NAME, CoyotleCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        if (GameActionManager.turn == 1){
            addToBot(new RelicAboveCreatureAction(p(), this));
            addToBot(new BetterDrawPileToHandAction(SeekNum));
        }
        if (GameActionManager.turn == 2){
            addToBot(new RelicAboveCreatureAction(p(), this));
            addToBot(new DiscardAction(p(), p(), DiscardNum, true));
            usedUp();
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        unusedUp();
    }
    @Override
    public void onVictory() {
        super.onVictory();
        unusedUp();
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
        return DESCRIPTIONS[0]+SeekNum+Cards(SeekNum)+DESCRIPTIONS[1]+DiscardNum+DESCRIPTIONS[2]+Cards(DiscardNum)+DESCRIPTIONS[5];
    }

    private String Cards(int i){
        String s = DESCRIPTIONS[3];
        if(i != 1){
            s += DESCRIPTIONS[4];
        }
        return s;
    }
}
