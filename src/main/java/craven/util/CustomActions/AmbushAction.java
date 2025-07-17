package craven.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Iterator;

import static craven.util.otherutil.variables.Variables.p;

public class AmbushAction extends AbstractGameAction {
    private final DamageInfo info;
    private final float startingDuration;
    int ExhaustedCards = 0;


    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public AmbushAction(AbstractCreature source, AbstractCreature target, DamageInfo info){
        this(source, target, info, 1);
    }
    public AmbushAction(AbstractCreature source, AbstractCreature target, DamageInfo info, int amount) {
        this.source = source;
        this.target = target;
        this.info = info;

        this.amount = amount;

        this.actionType = ActionType.DAMAGE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    @Override
    public void update() {

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {

            /// If this just started
            if (this.duration == this.startingDuration) {

                /// End the action immediately if we have no cards in hand
                if (AbstractDungeon.player.hand.isEmpty()) {
                    ///                 this.target.damage(this.info);
                   ///                 if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                   ///                     AbstractDungeon.actionManager.clearPostCombatActions();
                   ///                 } else {
                   ///                     this.addToTop(new WaitAction(0.1F));
                   ///                 }
                    this.isDone = true;
                }

                /// Otherwise, open a hand select screen  to pick 1 card
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);

                /// But if it's not the start and our selected cards aren't empty
            } else if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                /// Get the first selected card and yeet it
                AbstractCard c2 = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
                AbstractDungeon.player.drawPile.moveToExhaustPile(c2);
                /// And note that we did indeed exhaust cards
                ExhaustedCards += 1;
                AbstractDungeon.handCardSelectScreen.selectedCards.removeCard(c2);
            }

            this.tickDuration();

            if (this.isDone) {
                if(ExhaustedCards > 0){
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY, false));
                }else{
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_LIGHT, false));
                }
                this.target.damage(this.info);
                if (this.target.lastDamageTaken > 0) {
                    if(ExhaustedCards > 0){
                        this.addToTop(new GainBlockAction(this.source, this.target.lastDamageTaken * ExhaustedCards));
                        if (this.target.hb != null) {
                            this.addToTop(new VFXAction(new WallopEffect(this.target.lastDamageTaken, this.target.hb.cX, this.target.hb.cY)));
                        }
                    }
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                } else {
                    this.addToTop(new WaitAction(0.1F));
                }
            }
        }
    }
}
