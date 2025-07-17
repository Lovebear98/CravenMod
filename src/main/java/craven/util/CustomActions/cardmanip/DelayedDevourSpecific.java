package craven.util.CustomActions.cardmanip;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import craven.patches.interfaces.OnDevouredInterface;
import craven.patches.interfaces.SecondsInterface;

import static craven.util.otherutil.MechanicManager.DevourInternal;
import static craven.util.otherutil.variables.Variables.p;

public class DelayedDevourSpecific extends AbstractGameEffect {
    private final AbstractCard c;

    public DelayedDevourSpecific(AbstractCard cx) {
        this.c = cx;
    }

    @Override
    public void update() {
        if(c == null || p().exhaustPile.isEmpty()){
            this.isDone = true;
        }
        DevourInternal(c);

        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
