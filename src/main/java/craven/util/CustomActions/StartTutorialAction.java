package craven.util.CustomActions;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import craven.character.CravenCharacter;

import static craven.screens.AtlasReminderScreen.Enum.ATLAS_REMINDER_SCREEN;
import static craven.util.otherutil.ConfigManager.*;
import static craven.util.otherutil.Wiz.DevourEnabled;

public class StartTutorialAction extends AbstractGameAction {
    public StartTutorialAction(){
        this.duration = startDuration = 0.35f;
    }
    @Override
    public void update() {
        tickDuration();
    }

    @Override
    protected void tickDuration() {

            if ((AbstractDungeon.player instanceof CravenCharacter || DevourEnabled())) {
                BaseMod.openCustomScreen(ATLAS_REMINDER_SCREEN);
                ShowTutorial = false;
                try {
                    SpireConfig config = new SpireConfig("Craven Mod", "CravenConfig", CravenDefaults);
                    config.setBool(SHOW_TUTORIAL, ShowTutorial);
                    config.save();
                    StoredTutorialButton.enabled = !ShowTutorial;
                    StoredTutorialButton.toggle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ///LaunchTutorial();
            this.isDone = true;
        ///}
    }
}
