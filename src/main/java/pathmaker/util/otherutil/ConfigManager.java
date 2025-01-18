package pathmaker.util.otherutil;

import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ModToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.Properties;

import static pathmaker.patches.visual.TopPanelPatch.UpdateIcons;
import static pathmaker.util.otherutil.variables.UIText.*;

public class ConfigManager {
    public static SpireConfig PathmakerConfig;

    public static Properties PathmakerDefaults = new Properties();

    public static final String SHOW_TUTORIAL = "ShowTutorial";
    public static boolean ShowTutorial = true;

    public static final String ENABLE_SOUNDS = "EnableSounds";
    public static boolean EnableSounds = true;

    public static final String ENABLE_ALT_UI = "EnableAltUI";
    public static boolean EnableAltUI = true;

    public static final String ALL_BLOOM = "AllBloom";
    public static boolean AllBloom = false;




    ///Store the tutorial button so we can identify - and toggle - it
    public static ModToggleButton StoredTutorialButton;


    ///Set all our defaults
    public static void SetDefaults(){
        PathmakerDefaults.setProperty(SHOW_TUTORIAL, Boolean.toString(ShowTutorial));
        PathmakerDefaults.setProperty(ENABLE_SOUNDS, Boolean.toString(EnableSounds));
        PathmakerDefaults.setProperty(ENABLE_ALT_UI, Boolean.toString(EnableAltUI));
        PathmakerDefaults.setProperty(ALL_BLOOM, Boolean.toString(ShowTutorial));
        try {
            SpireConfig config = new SpireConfig("SandDruidMod", "PathmakerConfig", PathmakerDefaults);
            config.load();
            ShowTutorial = config.getBool(SHOW_TUTORIAL);
            EnableSounds = config.getBool(ENABLE_SOUNDS);
            EnableAltUI = config.getBool(ENABLE_ALT_UI);
            AllBloom = config.getBool(ALL_BLOOM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Add all our config buttons
    public static void AddConfigButtons(ModPanel settingsPanel){

        ModLabeledToggleButton ShowTutorialButton = new ModLabeledToggleButton(SHOWTUTORIALTEXT(),
                350.0f, 750.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                ShowTutorial, settingsPanel, (label) -> {
        }, (button) -> {
            ShowTutorial = button.enabled;
            StoredTutorialButton = button;
            try {
                SpireConfig config = new SpireConfig("Pathmaker Mod", "PathmakerConfig", PathmakerDefaults);
                config.setBool(SHOW_TUTORIAL, ShowTutorial);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(ShowTutorialButton);



        ModLabeledToggleButton EnableSoundsButton = new ModLabeledToggleButton(ENABLESOUNDSTEXT(),
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableSounds, settingsPanel, (label) -> {
        }, (button) -> {
            EnableSounds = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Pathmaker Mod", "PathmakerConfig", PathmakerDefaults);
                config.setBool(ENABLE_SOUNDS, EnableSounds);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(EnableSoundsButton);



        ModLabeledToggleButton AltUIButton = new ModLabeledToggleButton(ALTUITEXT(),
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableAltUI, settingsPanel, (label) -> {
        }, (button) -> {
            EnableAltUI = button.enabled;
            UpdateIcons();
            try {
                SpireConfig config = new SpireConfig("Pathmaker Mod", "PathmakerConfig", PathmakerDefaults);
                config.setBool(ENABLE_ALT_UI, EnableAltUI);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(AltUIButton);



        ModLabeledToggleButton AllBloomButton = new ModLabeledToggleButton(ALLBLOOMTEXT(),
                350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                AllBloom, settingsPanel, (label) -> {
        }, (button) -> {
            AllBloom = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Pathmaker Mod", "PathmakerConfig", PathmakerDefaults);
                config.setBool(ALL_BLOOM, AllBloom);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(AllBloomButton);




    }
}
