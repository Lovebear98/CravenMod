package pathmaker.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import pathmaker.character.PathmakerCharacter;
import pathmaker.patches.interfaces.BloomInterface;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.BloomManager.Growth;
import static pathmaker.util.otherutil.variables.Variables.PrintEnergy;
import static pathmaker.util.otherutil.variables.Variables.p;

public class CrocodilesJade extends BaseRelic {

    private static final String NAME = CrocodilesJade.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public CrocodilesJade() {
        super(ID, NAME, PathmakerCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    private static final int amount = 2;

    private static boolean SKILL = false;
    private static boolean POWER = false;
    private static boolean ATTACK = false;

    public void atTurnStart() {
        SKILL = false;
        POWER = false;
        ATTACK = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ATTACK = true;
        } else if (card.type == AbstractCard.CardType.SKILL) {
            SKILL = true;
        } else if (card.type == AbstractCard.CardType.POWER) {
            POWER = true;
        }

        if (ATTACK && SKILL && POWER) {
            addToBot(new RelicAboveCreatureAction(p(), this));
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(amount));
            addToBot(new GainEnergyAction(amount));
            SKILL = false;
            POWER = false;
            ATTACK = false;
        }

    }


    @Override
    public String getUpdatedDescription() {
        String s = "";
        s = DESCRIPTIONS[0] + PrintEnergy(amount) + DESCRIPTIONS[1]+amount+DESCRIPTIONS[2];
        if(amount > 1){
            s += DESCRIPTIONS[3];
        }
        s += DESCRIPTIONS[4];

        return s;
    }
}
