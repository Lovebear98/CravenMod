package craven.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import craven.character.CravenCharacter;
import craven.patches.interfaces.CravingInterface;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.PrintEnergy;

public class PrimeFrostruffle extends AtlasRelic implements CravingInterface {

    private static final String NAME = PrimeFrostruffle.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int XEnergyAmt = 2;

    public PrimeFrostruffle() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        fixDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if(card != null){
            if(card.cost == -1){
                this.flash();
                addToBot(new GainEnergyAction(XEnergyAmt));
            }
        }
    }



    @Override
    public String getUpdatedDescription() {
        String s = DESCRIPTIONS[0];
        if(XEnergyAmt >= 4){
            s += DESCRIPTIONS[1] + XEnergyAmt + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }else{
            s += PrintEnergy(XEnergyAmt)  + DESCRIPTIONS[3];
        }
        return s;
    }

    @Override
    public int CravingBonus() {
        return 0;
    }
}
