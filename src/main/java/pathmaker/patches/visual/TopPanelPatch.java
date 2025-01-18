package pathmaker.patches.visual;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import pathmaker.util.TextureLoader;

import static com.megacrit.cardcrawl.helpers.ImageMaster.*;
import static pathmaker.util.otherutil.ConfigManager.EnableAltUI;

public class TopPanelPatch {

    private static final Texture PathBar = loadImage("pathmaker/images/ui/uiswaps/TopBar.png");
    private static final Texture PathHP = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/HPIcon.png");
    private static final Texture PathGold = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/GoldIcon.png");
    private static final Texture PathFloors = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/FloorIcon.png");
    private static final Texture PathAsc = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/AscensionIcon.png");
    private static final Texture PathMap = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/MapIcon.png");
    private static final Texture PathDeck = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/DeckIcon.png");
    private static final Texture PathSettings = TextureLoader.getTexture("pathmaker/images/ui/uiswaps/SettingsIcon.png");




    private static final Texture ORIG_BAR = loadImage("images/ui/topPanel/bar.png");
    private static final Texture ORIG_HP = loadImage("images/ui/topPanel/panelHeart.png");
    private static final Texture ORIG_GOL = loadImage("images/ui/topPanel/panelGoldBag.png");
    private static final Texture ORIG_FLOORS = loadImage("images/ui/topPanel/floor.png");
    private static final Texture ORIG_ASC = loadImage("images/ui/topPanel/ascension.png");
    private static final Texture ORIG_MAP = loadImage("images/ui/topPanel/map.png");
    private static final Texture ORIG_DECK = loadImage("images/ui/topPanel/deck.png");
    private static final Texture ORIG_SETTINGS = loadImage("images/ui/topPanel/settings.png");
    public static void UpdateIcons(){
            if(EnableAltUI){
                MakeIconsSpecial();
                return;
            }
        MakeIconsNormal();
    }

    private static void MakeIconsSpecial() {
        ///Change the top menu bar

        ///OP_PANEL_BAR = PathBar;
        TP_HP = PathHP;
        TP_GOLD = PathGold;
        ///TP_FLOOR = PathFloors;
        TP_ASCENSION = PathAsc;
        MAP_ICON = PathMap;
        DECK_ICON = PathDeck;
        ///SETTINGS_ICON = PathSettings;
    }

    private static void MakeIconsNormal() {
        ///TOP_PANEL_BAR = ORIG_HP;
        TP_HP = ORIG_HP;
        TP_GOLD = ORIG_GOL;
        ///TP_FLOOR = ORIG_FLOORS;
        TP_ASCENSION = ORIG_ASC;
        MAP_ICON = ORIG_MAP;
        DECK_ICON = ORIG_DECK;
        ///SETTINGS_ICON = ORIG_SETTINGS;
    }
}