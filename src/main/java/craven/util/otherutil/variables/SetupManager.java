package craven.util.otherutil.variables;

import basemod.BaseMod;
import craven.screens.AtlasReminderScreen;

public class SetupManager {

    public static void AddEvents(){

    }

    public static void RegisterScreens(){
        BaseMod.addCustomScreen(new AtlasReminderScreen());
    }

}
