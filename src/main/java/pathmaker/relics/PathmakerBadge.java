package pathmaker.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;
import pathmaker.character.PathmakerCharacter;
import pathmaker.patches.interfaces.BloomInterface;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.BloomManager.Growth;
import static pathmaker.util.otherutil.variables.Variables.p;

public class PathmakerBadge extends BaseRelic implements BloomInterface {

    private static final String NAME = PathmakerBadge.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public PathmakerBadge() {
        super(ID, NAME, PathmakerCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    private static final int amount = 6;


    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if(usedUp){
            unusedUp();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if(usedUp){
            unusedUp();
        }
    }

    @Override
    public void OnBloom(AbstractCard c) {
        BloomInterface.super.OnBloom(c);
        if(!this.usedUp){
            addToBot(new RelicAboveCreatureAction(p(), this));
            Growth(amount);
            usedUp();
        }
    }

    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
