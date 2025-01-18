package coyotle.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import coyotle.character.CoyotleCharacter;
import coyotle.util.CustomActions.CustomGameEffects.vfx.DreamSmokeEffect;
import coyotle.util.CustomActions.CustomGameEffects.vfx.FancyDreamSmokeEffect;

import static coyotle.util.otherutil.variables.Variables.p;

public class CampfirePatch {

    private static boolean ShouldRenderSmoke = true;

    @SpirePatch(
            clz = RestRoom.class,
            method = "update"
    )
    public static class RenderPatch {
        private static int RestRoomDreamTimer = 0;
        private static int RestRoomDreamTimerCap = 15;

        @SpirePostfixPatch
        public static void DreamyRestRoom(RestRoom ___instance) {
            if(p() != null && p() instanceof CoyotleCharacter){
                if(___instance.phase == AbstractRoom.RoomPhase.COMPLETE){
                    ShouldRenderSmoke = false;
                }
                if(ShouldRenderSmoke){
                    if(RestRoomDreamTimer >= RestRoomDreamTimerCap){
                        RestRoomDreamTimer = 0;
                        AbstractDungeon.effectsQueue.add(new DreamSmokeEffect());
                        AbstractDungeon.effectsQueue.add(new FancyDreamSmokeEffect());
                        AbstractDungeon.effectsQueue.add(new DreamSmokeEffect());
                        AbstractDungeon.effectsQueue.add(new FancyDreamSmokeEffect());
                    }else{
                        RestRoomDreamTimer += 1;
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = RestRoom.class,
            method = "onPlayerEntry"
    )
    public static class EntryPatch {

        @SpirePostfixPatch
        public static void DreamyRestRoom(RestRoom ___instance) {
            ShouldRenderSmoke = true;
        }
    }
}
