package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static craven.util.otherutil.ConfigManager.EnableEarCandy;
import static craven.util.otherutil.MechanicManager.DevourInternal;

public class NibbleAction extends AbstractGameAction {
    private final AbstractCard c;

    public NibbleAction(AbstractCard cx) {
        this.c = cx;
    }

    @Override
    public void update() {
        if(c == null){
            this.isDone = true;
        }
        if(EnableEarCandy){
            CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
        }
        DevourInternal(c);

        isDone = true;
    }
}
