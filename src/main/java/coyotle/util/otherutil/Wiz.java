package coyotle.util.otherutil;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import coyotle.character.CoyotleCharacter;
import coyotle.powers.custompowers.DrawDownPower;
import coyotle.powers.custompowers.NeutralFastingPower;

import static coyotle.CoyotleMod.makeID;
import static coyotle.patches.PlayerFields.*;
import static coyotle.util.CustomTags.Dreaming;
import static coyotle.util.CustomTags.Prophecy;
import static coyotle.util.otherutil.ConfigManager.EnableEvents;
import static coyotle.util.otherutil.DreamManager.AdminExitDream;
import static coyotle.util.otherutil.ShardManager.CombatStartShard;
import static coyotle.util.otherutil.ValorManager.ResetValor;
import static coyotle.util.otherutil.variables.Variables.p;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;


public class Wiz {
    public static void PreCombat(){
        CombatStartShard();
        AdminExitDream();
        ResetValor();
        SmokeMysticPrice();
    }

    public static void PostCombat(){
        AdminExitDream();
    }

    public static void FlashCard(AbstractCard card, Color color){
        if(AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)){
            AbstractDungeon.topLevelEffectsQueue.add(new CardFlashVfx(card, color));
        }
    }

    public static String OrbPath(String text){
        return "coyotle/images/orbs/" + text + ".png";
    }

    private static final Color ProphecyColor = new Color(172/255f, 123/255f, 242/255f, 1f);
    public static Color ProphecyColor(){
        return ProphecyColor;
    }


    public static boolean ValidForDream(){
        if(p() != null){
            if(p() instanceof CoyotleCharacter){
                return true;
            }
            for(AbstractCard c: p().masterDeck.group){
                if(c.hasTag(Dreaming)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean ValidForShards(){
        if(p() != null){
            if(p() instanceof CoyotleCharacter){
                return true;
            }
            for(AbstractCard c: p().masterDeck.group){
                if(c.hasTag(Prophecy)){
                    return true;
                }
            }
        }
        return false;
    }

    private static final UIStrings strikeDefendStrings = CardCrawlGame.languagePack.getUIString(makeID("StrikeDefendChecks"));
    public static boolean isStrikeOrDefend(AbstractCard c){
        if(c != null){
            if(c.hasTag(AbstractCard.CardTags.STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)){
                return true;
            }

            ///Tags aren't declared post constructor, so we check names
            if(
                    containsIgnoreCase(c.name, strikeDefendStrings.TEXT[0]) ||
                    containsIgnoreCase(c.name, strikeDefendStrings.TEXT[1]) ||
                    containsIgnoreCase(c.name, strikeDefendStrings.TEXT[2]) ||
                    containsIgnoreCase(c.name, strikeDefendStrings.TEXT[3]) ||
                    containsIgnoreCase(c.name, strikeDefendStrings.TEXT[4])){
                return true;
            }
        }

        return false;
    }

    public static boolean EnableEvents(){
        if(p() != null){
            if(p() instanceof CoyotleCharacter){
                return true;
            }
        }
        return EnableEvents;
    }



    private static void SmokeMysticPrice() {
        if(MysticNextEnergy() > 0){
            p().powers.add(new NeutralFastingPower(p(), MysticNextEnergy()));
            ClearMysticNextEnergy();
        }
        if(MysticNextDraw() > 0){
            DrawDownPower pow = new DrawDownPower(p(), MysticNextDraw());
            p().powers.add(pow);
            pow.FirstApp();
            ClearMysticNextDraw();
        }
    }
}
