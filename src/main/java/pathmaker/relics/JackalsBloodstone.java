package pathmaker.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import pathmaker.character.PathmakerCharacter;
import pathmaker.patches.interfaces.OasisReachedInterface;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.variables.Variables.PrintEnergy;
import static pathmaker.util.otherutil.variables.Variables.p;

public class JackalsBloodstone extends BaseRelic implements OasisReachedInterface {

    private static final String NAME = JackalsBloodstone.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public JackalsBloodstone() {
        super(ID, NAME, PathmakerCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    private static final int amount = 2;

    @Override
    public void OnOasisReached(AbstractCard c) {
        OasisReachedInterface.super.OnOasisReached(c);
        addToBot(new RelicAboveCreatureAction(p(), this));
        addToBot(new ApplyPowerAction(p(),p(), new StrengthPower(p(), amount)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
