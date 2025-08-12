package craven.util.otherutil.variables;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import craven.events.NoWitnessesEvent;
import craven.screens.AtlasReminderScreen;
import craven.util.otherutil.Wiz;

import static craven.util.otherutil.Wiz.EnableCravenEvents;

public class SetupManager {

    public static void AddEvents(){
        BaseMod.addEvent(
                new AddEventParams.Builder(
                        NoWitnessesEvent.ID,
                        NoWitnessesEvent.class)
                        .bonusCondition(Wiz::EnableCravenEvents)
                        .create()
        );

    }

    public static void RegisterScreens(){
        BaseMod.addCustomScreen(new AtlasReminderScreen());
    }

}
