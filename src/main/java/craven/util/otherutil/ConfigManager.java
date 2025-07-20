package craven.util.otherutil;

import basemod.ModLabeledToggleButton;
import basemod.ModMinMaxSlider;
import basemod.ModPanel;
import basemod.ModToggleButton;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.Properties;

import static craven.util.otherutil.variables.UIText.*;

public class ConfigManager {
    public static SpireConfig CravenConfig;

    public static Properties CravenDefaults = new Properties();

    public static final String ENABLE_EYE_CANDY = "EnableEyeCandy";
    public static boolean EnableEyeCandy = true;
    public static final String ENABLE_ENABLE_EAR_CANDY = "EnableEarCandy";
    public static boolean EnableEarCandy = true;
    public static final String SHOW_TIP_BUTTON = "ShowTipButton";
    public static boolean ShowTipButton = true;
    public static final String ENABLE_EVENTS = "EnableEvents";
    public static boolean EnableEvents = false;
    public static final String ALL_DEVOUR = "AllDevour";
    public static boolean AllDevour = false;

    public static final String BRANCH_NERF = "BranchNerf";
    public static boolean BranchNerf = true;

    public static int StarterRations = 10;
    public static final String STARTER_RATIONS = "StarterRations";

    public static int ScavengeCount = 5;
    public static final String SCAVENGE_COUNT = "ScavengeCount";

    public static final String SHOW_TUTORIAL = "ShowTutorial";
    public static boolean ShowTutorial = true;




    ///Store the tutorial button so we can identify - and toggle - it
    public static ModToggleButton StoredTutorialButton;


    ///Set all our defaults
    public static void SetConfigDefaults(){
        CravenDefaults.setProperty(ENABLE_EYE_CANDY, Boolean.toString(EnableEyeCandy));
        CravenDefaults.setProperty(ENABLE_ENABLE_EAR_CANDY, Boolean.toString(EnableEarCandy));
        CravenDefaults.setProperty(SHOW_TIP_BUTTON, Boolean.toString(ShowTipButton));
        CravenDefaults.setProperty(ENABLE_EVENTS, Boolean.toString(EnableEvents));
        CravenDefaults.setProperty(ALL_DEVOUR, Boolean.toString(AllDevour));
        CravenDefaults.setProperty(BRANCH_NERF, Boolean.toString(BranchNerf));
        CravenDefaults.setProperty(SHOW_TUTORIAL, Boolean.toString(ShowTutorial));

        CravenDefaults.setProperty(STARTER_RATIONS, Integer.toString(StarterRations));
        CravenDefaults.setProperty(SCAVENGE_COUNT, Integer.toString(ScavengeCount));
        try {
            SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
            config.load();
            EnableEyeCandy = config.getBool(ENABLE_EYE_CANDY);
            EnableEarCandy = config.getBool(ENABLE_ENABLE_EAR_CANDY);
            ShowTipButton = config.getBool(SHOW_TIP_BUTTON);
            EnableEvents = config.getBool(ENABLE_EVENTS);
            AllDevour = config.getBool(ALL_DEVOUR);
            BranchNerf = config.getBool(BRANCH_NERF);
            ShowTutorial = config.getBool(SHOW_TUTORIAL);
            StarterRations = config.getInt(STARTER_RATIONS);
            ScavengeCount = config.getInt(SCAVENGE_COUNT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Add all our config buttons
    public static void AddConfigButtons(ModPanel settingsPanel){

        ModLabeledToggleButton EyeCandyButton = new ModLabeledToggleButton(EYECANDYTEXT(),
                350.0f, yPos(1), Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableEyeCandy, settingsPanel, (label) -> {
        }, (button) -> {
            EnableEyeCandy = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                config.setBool(ENABLE_EYE_CANDY, EnableEyeCandy);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(EyeCandyButton);


        ModLabeledToggleButton EarCandyButton = new ModLabeledToggleButton(EARCANDYTEXT(),
                350.0f, yPos(2), Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableEarCandy, settingsPanel, (label) -> {
        }, (button) -> {
            EnableEarCandy = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                config.setBool(ENABLE_ENABLE_EAR_CANDY, EnableEarCandy);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(EarCandyButton);


        ModLabeledToggleButton ShowTutorialButton = new ModLabeledToggleButton(SHOWTUTORIALTEXT(),
                350.0f, yPos(4), Settings.CREAM_COLOR, FontHelper.charDescFont,
                ShowTutorial, settingsPanel, (label) -> {
        }, (button) -> {
            ShowTutorial = button.enabled;
            StoredTutorialButton = button;
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                config.setBool(SHOW_TUTORIAL, ShowTutorial);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(ShowTutorialButton);


        ModLabeledToggleButton ShowTipButton = new ModLabeledToggleButton(SHOWTIPTEXT(),
                350.0f, yPos(5), Settings.CREAM_COLOR, FontHelper.charDescFont,
                ConfigManager.ShowTipButton, settingsPanel, (label) -> {
        }, (button) -> {
            ConfigManager.ShowTipButton = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                config.setBool(SHOW_TIP_BUTTON, ConfigManager.ShowTipButton);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(ShowTipButton);



        ModLabeledToggleButton EnableEventButton = new ModLabeledToggleButton(ENNABLEEVENTSTEXT(),
                350.0f, yPos(7), Color.GRAY.cpy(), FontHelper.charDescFont,
                EnableEvents, settingsPanel, (label) -> {
        }, (button) -> {
            EnableEvents = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                config.setBool(ENABLE_EVENTS, EnableEvents);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(EnableEventButton);


        ModLabeledToggleButton AllDevourButton = new ModLabeledToggleButton(ALLDEVOURTEXT(),
                350.0f, yPos(8), Settings.CREAM_COLOR, FontHelper.charDescFont,
                AllDevour, settingsPanel, (label) -> {
        }, (button) -> {
            AllDevour = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                config.setBool(ALL_DEVOUR, AllDevour);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(AllDevourButton);



        ModMinMaxSlider RationsSlider = new ModMinMaxSlider(STARTERRATIONSTEXT(), 500f, yPos(9)+ 15, 0, 20, StarterRations, "x%.0f", settingsPanel, slider -> {
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                int i = Math.round(slider.getValue());
                config.setInt(STARTER_RATIONS, i);
                StarterRations = i;
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(RationsSlider);


        ModMinMaxSlider ScavengeSlider = new ModMinMaxSlider(SCAVENGERATIONSTEXT(), 500f, yPos(10)+ 15, 0, 10, ScavengeCount, "x%.0f", settingsPanel, slider -> {
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                int i = Math.round(slider.getValue());
                config.setInt(SCAVENGE_COUNT, i);
                ScavengeCount = i;
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(ScavengeSlider);


        ModLabeledToggleButton BranchNerfButton = new ModLabeledToggleButton(BRANCHNERFTEXT(),
                350.0f, yPos(12), Settings.CREAM_COLOR, FontHelper.charDescFont,
                BranchNerf, settingsPanel, (label) -> {
        }, (button) -> {
            BranchNerf = button.enabled;
            try {
                SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                config.setBool(BRANCH_NERF, BranchNerf);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(BranchNerfButton);
    }


    public static float yPos(int i){
        return 750.0f - (50.0f * (i - 1));
    }
}
