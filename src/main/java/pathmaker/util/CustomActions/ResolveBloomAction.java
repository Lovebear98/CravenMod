package pathmaker.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import pathmaker.patches.interfaces.BloomInterface;
import pathmaker.util.CustomActions.CustomGameEffects.FlowerSproutEffect;

import static pathmaker.util.CustomTags.Bloom;
import static pathmaker.util.otherutil.SoundManager.PlaySound;

public class ResolveBloomAction extends AbstractGameAction {

    AbstractCard c;
    public ResolveBloomAction(AbstractCard c) {
    this.c = c;

        ///Start at 0
        int EnergyOutput = 0;
        ///If the card EXISTS
        if(c != null){
            ///If the card isn't free to play
            if(!c.freeToPlay()){
                ///If it costs more than 0
                if(c.costForTurn > 0){
                    ///We use the card's cost
                    EnergyOutput = c.costForTurn;
                    ///otherwise, if it's an X-Cost
                }else if(c.costForTurn == -1){
                    ///We use the energy spent on it
                    EnergyOutput = c.energyOnUse;
                }
            }
        }

        //Make the amount the energy we actually used
        this.amount = EnergyOutput;
    }

    @Override
    public void update() {
        ///If we actually used a card, trigger its Bloom
        if(c != null){
            if(c instanceof BloomInterface && c.hasTag(Bloom)) {
                ((BloomInterface) c).OnBloom(c);
            }
        }

        ///Trigger our appropriate relics
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof BloomInterface) {
                ((BloomInterface) r).OnBloom(c);
            }
        }

        ///Trigger our appropriate powers
        for(AbstractPower p: AbstractDungeon.player.powers){
            if(p instanceof BloomInterface){
                ((BloomInterface) p).OnBloom(c);
            }
        }

        ///If we spent energy or are considered to have spent energy, draw a card
        if(amount > 0){
            addToBot(new DrawCardAction(1));
        }
        ///Right before we're done, queue up the flower sprout FX
        AbstractDungeon.effectsQueue.add(new FlowerSproutEffect(amount > 0));
        this.isDone = true;
    }
}
