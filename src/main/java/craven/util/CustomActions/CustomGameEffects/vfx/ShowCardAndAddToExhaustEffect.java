package craven.util.CustomActions.CustomGameEffects.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;



public class ShowCardAndAddToExhaustEffect extends AbstractGameEffect {
    public static final float ModEXHAUST_PILE_X = 1850.0F * Settings.xScale;
    public static final float ModEXHAUST_PILE_Y = 180.0F * Settings.yScale + (AbstractCard.IMG_HEIGHT * 0.65f);
    private static final int HeightCap = 8;
    private AbstractCard card;
    private static final float PADDING;

    private static float actiondur = 0.8F;

    public ShowCardAndAddToExhaustEffect(AbstractCard card) {
        this.card = card;
        this.duration = actiondur;
        this.identifySpawnLocation((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01F;
        card.targetDrawScale = 0.75F;
        if (card.type != CardType.CURSE && card.type != CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            card.upgrade();
        }

        AbstractDungeon.player.exhaustPile.addToTop(card);
    }

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        int LeftMoves = 0;

        ///Get the number of this effect going on, and count how many
        for (AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof ShowCardAndAddToExhaustEffect) {
                ++effectCount;
            }
        }

        this.card.target_y = (float)Settings.HEIGHT * 0.5F;
        switch (effectCount) {
            case 0:
                this.card.target_x = (float)Settings.WIDTH * 0.5F;
                break;
            case 1:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
                break;
            case 2:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
                break;
            case 3:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            case 4:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;// 74
            default:
                this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
                this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
        }
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
            AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(this.card));
            if (AbstractDungeon.player.limbo.contains(this.card)) {
                AbstractDungeon.player.limbo.removeCard(this.card);
            }

            AbstractDungeon.player.cardInUse = null;

        }
        if(this.duration <= this.startingDuration * 0.5f){
            this.card.target_y = Interpolation.bounce.apply(this.card.target_y, ModEXHAUST_PILE_Y, duration);
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }

    }

    public void dispose() {
    }

    static {
        PADDING = 30.0F * Settings.scale;
    }
}
