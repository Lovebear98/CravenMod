package pathmaker.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathmaker.cards.status.GrowthCard;
import pathmaker.powers.custompowers.PathoftheCrocodilePower;

import static pathmaker.util.otherutil.BloomManager.AttemptBloom;
import static pathmaker.util.otherutil.BloomManager.GrowthMode;
import static pathmaker.util.otherutil.variables.Variables.p;

public class GrowthAction extends AbstractGameAction {

    boolean FirstPass = true;
    public GrowthAction() {
        ///This is an artificial bloom, so we should ALWAYS be able to get it
        GrowthMode = true;
    }

    @Override
    public void update() {
        ///Our default 0-cost 0-everything card for Growth
        AbstractCard c = new GrowthCard();
        ///Modify the card's type if we have the crocodile power
        if(p().hasPower(PathoftheCrocodilePower.POWER_ID)){
            AbstractPower pow = p().getPower(PathoftheCrocodilePower.POWER_ID);
            if(pow instanceof PathoftheCrocodilePower){
                c.type = ((PathoftheCrocodilePower) pow).GrowType;
            }
        }



        ///Our first time through, do this
        if(FirstPass){
            //This one happens first by default by not being queue'd in an action
            p().hand.triggerOnOtherCardPlayed(c);

            FirstPass = false;
            ///Otherwise
        }else{
            //Do the rest!

            ///This action's constructor actually does everything we need, so we just need to construct it
                UseCardAction FillerAction = new UseCardAction(c);

            ///And after we've done all the normal stuff, THEN we call our blooms
            AttemptBloom(c);
            isDone = true;
        }
    }
}
