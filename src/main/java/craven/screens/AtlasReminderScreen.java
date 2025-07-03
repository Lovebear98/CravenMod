package craven.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import craven.util.TextureLoader;

import static craven.CravenMod.TipUI;
import static craven.CravenMod.makeID;
import static craven.util.otherutil.SoundManager.PlaySound;
import static craven.util.otherutil.variables.UIText.ReturnText;

public class AtlasReminderScreen extends CustomScreen {
    /** UNLEASH THE VARIABLES */
    private Hitbox LeftArrowHB;
    private Hitbox ExitHB;
    private Hitbox RightArrowHB;
    private Hitbox ExtraHB = new Hitbox(1, 1);


    public static final float SizeCorrect = (1.4f*Settings.scale);

    private static final float sx = (float) Settings.WIDTH / 2;
    private static float x = (float) Settings.WIDTH / 2;
    private static final float y = (float) Settings.HEIGHT /2;

    private static final int PageFloor = -1;
    private static int PageNum = 0;
    private static final int PageCap = 2;


    private static final Texture TipBorder = TextureLoader.getTexture("craven/images/tutorialimgs/TipBorder.png");
    private static final Texture Tip1 = TextureLoader.getTexture("craven/images/tutorialimgs/Tip1.png");
    private static final Texture Tip2 = TextureLoader.getTexture("craven/images/tutorialimgs/Tip2.png");
    private static final Texture Tip3 = TextureLoader.getTexture("craven/images/tutorialimgs/Tip3.png");
    private static final Texture Tip4 = TextureLoader.getTexture("craven/images/tutorialimgs/Tip4.png");

