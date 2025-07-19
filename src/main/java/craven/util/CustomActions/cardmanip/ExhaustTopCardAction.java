package craven.util.CustomActions.cardmanip;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static craven.util.otherutil.variables.Variables.p;

public class ExhaustTopCardAction extends AbstractGameAction {
    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.actions.common.DrawCardAction.class.getName());
    public static ArrayList<AbstractCard> drawnCards = new ArrayList();
    private AbstractGameAction followUpAction;

    public ExhaustTopCardAction(AbstractCreature source, int amount) {
        this.shuffleCheck = false;
        this.followUpAction = null;

        this.setValues(AbstractDungeon.player, source, amount);

        this.actionType = ActionType.EXHAUST;

        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }

    }

    public ExhaustTopCardAction(AbstractCreature source) {
        this(source, 1);
    }

    public ExhaustTopCardAction(int amount) {
        this(p(), amount);
    }

    public ExhaustTopCardAction(int amount, AbstractGameAction action) {
        this(amount);
        this.followUpAction = action;
    }// 70

    public void update() {
        if (this.amount <= 0) {
            this.endActionWithFollowUp();// 86
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.endActionWithFollowUp();
                } else if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.endActionWithFollowUp();
                } else {
                    if (!this.shuffleCheck) {
                        int tmp;
                        if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
                            tmp = BaseMod.MAX_HAND_SIZE - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += tmp;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }

                        if (this.amount > deckSize) {
                            tmp = this.amount - deckSize;
                            this.addToTop(new ExhaustTopCardAction(tmp, this.followUpAction));
                            this.addToTop(new EmptyDeckShuffleAction());
                            if (deckSize != 0) {
                                this.addToTop(new ExhaustTopCardAction(deckSize));
                            }

                            this.amount = 0;
                            this.isDone = true;
                            return;
                        }

                        this.shuffleCheck = true;
                    }

                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F) {
                        if (Settings.FAST_MODE) {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        } else {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }

                        --this.amount;
                        if (!AbstractDungeon.player.drawPile.isEmpty()) {
                            p().drawPile.moveToExhaustPile(p().drawPile.getTopCard());
                            AbstractDungeon.player.hand.refreshHandLayout();
                        } else {
                            logger.warn("Player attempted to exhaust from an empty drawpile mid-ExhaustAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            this.endActionWithFollowUp();
                        }

                        if (this.amount == 0) {
                            this.endActionWithFollowUp();
                        }
                    }

                }
            }
        }
    }

    private void endActionWithFollowUp() {
        this.isDone = true;
        if (this.followUpAction != null) {
            this.addToTop(this.followUpAction);
        }

    }
}
