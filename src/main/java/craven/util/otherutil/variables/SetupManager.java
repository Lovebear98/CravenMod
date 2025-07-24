package craven.util.otherutil.variables;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import craven.events.OpportunityEvent;
import craven.screens.AtlasReminderScreen;
import craven.util.otherutil.Wiz;

public class SetupManager {

    public static void AddEvents(){
        BaseMod.addEvent(
                new AddEventParams.Builder(
                        OpportunityEvent.ID,
                        OpportunityEvent.class)
                        .bonusCondition(() -> false)
                        .create()
        );

    }

    public static void RegisterScreens(){
        BaseMod.addCustomScreen(new AtlasReminderScreen());
    }

}
