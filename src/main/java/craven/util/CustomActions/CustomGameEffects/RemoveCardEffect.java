package craven.util.CustomActions.CustomGameEffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Objects;

import static craven.util.otherutil.variables.Variables.p;

public class RemoveCardEffect extends AbstractGameEffect {
    private String targetID;
    private AbstractCard targetCard;

    public RemoveCardEffect(String id) {
        this.targetID = id;
    }
    public RemoveCardEffect(AbstractCard card) {
        this.targetCard = card;
    }


    @Override
    public void update() {
        if(this.targetID != null){
            for(AbstractCard c : p().masterDeck.group){
                if(Objects.equals(c.cardID, targetID)){
                    AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    p().masterDeck.removeCard(c);
                    break;
                }
            }
        }else if(this.targetCard != null){
            for(AbstractCard c : p().masterDeck.group){
                if(c == targetCard){
                    AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    p().masterDeck.removeCard(c);
                    break;
                }
            }
        }
        this.isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