    private static final float ImageHeightOffset = Settings.HEIGHT * 0.05f;
    private static final float TextHeightOffset = Settings.HEIGHT * 0.25f;
    private static final float LineWidth = 1000f * Settings.scale;
    private static final Color FadeColor = new Color(0.35f, 0.35f, 0.35f, 1f);


    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen ATLAS_REMINDER_SCREEN;
    }
    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.ATLAS_REMINDER_SCREEN;
    }

    /** Constructor */
    public AtlasReminderScreen(){
        ExitHB = new Hitbox(300.0F * Settings.scale, 100.0F * Settings.scale);
        LeftArrowHB = new Hitbox(160.0F * Settings.scale, 160.0F * Settings.scale);
        RightArrowHB = new Hitbox(160.0F * Settings.scale, 160.0F * Settings.scale);
    }

    /** Hitbox manipulation */
    private void ScaleHitboxes(){
        LeftArrowHB.resize(160.0F * Settings.scale, 160.0F * Settings.scale);
        RightArrowHB.resize(160.0F * Settings.scale, 160.0F * Settings.scale);
        ExitHB.resize(300.0F * Settings.scale, 100.0F * Settings.scale);
    }
    private void MoveHitboxes() {
        LeftArrowHB.move((float)Settings.WIDTH / 2.0F - 600.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F);
        RightArrowHB.move((float)Settings.WIDTH / 2.0F + 600.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F);
        ExitHB.move(SHOW_X + 106.0F * Settings.scale, DRAW_Y + 60.0F * Settings.scale);
    }

    /** Open/Close logic */
    private void open() {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE) {
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;

        ScaleHitboxes();
        MoveHitboxes();
        ResetPositions();
    }
    @Override
    public void reopen() {
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }
    @Override
    public void close() {
        if(AbstractDungeon.previousScreen != null){
            AbstractDungeon.screen = AbstractDungeon.previousScreen;
        }else{
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
        }
        AbstractDungeon.isScreenUp = false;
        AbstractDungeon.overlayMenu.hideBlackScreen();
        ResetPositions();
        TipUI.flash();
        TipUI.ResetClickLock();
    }


    /** Frame executed features */
    @Override
    public void update() {
        LeftArrowHB.update();
        ExitHB.update();
        RightArrowHB.update();
        ExtraHB.update();
        updateGlow();


        if (this.LeftArrowHB.justHovered || RightArrowHB.justHovered || ExitHB.justHovered) {
            CardCrawlGame.sound.play("UI_HOVER");
        }

        x = Interpolation.bounce.apply(x, Goalx(), 0.25f- PageChangeCountdown);


        PageChangeCountdown -= Gdx.graphics.getDeltaTime();
        if(PageChangeCountdown <= 0){
            PageChangeCountdown = 0;
        }
        ScrollLock -= Gdx.graphics.getDeltaTime();
        if(ScrollLock <= 0){
            ScrollLock = 0;
        }


        if(InputHelper.justClickedLeft){
            if(LeftArrowHB.hovered){
                ChangedPage(-1);
            }
            if(RightArrowHB.hovered){
                ChangedPage(1);
            }
            if(ExitHB.hovered){
                close();
            }
        }
        if(InputHelper.scrolledUp && ScrollLock <= 0f){
            ChangedPage(-1);
            SetScrollLock();
        }
        if(InputHelper.scrolledDown && ScrollLock <= 0f){
            ChangedPage(1);
            SetScrollLock();
        }
        if(InputHelper.justReleasedClickRight){
            close();
        }

        if(CInputActionSet.left.justPressed){
            ChangedPage(-1);
        }
        if(CInputActionSet.right.justPressed){
            ChangedPage(1);
        }
        if(CInputActionSet.cancel.justPressed){
            close();
        }
        if(InputHelper.pressedEscape){
            close();
        }
    }

    private float Goalx() {
        return sx - (Settings.WIDTH * PageNum);
    }

    private float PageChangeCountdown = 0.25f;
    private float ScrollLock = 0f;
    private void SetScrollLock() {
        ScrollLock = 0.15f;
    }

    private void ChangedPage(int i){
        if(i != 0){
            PageNum += i;
            if(PageNum < PageFloor){
                PageNum = PageCap;
            }
            if(PageNum > PageCap){
                PageNum = PageFloor;
            }

            this.PageChangeCountdown = 0.25f;
            PlaySound("CARD_SELECT", 0.05f);
        }
    }

    private float Pagex(int i){
        return x + (Settings.WIDTH * i);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        String text;
        int pagetoshow = PageFloor;
        float offsetY = 0f;
        if (Settings.BIG_TEXT_MODE) {
            offsetY = 110f * Settings.scale;
        }

        ///The universal header
        FontHelper.bannerNameFont.getData().setScale(1.0F);
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, HeaderText(), sx, Settings.HEIGHT * 0.85f, Color.YELLOW.cpy());
        FontHelper.bannerNameFont.getData().setScale(0.4F);

        ///This is how we print any given page's header and text

        ///Start Page
        pagetoshow = -1;
        text = Page1Text();

        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, Page1Header(), Pagex(pagetoshow), Settings.HEIGHT * 0.775f, Color.WHITE.cpy());
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, Pagex(pagetoshow)-(500f * Settings.scale), Settings.HEIGHT / 2f - TextHeightOffset - FontHelper.getSmartHeight(FontHelper.panelNameFont, text, LineWidth, 40f * Settings.scale) / 2f + offsetY, LineWidth, 40f * Settings.scale, Settings.CREAM_COLOR);
        renderTipImage(sb, Tip1, pagetoshow);
        ///End Page

        ///Start Page
        pagetoshow = 0;
        text = Page2Text();

        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, Page2Header(), Pagex(pagetoshow), Settings.HEIGHT * 0.775f, Color.WHITE.cpy());
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, Pagex(pagetoshow)-(500f * Settings.scale), Settings.HEIGHT / 2f - TextHeightOffset - FontHelper.getSmartHeight(FontHelper.panelNameFont, text, LineWidth, 40f * Settings.scale) / 2f + offsetY, LineWidth, 40f * Settings.scale, Settings.CREAM_COLOR);
        renderTipImage(sb, Tip2, pagetoshow);
        ///End Page

        ///Start Page
        pagetoshow = 1;
        text = Page3Text();

        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, Page3Header(), Pagex(pagetoshow), Settings.HEIGHT * 0.775f, Color.WHITE.cpy());
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, Pagex(pagetoshow)-(500f * Settings.scale), Settings.HEIGHT / 2f - TextHeightOffset - FontHelper.getSmartHeight(FontHelper.panelNameFont, text, LineWidth, 40f * Settings.scale) / 2f + offsetY, LineWidth, 40f * Settings.scale, Settings.CREAM_COLOR);
        renderTipImage(sb, Tip3, pagetoshow);
        ///End Page

        ///Start Page
        pagetoshow = 2;
        text = Page4Text();

        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, Page4Header(), Pagex(pagetoshow), Settings.HEIGHT * 0.775f, Color.WHITE.cpy());
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, Pagex(pagetoshow)-(500f * Settings.scale), Settings.HEIGHT / 2f - TextHeightOffset - FontHelper.getSmartHeight(FontHelper.panelNameFont, text, LineWidth, 40f * Settings.scale) / 2f + offsetY, LineWidth, 40f * Settings.scale, Settings.CREAM_COLOR);
        renderTipImage(sb, Tip4, pagetoshow);
        ///End Page

        FontHelper.bannerNameFont.getData().setScale(1.0F);


        renderArrows(sb);
        renderPageNumbers(sb);
        renderExitButton(sb);

        ///if(ExtraHB.hovered){
        ///    TipHelper.renderTipForCard(DisplayCard, sb, DisplayCard.keywords);
        ///}

        LeftArrowHB.render(sb);
        ExitHB.render(sb);
        RightArrowHB.render(sb);
        ExtraHB.render(sb);
    }

    private void renderPageNumbers(SpriteBatch sb) {
        Color PageTexColor;
        FontHelper.blockInfoFont.getData().setScale(2.0F);
        if(PageNum > PageFloor){
            PageTexColor = Color.GREEN.cpy();
        }else{
            PageTexColor = Color.RED.cpy();
        }
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, String.valueOf(((PageNum + 2) - (PageFloor+2))), LeftArrowHB.cX+(5f*Settings.scale), LeftArrowHB.cY, PageTexColor);

        if(PageNum < PageCap){
            PageTexColor = Color.GREEN.cpy();
        }else{
            PageTexColor = Color.RED.cpy();
        }
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, String.valueOf(((PageCap + 2) - (PageNum+2))), RightArrowHB.cX-(5f*Settings.scale), RightArrowHB.cY, PageTexColor);
        FontHelper.bannerNameFont.getData().setScale(1.0F);
    }

    private void renderTipImage(SpriteBatch sb, Texture tex, int page) {
        drawCentered(sb, tex, Pagex(page), Settings.HEIGHT/2f + ImageHeightOffset);
        drawCentered(sb, TipBorder, Pagex(page), Settings.HEIGHT/2f + ImageHeightOffset);
    }


    /** Functions for drawing items in place */
    private void drawCentered(SpriteBatch sb, Texture tex, float x, float y){
        sb.draw(tex, x - (tex.getWidth() * SizeCorrect)/2, y-(tex.getHeight() * SizeCorrect)/2, tex.getWidth() * SizeCorrect, tex.getHeight() * SizeCorrect);
    }
    private void draw(SpriteBatch sb, Texture tex, float x, float y){
        sb.draw(tex, x, y, tex.getWidth() * SizeCorrect, tex.getHeight() * SizeCorrect);
    }









    /** Rendering methods stolen from basegame for left/right arrows and the exit button */
    private static final float SHOW_X = (float)Settings.WIDTH - 256.0F * Settings.scale;
    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.3F);
    private static final float DRAW_Y = 228.0F * Settings.scale;
    private static final float TEXT_OFFSET_Y = 57.0F * Settings.scale;
    private static final float TEXT_OFFSET_X = 136.0F * Settings.scale;
    private void renderExitButton(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.CONFIRM_BUTTON_SHADOW, SHOW_X - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        sb.setColor(this.glowColor);
        sb.draw(ImageMaster.CONFIRM_BUTTON_OUTLINE, SHOW_X - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.CONFIRM_BUTTON, SHOW_X - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);

        if (Settings.isControllerMode) {
            sb.draw(CInputActionSet.cancel.getKeyImg(), SHOW_X - 32.0F + 0.0F * Settings.scale, DRAW_Y - 32.0F + 100.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

        if (ExitHB.hovered && !ExitHB.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            sb.draw(ImageMaster.CONFIRM_BUTTON, SHOW_X - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
        if (ExitHB.clickStarted) {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, ReturnText(), SHOW_X + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, Color.LIGHT_GRAY);
        } else if (ExitHB.hovered) {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, ReturnText(), SHOW_X + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, ReturnText(), SHOW_X + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);
        }
    }
    private Color glowColor = Color.WHITE.cpy();
    private float glowAlpha = 0.0f;
    private void updateGlow() {
        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
        if (this.glowAlpha < 0.0F) {
            this.glowAlpha *= -1.0F;
        }
        float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0F) {
            this.glowColor.a = -tmp / 2.0F + 0.3F;
        } else {
            this.glowColor.a = tmp / 2.0F + 0.3F;
        }
    }
    private void renderArrows(SpriteBatch sb) {
        if(PageNum == PageFloor){
            sb.setColor(FadeColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        sb.draw(ImageMaster.POPUP_ARROW, this.LeftArrowHB.cX - 128.0F, this.LeftArrowHB.cY - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
        if (Settings.isControllerMode) {
            sb.draw(CInputActionSet.left.getKeyImg(), this.LeftArrowHB.cX - 32.0F + 0.0F * Settings.scale, this.LeftArrowHB.cY - 32.0F + 100.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if (this.LeftArrowHB.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
            sb.draw(ImageMaster.POPUP_ARROW, this.LeftArrowHB.cX - 128.0F, this.LeftArrowHB.cY - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);
        }

        if(PageNum == PageCap){
            sb.setColor(FadeColor);
        }else{
            sb.setColor(Color.WHITE.cpy());
        }
        sb.draw(ImageMaster.POPUP_ARROW, this.RightArrowHB.cX - 128.0F, this.RightArrowHB.cY - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, true, false);
        if (Settings.isControllerMode) {
            sb.draw(CInputActionSet.right.getKeyImg(), this.RightArrowHB.cX - 32.0F + 0.0F * Settings.scale, this.RightArrowHB.cY - 32.0F + 100.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if (this.RightArrowHB.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
            sb.draw(ImageMaster.POPUP_ARROW, this.RightArrowHB.cX - 128.0F, this.RightArrowHB.cY - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, true, false);
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);
        }
    }

    /** What we can open while this menu is up, and what to do when we do */
    @Override
    public void openingSettings() {
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();
    }
    @Override
    public boolean allowOpenDeck() {
        return true;
    }
    @Override
    public void openingDeck() {
        super.openingDeck();
        AbstractDungeon.previousScreen = curScreen();
    }
    @Override
    public boolean allowOpenMap() {
        return true;
    }
    @Override
    public void openingMap() {
        super.openingMap();
        AbstractDungeon.previousScreen = curScreen();
    }

    /** Reset the rotation of the carousel to its default */
    public void ResetPositions() {
        PageNum = PageFloor;
        x = Goalx();
    }


    /** Handling text */
    public static UIStrings UIText = CardCrawlGame.languagePack.getUIString(makeID(AtlasReminderScreen.class.getSimpleName()));
    private String HeaderText(){
        return UIText.TEXT[0] ;
    }
    ///
    private String Page1Header(){
        return UIText.TEXT[1];
    }
    private String Page1Text(){
        return UIText.EXTRA_TEXT[1];
    }
    ///
    private String Page2Header(){
        return UIText.TEXT[2];
    }
    private String Page2Text(){
        return UIText.EXTRA_TEXT[2];
    }
    ///
    private String Page3Header(){
        return UIText.TEXT[3];
    }
    private String Page3Text(){
        return UIText.EXTRA_TEXT[3];
    }
    ///
    private String Page4Header(){
        return UIText.TEXT[4];
    }
    private String Page4Text(){
        return UIText.EXTRA_TEXT[4];
    }
}