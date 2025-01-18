package coyotle.util.otherutil;

import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ModToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.Properties;

import static coyotle.util.otherutil.variables.UIText.*;

public class ConfigManager {
    public static SpireConfig ProphecyConfig;

    public static Properties ProphecyDefaults = new Properties();

    public static final String ENABLE_EYE_CANDY = "EnableEyeCandy";
    public static boolean EnableEyeCandy = true;

    public static final String ENABLE_ENABLE_EAR_CANDY = "EnableEarCandy";
    public static boolean EnableEarCandy = true;

    public static final String ENABLE_ALT_UI = "EnableAltUI";
    public static boolean EnableAltUI = true;
    public static final String ENABLE_EVENTS = "EnableEvents";
    public static boolean EnableEvents = true;
    public static final String SHOW_TUTORIAL = "ShowTutorial";
    public static boolean ShowTutorial = true;




    ///Store the tutorial button so we can identify - and toggle - it
    public static ModToggleButton StoredTutorialButton;


    ///Set all our defaults
    public static void SetConfigDefaults(){
        ProphecyDefaults.setProperty(ENABLE_EYE_CANDY, Boolean.toString(EnableEyeCandy));
        ProphecyDefaults.setProperty(ENABLE_ENABLE_EAR_CANDY, Boolean.toString(EnableEarCandy));
        ProphecyDefaults.setProperty(ENABLE_ALT_UI, Boolean.toString(EnableAltUI));
        ProphecyDefaults.setProperty(ENABLE_EVENTS, Boolean.toString(EnableEvents));
        ProphecyDefaults.setProperty(SHOW_TUTORIAL, Boolean.toString(ShowTutorial));
        try {
            SpireConfig config = new SpireConfig("Prophecy Mod", "ProphecyConfig", ProphecyDefaults);
            config.load();
            EnableEyeCandy = config.getBool(ENABLE_EYE_CANDY);
            EnableEarCandy = config.getBool(ENABLE_ENABLE_EAR_CANDY);
            EnableAltUI = config.getBool(ENABLE_ALT_UI);
            EnableEvents = config.getBool(ENABLE_EVENTS);
            ShowTutorial = config.getBool(SHOW_TUTORIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Add all our config buttons
    public static void AddConfigButtons(ModPanel settingsPanel){

        ModLabeledToggleButton EyeCandyButton = new ModLabeledToggleButton(EYECANDYTEXT(),
                350.0f, 750.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableEyeCandy, settingsPanel, (label) -> {
        }, (button) -> {
            EnableEyeCandy = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Prophecy Mod", "ProphecyConfig", ProphecyDefaults);
                config.setBool(ENABLE_EYE_CANDY, EnableEyeCandy);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(EyeCandyButton);


        ModLabeledToggleButton EarCandyButton = new ModLabeledToggleButton(EARCANDYTEXT(),
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableEarCandy, settingsPanel, (label) -> {
        }, (button) -> {
            EnableEarCandy = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Prophecy Mod", "ProphecyConfig", ProphecyDefaults);
                config.setBool(ENABLE_ENABLE_EAR_CANDY, EnableEarCandy);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(EarCandyButton);


        ModLabeledToggleButton AltUIButton = new ModLabeledToggleButton(ALTUITEXT(),
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableAltUI, settingsPanel, (label) -> {
        }, (button) -> {
            EnableAltUI = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Prophecy Mod", "ProphecyConfig", ProphecyDefaults);
                config.setBool(ENABLE_ALT_UI, EnableAltUI);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(AltUIButton);


        ModLabeledToggleButton EnableEventButton = new ModLabeledToggleButton(ENNABLEEVENTSTEXT(),
                350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableEvents, settingsPanel, (label) -> {
        }, (button) -> {
            EnableEvents = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Prophecy Mod", "ProphecyConfig", ProphecyDefaults);
                config.setBool(ENABLE_EVENTS, EnableEvents);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(EnableEventButton);


        ModLabeledToggleButton ShowTutorialButton = new ModLabeledToggleButton(SHOWTUTORIALTEXT(),
                350.0f, 400.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                ShowTutorial, settingsPanel, (label) -> {
        }, (button) -> {
            ShowTutorial = button.enabled;
            StoredTutorialButton = button;
            try {
                SpireConfig config = new SpireConfig("Prophecy Mod", "ProphecyConfig", ProphecyDefaults);
                config.setBool(SHOW_TUTORIAL, ShowTutorial);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(ShowTutorialButton);

    }
}
