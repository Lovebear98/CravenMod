package craven.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.EventPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import craven.cards.power.Brazen;
import craven.relics.GoldpawVintage;
import craven.relics.WineCasket;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class OpportunityEvent extends PhasedEvent {
    public static final String ID = makeID(OpportunityEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String titler = eventStrings.NAME;


    private static final String EnterKey = "EnterKey";
    private static final String HelpKey = "HelpKey";
    private static final String AttackKey = "AttackKey";
    private static final String LeaveKey = "LeaveKey";

    public OpportunityEvent() {
        super(ID, titler, "craven/images/events/Event.png");
        //This is where you define all the event's phases.

        registerPhase(EnterKey,
                new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0], new GoldpawVintage()), (i)->transitionKey(HelpKey))
                .addOption(new TextPhase.OptionInfo(OPTIONS[1] + BrazenNum() + OPTIONS[2]+HPLoss()+OPTIONS[3], new Brazen()), (i)->transitionKey(AttackKey))
        );

        registerPhase(HelpKey,
                new TextPhase(DESCRIPTIONS[1])
                        .addOption(OPTIONS[4], (i)->openMap())
        );
        registerPhase(AttackKey,
                new TextPhase(DESCRIPTIONS[2])
                        .addOption(OPTIONS[4], (i)->openMap())
        );


        registerPhase(LeaveKey,
                new TextPhase(DESCRIPTIONS[3])
                        .addOption(OPTIONS[4], (i)->openMap())
        );


        transitionKey(EnterKey);
    }


    private int HPLoss() {
        if(p() != null){
            if(AbstractDungeon.ascensionLevel >= 15){
                return (int) (p().maxHealth * 0.35f);
            }else{
                return (int) (p().maxHealth * 0.2f);
            }
        }
        return 1;
    }

    private int BrazenNum() {
        if(p() != null){
            if(AbstractDungeon.ascensionLevel >= 15){
                return 3;
            }else{
                return 5;
            }
        }
        return 3;
    }


    @Override
    public void transitionPhase(EventPhase next) {
        if(SamePhase(next, HelpKey)){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2,
                    RelicLibrary.getRelic(GoldpawVintage.ID).makeCopy());
        }
        if(SamePhase(next, AttackKey)){
            CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
            AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, HPLoss(), DamageInfo.DamageType.HP_LOSS));
            for(int i = BrazenNum(); i > 0; i -= 1){
                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Brazen(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }

        super.transitionPhase(next);
    }

    private boolean SamePhase(EventPhase e, String s){
        return e == getPhase(s);
    }
}