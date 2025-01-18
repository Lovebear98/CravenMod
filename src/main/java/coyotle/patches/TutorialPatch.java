package coyotle.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import coyotle.character.CoyotleCharacter;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.ConfigManager.*;
import static coyotle.util.otherutil.Wiz.ValidForShards;


public class TutorialPatch extends FtueTip {
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString(makeID(TutorialPatch.class.getSimpleName()));
    public static final String[] MSG = tutorialStrings.TEXT;
    public static final String[] LABEL = tutorialStrings.LABEL;

    private static final TextureRegion img1 = new TextureRegion(new Texture("coyotle/images/tutorialimgs/t1.png"));
    private static final TextureRegion img2 = new TextureRegion(new Texture("coyotle/images/tutorialimgs/t2.png"));
    private static final TextureRegion img3 = new TextureRegion(new Texture("coyotle/images/tutorialimgs/t3.png"));
    private Color screen = Color.valueOf("1c262a00");
    private float x, x1, x2, x3, targetX, startX;
    private float scrollTimer = 0f;
    private static final float SCROLL_TIME = 0.3f;
    private int currentSlot = 0;
    private static final String msg1 = MSG[0];
    private static final String msg2 = MSG[1];
    private static final String msg3 = MSG[2];

    public TutorialPatch() {
        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        x = 0f;
        x1 = 567f * Settings.scale;
        x2 = x1 + Settings.WIDTH;
        x3 = x2 + Settings.WIDTH;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }

    @Override
    public void update() {
        if (screen.a != 0.8f) {
            screen.a += Gdx.graphics.getDeltaTime();
            if (screen.a > 0.8f) {
                screen.a = 0.8f;
            }
        }

        if (AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft
                || CInputActionSet.proceed.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            if (currentSlot == -2) {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
                AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
                return;
            }
            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            AbstractDungeon.overlayMenu.proceedButton.show();
            CardCrawlGame.sound.play("DECK_CLOSE");
            currentSlot--;
            startX = x;
            targetX = currentSlot * Settings.WIDTH;
            scrollTimer = SCROLL_TIME;

            if (currentSlot == -2) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
            }
        }

        if (scrollTimer != 0f) {
            scrollTimer -= Gdx.graphics.getDeltaTime();
            if (scrollTimer < 0f) {
                scrollTimer = 0f;
            }
        }

        x = Interpolation.fade.apply(targetX, startX, scrollTimer / SCROLL_TIME);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0f, 0f, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE);
        sb.draw(img1, x + x1 - img1.getRegionWidth() / 2f, Settings.HEIGHT / 2f - img1.getRegionHeight() / 2f, img1.getRegionWidth() / 2f, img1.getRegionHeight() / 2f, img1.getRegionWidth(), img1.getRegionHeight(), Settings.scale, Settings.scale, 0);
        sb.draw(img2, x + x2 - img2.getRegionWidth() / 2f, Settings.HEIGHT / 2f - img2.getRegionHeight() / 2f, img2.getRegionWidth() / 2f, img2.getRegionHeight() / 2f, img2.getRegionWidth(), img2.getRegionHeight(), Settings.scale, Settings.scale, 0);
        sb.draw(img3, x + x3 - img3.getRegionWidth() / 2f, Settings.HEIGHT / 2f - img3.getRegionHeight() / 2f, img3.getRegionWidth() / 2f, img3.getRegionHeight() / 2f, img3.getRegionWidth(), img3.getRegionHeight(), Settings.scale, Settings.scale, 0);

        float offsetY = 0f;
        if (Settings.BIG_TEXT_MODE) {
            offsetY = 110f * Settings.scale;
        }

        // Message 1
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg1, x + x1 + 400f * Settings.scale, Settings.HEIGHT / 2f - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg1, 700f * Settings.scale, 40f * Settings.scale) / 2f + offsetY, 700f * Settings.scale, 40f * Settings.scale, Settings.CREAM_COLOR);

        // Message 2
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg2, x + x2 + 400f * Settings.scale, Settings.HEIGHT / 2f - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg2, 700f * Settings.scale, 40f * Settings.scale) / 2f + offsetY, 700f * Settings.scale, 40f * Settings.scale, Settings.CREAM_COLOR);

        // Message 3
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg3, x + x3 + 400f * Settings.scale, Settings.HEIGHT / 2f - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg3, 700f * Settings.scale, 40f * Settings.scale) / 2f + offsetY, 700f * Settings.scale, 40f * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], Settings.WIDTH / 2f, Settings.HEIGHT / 2f - 360f * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, LABEL[3] + Integer.toString(Math.abs(currentSlot - 1)) + LABEL[4], Settings.WIDTH / 2f, Settings.HEIGHT / 2f - 400f * Settings.scale, Settings.CREAM_COLOR);

        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }

    public static void LaunchTutorial(){
        if ((AbstractDungeon.player instanceof CoyotleCharacter || ValidForShards()) && ShowTutorial) {
            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.FTUE) {
                AbstractDungeon.ftue = new TutorialPatch();
                ShowTutorial = false;
                try {
                    SpireConfig config = new SpireConfig("Prophecy Mod", "ProphecyConfig", ProphecyDefaults);
                    config.setBool(SHOW_TUTORIAL, ShowTutorial);
                    config.save();
                    StoredTutorialButton.enabled = !ShowTutorial;
                    StoredTutorialButton.toggle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}