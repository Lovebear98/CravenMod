package craven.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import craven.character.CravenCharacter;

import static craven.CravenMod.makeID;
import static craven.util.CustomTags.Food;
import static craven.util.otherutil.variables.Variables.p;

public class GiggleJuice extends AtlasRelic {

    public static final int StartingEnergy = 3;

    private static final String NAME = GiggleJuice.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.SOLID;
    private static final int DrawAmount = 1;

    public GiggleJuice() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        fixDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        if(card.hasTag(Food)){
            this.flash();
            addToBot(new DrawCardAction(DrawAmount));
        }
    }

    @Override
    public boolean canSpawn() {
        if(p() != null){
            return p().hasRelic(GoldpawVintage.ID);
        }
        return super.canSpawn();
    }

    @Override
    public void obtain() {
        // Replace the starter relic, or just give the relic if starter isn't found
        if (AbstractDungeon.player.hasRelic(GoldpawVintage.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(GoldpawVintage.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void instantObtain() {
        super.instantObtain();
        getUpdatedDescription();
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        super.instantObtain(p, slot, callOnEquip);
        getUpdatedDescription();
    }

    @Override
    public String getUpdatedDescription() {
        String s = DESCRIPTIONS[0] + DrawAmount + DESCRIPTIONS[1];
        if(DrawAmount != 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3];
        return s;
    }
}
