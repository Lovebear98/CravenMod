package craven.util.otherutil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import craven.cards.skill.SetTheTable;
import craven.cards.starter.DinnerBell;
import craven.character.CravenCharacter;
import craven.powers.custompowers.RavenousPower;
import craven.util.CustomActions.generic.BulkDevourAction;

import java.util.ArrayList;

import static craven.util.CustomActions.CustomGameEffects.vfx.ShowCardAndAddToExhaustEffect.ModEXHAUST_PILE_X;
import static craven.util.otherutil.ConfigManager.AllDevour;
import static craven.util.otherutil.ConfigManager.EnableEyeCandy;
import static craven.util.otherutil.MechanicManager.DevourCallback;
import static craven.util.otherutil.MechanicManager.ResetRisk;
import static craven.util.otherutil.variables.Variables.p;


public class Wiz {

    public static boolean DrawingStartingHand = false;

    public static final float BITE_Y = 180.0F * Settings.yScale - 40.0F * Settings.scale;

    /**
     * Hooks for things that happen before and after combat in one place
     */
    public static void PreCombat() {
        PrePostCombat();
    }

    public static void PostCombat() {
        PrePostCombat();
    }

    private static void PrePostCombat() {
        ResetRisk();
    }

    /**
     * Method for generally flashing cards
     */
    public static void FlashCard(AbstractCard card, Color color) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)) {
            AbstractDungeon.topLevelEffectsQueue.add(new CardFlashVfx(card, color));
        }
    }

    /**
     * Check if we're a "Devour Enabled" character or not.
     */
    public static boolean DevourEnabled() {
        if (AllDevour) {
            return true;
        }
        if(p() == null){
            return true;
        }
        return p() instanceof CravenCharacter;
    }

    /**
     * Section for placing the "Skip Optional" hotkey.
     */
    public static boolean CancelOptional() {
        if (p() != null) {
            if ((InputHelper.isMouseDown_R && !InputHelper.justClickedRight)) {
                return true;
            }
        }
        return false;
    }

    public static int swapKey = Input.Keys.Q;

    public int getKeyCode() {
        return swapKey;
    }

    public boolean cancelHotkeyPressed() {
        return Gdx.input.isKeyJustPressed(getKeyCode());
    }

    /**
     * Section for common, multi-use methods
     */
    public static void DevourCards(int e) {
        ArrayList<AbstractCard> tmp = new ArrayList<>();
        int z = Math.min(p().exhaustPile.size(), e);
        if (z > 0) {
            for (int i = 0; i < Math.min(z, p().exhaustPile.size()); i += 1) {
                tmp.add(AbstractDungeon.player.exhaustPile.group.get(i));
            }

            AbstractDungeon.actionManager.addToTop(new BulkDevourAction(tmp, p().exhaustPile));
            if(EnableEyeCandy){
                AbstractDungeon.actionManager.addToTop(new VFXAction(new BiteEffect(ModEXHAUST_PILE_X, BITE_Y, Settings.RED_TEXT_COLOR.cpy()), 0.01F));
            }
            DevourCallback(z);
        }
    }

    public static void GainRavenous(int i) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p(), p(), new RavenousPower(p(), i)));
    }

    private static int MaxTries = 300;
    public static AbstractCard GetTotallyRandomXCostCard(boolean inCombat){
        return GetTotallyRandomXCostCard(inCombat, false);
    }
    public static AbstractCard GetTotallyRandomXCostCard(boolean inCombat, boolean Colorless) {
        int Tries = 0;

        AbstractCard.CardRarity rarity;

        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 65) {
            rarity = AbstractCard.CardRarity.COMMON;
        } else if (roll < 90) {
            rarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            rarity = AbstractCard.CardRarity.RARE;
        }


        AbstractCard c;
        do {
            c = CardLibrary.getAnyColorCard(rarity);
            AbstractCard card = null;
        }
        while (
                Tries <= MaxTries && (c == null || c.cost != -1 || !Colorless && c.color == AbstractCard.CardColor.COLORLESS || (inCombat && c.hasTag(AbstractCard.CardTags.HEALING)))
        );

        if(c == null){
            c = new DinnerBell();
        }
        return c;
    }

    public static AbstractCard GetTotallyRandomCard(boolean inCombat, boolean Colorless, AbstractCard.CardType type) {
        int Tries = 0;

        AbstractCard.CardRarity rarity;

        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 65) {
            rarity = AbstractCard.CardRarity.COMMON;
        } else if (roll < 90) {
            rarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            rarity = AbstractCard.CardRarity.RARE;
        }


        AbstractCard c;
        do {
            c = CardLibrary.getAnyColorCard(rarity);
            AbstractCard card = null;
        }
        while (
                Tries <= MaxTries && (c == null  || c.type != type || !Colorless && c.color == AbstractCard.CardColor.COLORLESS || (inCombat && c.hasTag(AbstractCard.CardTags.HEALING)))
        );

        if(c == null){
            c = new SetTheTable();
        }
        return c;
    }





}
