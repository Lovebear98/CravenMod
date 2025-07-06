package craven.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import craven.util.CustomActions.CustomGameEffects.vfx.HungryAuraEffect;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class HungryStance extends AbstractStance {
    public static final String STANCE_ID = makeID(HungryStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static long sfxId = -1L;

    public int Count = 0;

    public HungryStance() {
        this(1);
    }
    public HungryStance(int i){
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
        this.Count = i;
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new HungryAuraEffect());
        }
    }

    public void onEnterStance() {
        if (sfxId != -1L) {
            this.stopIdleSfx();
        }


        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED.cpy(), true));
        updateDescription();
    }

    public void onExitStance() {
        this.stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }

    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
    }

    public void tickDown(int i){
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                Count -= i;
                if(Count <= 0){
                    this.addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p().hb.cX, p().hb.cY), 0.1F)));
                    this.addToBot(new ChangeStanceAction("Neutral"));
                }
                isDone = true;
            }
        });
    }

    public void updateDescription() {
        String s = stanceString.DESCRIPTION[0] + Count + stanceString.DESCRIPTION[1];
        if(Count != 1){
            s += stanceString.DESCRIPTION[2];
        }
        s += stanceString.DESCRIPTION[3];
        this.description = s;
    }
}