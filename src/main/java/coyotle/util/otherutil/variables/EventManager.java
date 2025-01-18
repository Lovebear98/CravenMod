package coyotle.util.otherutil.variables;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import coyotle.events.EnterTheDreamEvent;
import coyotle.events.WindsingerEvent;

import static coyotle.util.otherutil.Wiz.EnableEvents;

public class EventManager {

    public static void AddEvents(){
        BaseMod.addEvent(
                new AddEventParams.Builder(
                        WindsingerEvent.ID1,
                        WindsingerEvent.class)
                        .eventType(EventUtils.EventType.SHRINE)
                        .bonusCondition(() -> EnableEvents())
                        .create()
        );
        BaseMod.addEvent(
                new AddEventParams.Builder(
                        EnterTheDreamEvent.ID,
                        EnterTheDreamEvent.class)
                        .bonusCondition(() -> EnableEvents())
                        .create()
        );
        BaseMod.addEvent(
                new AddEventParams.Builder(
                        WindsingerEvent.ID2,
                        WindsingerEvent.class)
                        .bonusCondition(() -> EnableEvents())
                        .dungeonIDs(Exordium.ID, TheBeyond.ID, TheCity.ID)
                        .create()
        );
        BaseMod.addEvent(
                new AddEventParams.Builder(
                        WindsingerEvent.ID3,
                        WindsingerEvent.class)
                        .bonusCondition(() -> EnableEvents())
                        .create()
        );
    }

}
