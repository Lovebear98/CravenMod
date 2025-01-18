package coyotle.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.EventPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import coyotle.character.CoyotleCharacter;
import coyotle.relics.CoyotleRelic;
import coyotle.relics.MasteroftheHunt;

import static coyotle.CoyotleMod.makeID;
import static coyotle.patches.PlayerFields.*;
import static coyotle.util.otherutil.variables.Variables.p;

public class WindsingerEvent extends PhasedEvent {
    public static final String ID = makeID(WindsingerEvent.class.getSimpleName());
    public static final String ID1 = makeID(WindsingerEvent.class.getSimpleName()+"1");
    public static final String ID2 = makeID(WindsingerEvent.class.getSimpleName()+"2");
    public static final String ID3 = makeID(WindsingerEvent.class.getSimpleName()+"3");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;

    public WindsingerEvent() {
        super(ID, title, "coyotle/images/events/WindsingerEvent.png"); //Note, this constructor is different than that of a normal event.

        //This is where you define all the event's phases.
        registerPhase("FirstEncounter",
                new TextPhase(FirstEncounterText())
                .addOption(new TextPhase.OptionInfo(OPTIONS[0], new MasteroftheHunt()).enabledCondition(() -> HaveProofOfFriendship()), (i)->transitionKey("FirstFriend"))
                .addOption(OPTIONS[1], (i)->transitionKey("FightWindsinger"))
                .addOption(new TextPhase.OptionInfo(OPTIONS[2], new Pride()), (i)->transitionKey("FirstNeutral"))
                .addOption(OPTIONS[3], (i)->transitionKey("FirstLeave"))
        );
        registerPhase("FirstFriend",
                new TextPhase(DESCRIPTIONS[4])
                        .addOption(OPTIONS[9], (i)->openMap())
        );
        registerPhase("FirstNeutral",
                new TextPhase(DESCRIPTIONS[5])
                        .addOption(OPTIONS[9], (i)->openMap())
        );
        registerPhase("FirstLeave",
                new TextPhase(DESCRIPTIONS[6])
                        .addOption(OPTIONS[9], (i)->openMap())
        );


        registerPhase("SecondEncounterFriend",
                new TextPhase(DESCRIPTIONS[2])
                        .addOption(OPTIONS[5]+HealNum()+OPTIONS[6], (i)->transitionKey("SecondRest"))
                        .addOption(OPTIONS[7], (i)->transitionKey("SecondChat"))
                        .addOption(OPTIONS[8], (i)->transitionKey("FightWindsinger"))
                        .addOption(OPTIONS[9], (i)->transitionKey("SecondLeave"))
        );

        registerPhase("SecondRest",
                new TextPhase(DESCRIPTIONS[9])
                        .addOption(OPTIONS[9], (i)->openMap())
        );
        registerPhase("SecondChat",
                new TextPhase(DESCRIPTIONS[10])
                        .addOption(OPTIONS[9], (i)->openMap())
        );
        registerPhase("SecondLeave",
                new TextPhase(DESCRIPTIONS[11])
                        .addOption(OPTIONS[9], (i)->openMap())
        );

        ///registerPhase("SecondEncounterFoe",
        ///        new TextPhase(SecondEncounterText())
        ///                .addOption(OPTIONS[12], (i)->transitionKey("SecondApologize"))
        ///                .addOption(OPTIONS[13], (i)->transitionKey("FightWindsinger"))
        ///                .addOption(OPTIONS[14]+DamageNum()+OPTIONS[15], (i)->transitionKey("SecondFlight"))
        ///);

        registerPhase("Leave", new TextPhase(SecondEncounterText()).addOption(OPTIONS[9], (i)->openMap()));

        registerPhase("FightWindsinger", new TextPhase(FightText()).addOption(OPTIONS[16], (i)->transitionKey("StartWindsingerFight")));

        registerPhase("StartWindsingerFight", new CombatPhase(MonsterHelper.CULTIST_ENC)
                .addRewards(true, (room)->room.addRelicToRewards(AbstractRelic.RelicTier.RARE))
                .setNextKey("AfterFight"));

        registerPhase("AfterFight", new TextPhase(DESCRIPTIONS[14]).addOption(OPTIONS[9], (i)->openMap()));

        if(WindsingerEncounters() == 0){
            transitionKey("FirstEncounter");
        }else{
            transitionKey("SecondEncounterFriend");
        }
    }




    @Override
    public void transitionPhase(EventPhase next) {
        super.transitionPhase(next);
        if(SamePhase(next, "FirstEncounter") || SamePhase(next, "SecondEncounterFriend")){
            IncreaseEncounters();
        }
        if(SamePhase(next, "SecondRest")){
            AbstractDungeon.player.heal(HealNum());
        }
        if(SamePhase(next, "FirstFriend")){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2,
                    RelicLibrary.getRelic(MasteroftheHunt.ID).makeCopy());
        }
        if(SamePhase(next, "FirstNeutral")){
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pride(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
        if(SamePhase(next, "SecondChat")){
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pride(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }

    private int DamageNum() {
        return 5;
    }

    private int HealNum() {
        int healAmt;

        if (ModHelper.isModEnabled("Night Terrors")) {
            healAmt = (int)((float)AbstractDungeon.player.maxHealth * 1.0F);
        } else {
            healAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.3F);
        }

        if (Settings.isEndless && AbstractDungeon.player.hasBlight("FullBelly")) {
            healAmt /= 2;
        }
        return healAmt;
    }

    private String FirstEncounterText() {
        if(p() instanceof CoyotleCharacter){
            return DESCRIPTIONS[0];
        }
        return DESCRIPTIONS[1];
    }

    private String SecondEncounterText() {
        if(WindsingerIsFriend() || p() instanceof CoyotleCharacter){
            return DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[3];
    }

    private String FightText() {
        if(WindsingerIsFriend() || p() instanceof CoyotleCharacter){
            return DESCRIPTIONS[7];
        }
        return DESCRIPTIONS[8];
    }

    private Boolean HaveProofOfFriendship() {
        if(p() != null){
            if(p() instanceof CoyotleCharacter){
                return true;
            }
            for(AbstractCard c: p().masterDeck.group){
                if(c.color == CoyotleCharacter.Meta.CARD_COLOR){
                    return true;
                }
            }
            for(AbstractRelic r: p().relics){
                if(r instanceof CoyotleRelic){
                    return true;
                }
            }
        }
        return false;
    }


    private boolean SamePhase(EventPhase e, String s){
        if(e == getPhase(s)){
            return true;
        }
        return false;
    }
}