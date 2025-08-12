package craven.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.EventPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import craven.cards.generated.Mechanics.Rations;
import craven.cards.power.Brazen;
import craven.relics.SeedOfChange;
import craven.util.CustomActions.CustomGameEffects.RemoveCardEffect;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.Wiz.PlayerOwnedRations;
import static craven.util.otherutil.variables.Variables.p;

public class NoWitnessesEvent extends PhasedEvent {
    public static final String ID = makeID(NoWitnessesEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String titler = eventStrings.NAME;


    private static final String EnterKey = "EnterKey";
    private static final String Hesitate = "Hesitate";
    private static final String EatKey = "EatKey";
    private static final String ResistKey = "ResistKey";
    private static final String LeaveKey = "LeaveKey";

    public NoWitnessesEvent() {
        super(ID, titler, "craven/images/events/NoWitnessesEvent.png");
        //This is where you define all the event's phases.

        registerPhase(EnterKey,
                new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0]+ GainRationAmount() + OPTIONS[1] + GainShameAmount()+OPTIONS[2], new Shame()), (i)->transitionKey(Hesitate))
                .addOption(new TextPhase.OptionInfo(OPTIONS[3] + ProperLoseRationAmount() + OPTIONS[4] + GainBrazenAmount() + OPTIONS[5], new Brazen()), (i)->transitionKey(EatKey))
                .addOption(new TextPhase.OptionInfo(OPTIONS[6] + HPLossAmount() + OPTIONS[7], new SeedOfChange()), (i)->transitionKey(ResistKey))
        );


        registerPhase(Hesitate,
                new TextPhase(DESCRIPTIONS[1])
                        .addOption(OPTIONS[8], (i)->openMap())
        );
        registerPhase(EatKey,
                new TextPhase(DESCRIPTIONS[2])
                        .addOption(OPTIONS[8], (i)->openMap())
        );


        registerPhase(ResistKey,
                new TextPhase(DESCRIPTIONS[3])
                        .addOption(OPTIONS[8], (i)->openMap())
        );


        transitionKey(EnterKey);
    }

    private int ProperLoseRationAmount() {
        if(p() != null){
            return Math.min(LoseRationAmount(), PlayerOwnedRations());
        }
        return LoseRationAmount();
    }


    private int HPLossAmount() {
        if(p() != null){
            if(AbstractDungeon.ascensionLevel >= 15){
                return (int) (p().maxHealth * 0.30f);
            }else{
                return (int) (p().maxHealth * 0.15f);
            }
        }
        return 1;
    }

    private int GainBrazenAmount() {
        if(p() != null){
            if(AbstractDungeon.ascensionLevel >= 15){
                return 2;
            }
        }
        return 3;
    }

    private int GainRationAmount() {
        if(p() != null){
            if(AbstractDungeon.ascensionLevel >= 15){
                return 7;
            }
        }
        return 8;
    }

    private int LoseRationAmount() {
        if(p() != null){
            if(AbstractDungeon.ascensionLevel >= 15){
                return 8;
            }
        }
        return 5;
    }

    private int GainShameAmount() {
        if(p() != null){
            if(AbstractDungeon.ascensionLevel >= 15){
                return 3;
            }
        }
        return 2;
    }


    @Override
    public void transitionPhase(EventPhase next) {
        if(SamePhase(next, Hesitate)){
            CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
            for(int i = GainRationAmount(); i > 0; i -= 1){
                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Rations(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
            for(int i = GainShameAmount(); i > 0; i -= 1){
                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Shame(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }
        if(SamePhase(next, EatKey)){
            CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
            int e = Math.min(LoseRationAmount(), PlayerOwnedRations());
            if(e > 0){
                for(int i = e; i > 0; i -= 1){
                    AbstractDungeon.effectsQueue.add(new RemoveCardEffect(Rations.ID));
                }
            }
            for(int i = GainBrazenAmount(); i > 0; i -= 1){
                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Brazen(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }

        }
        if(SamePhase(next, ResistKey)){
            AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, HPLossAmount(), DamageInfo.DamageType.HP_LOSS));
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2,
                    RelicLibrary.getRelic(SeedOfChange.ID).makeCopy());
        }

        super.transitionPhase(next);
    }

    private boolean SamePhase(EventPhase e, String s){
        return e == getPhase(s);
    }
}