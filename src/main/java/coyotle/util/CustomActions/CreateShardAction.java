package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static coyotle.ui.ProphecyPanel.FixPreviewInstantly;
import static coyotle.util.otherutil.ConfigManager.EnableEarCandy;
import static coyotle.util.otherutil.ShardManager.GetShardSound;
import static coyotle.util.otherutil.ShardManager.RandomShard;
import static coyotle.util.otherutil.SoundManager.PlaySound;
import static coyotle.util.otherutil.variables.Variables.p;

public class CreateShardAction extends AbstractGameAction {

    @Override
    public void update() {
        if(p() != null){
            AbstractCard c = RandomShard();
            p().drawPile.addToBottom(c);
            if(EnableEarCandy){
                PlaySound(GetShardSound(c));
            }
            FixPreviewInstantly = true;
        }
        isDone = true;
    }
}
