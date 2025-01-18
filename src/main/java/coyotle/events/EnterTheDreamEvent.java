package coyotle.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.EventPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import coyotle.cards.skill.DreamsmokeMystic;
import coyotle.cards.skill.EnterTheDream;
import coyotle.character.CoyotleCharacter;
import coyotle.relics.MemoryoftheDream;

import java.util.ArrayList;
import java.util.Objects;

import static coyotle.CoyotleMod.makeID;
import static coyotle.patches.PlayerFields.*;
import static coyotle.util.otherutil.variables.Variables.p;

public class EnterTheDreamEvent extends PhasedEvent {
    public static final String ID = makeID(EnterTheDreamEvent.class.getSimpleName());
    private static final String EarthKey = "EarthDream";
    private static final String SkyKey ="SkyDream";
    private static final String SeaKey ="SeaDream";
    private static final String SilenceKey ="SilenceDream";
    private static final String ValorKey ="ValorDream";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String titler = eventStrings.NAME;
    private boolean UsedEarth = false;
    private boolean UsedSky = false;
    private boolean UsedSea = false;
    private boolean UsedSilence = false;
    private boolean UsedValor = false;

    public EnterTheDreamEvent() {
        super(ID, titler, "coyotle/images/events/SmokeMysticEvent.png"); //Note, this constructor is different than that of a normal event.
        //This is where you define all the event's phases.

        ChooseDreams();

        registerPhase("Enter",
                new TextPhase(FirstEncounterText())
                .addOption(new TextPhase.OptionInfo(OPTIONS[0], new EnterTheDream()), (i)->transitionKey("Speak"))
                .addOption(new TextPhase.OptionInfo(OPTIONS[1], new DreamsmokeMystic()), (i)->transitionKey("Listen"))
                .addOption(OPTIONS[11], (i)->transitionKey("Leave"))
        );
        registerPhase("Speak",
                new TextPhase(DESCRIPTIONS[4])
                        .addOption(OPTIONS[2], (i)->transitionKey("EnterDream"))
        );
        registerPhase("Listen",
                new TextPhase(DESCRIPTIONS[3])
                        .addOption(OPTIONS[2], (i)->transitionKey("EnterDream"))
        );


        registerPhase("EnterDream",
                ModifyOptions(new TextPhase(DESCRIPTIONS[5]))
        );

        registerPhase(EarthKey,
                ModifyOptions(new TextPhase(DESCRIPTIONS[8]))
        );

        registerPhase(SkyKey,
                ModifyOptions(new TextPhase(DESCRIPTIONS[9]))
        );

        registerPhase(SeaKey,
                ModifyOptions(new TextPhase(DESCRIPTIONS[10]))
        );

        registerPhase(SilenceKey,
                ModifyOptions(new TextPhase(DESCRIPTIONS[11]))
        );

        registerPhase(ValorKey,
                ModifyOptions(new TextPhase(DESCRIPTIONS[12]))
        );


        registerPhase("WakeUp", new TextPhase(DESCRIPTIONS[6]).addOption(OPTIONS[11], (i)->openMap()));
        registerPhase("Leave", new TextPhase(DESCRIPTIONS[7]).addOption(OPTIONS[11], (i)->openMap()));


        transitionKey("Enter");
    }


    private static final int DreamsToHave = 3;
    private ArrayList<String> opt = new ArrayList<>();
    private void ChooseDreams() {
        opt.add(EarthKey);
        opt.add(SkyKey);
        opt.add(SeaKey);
        opt.add(SilenceKey);
        opt.add(ValorKey);

        for(int i = opt.size()-1; i >= DreamsToHave; i -= 1){
            opt.remove(AbstractDungeon.eventRng.random(i));
        }
    }

    private TextPhase ModifyOptions(TextPhase t){
        for(String s: opt){
            if(Objects.equals(s, EarthKey)){
                t.addOption(new TextPhase.OptionInfo(OPTIONS[3] + MAXHPLOSS() + OPTIONS[4], new MemoryoftheDream()).enabledCondition(() -> !UsedEarth), (i)->transitionKey(EarthKey));
            }
            if(Objects.equals(s, SkyKey)){
                t.addOption(new TextPhase.OptionInfo(OPTIONS[5]).enabledCondition(() -> !UsedSky), (i)->transitionKey(SkyKey));
            }
            if(Objects.equals(s, SeaKey)){
                t.addOption(new TextPhase.OptionInfo(OPTIONS[6] + HEAL() + OPTIONS[7]).enabledCondition(() -> !UsedSea), (i)->transitionKey(SeaKey));
            }
            if(Objects.equals(s, SilenceKey)){
                t.addOption(new TextPhase.OptionInfo(OPTIONS[8]).enabledCondition(() -> !UsedSilence), (i)->transitionKey(SilenceKey));
            }
            if(Objects.equals(s, ValorKey)){
                t.addOption(new TextPhase.OptionInfo(OPTIONS[9]).enabledCondition(() -> !UsedValor), (i)->transitionKey(ValorKey));
            }
        }
                t.addOption(new TextPhase.OptionInfo(OPTIONS[10]), (i)->transitionKey("WakeUp"));

        return t;
    }

    private int MAXHPLOSS() {
        return (int) (p().maxHealth * 0.2f);
    }

    private int HEAL(){
        return (int) (p().maxHealth * 1.0f);
    }


    @Override
    public void transitionPhase(EventPhase next) {
        if(SamePhase(next, "Enter")){
            EncounterMystic();
        }
        if(SamePhase(next, "Speak")){
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new EnterTheDream(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
        if(SamePhase(next, "Listen")){
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new DreamsmokeMystic(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
        if(SamePhase(next, "EnterDream")){
            this.title = DESCRIPTIONS[0];
            this.imageEventText.loadImage("coyotle/images/events/SmokeMysticEvent2.png");
        }
        if(SamePhase(next, EarthKey)){
            UsedEarth = true;
            p().decreaseMaxHealth(MAXHPLOSS());
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2,
                    RelicLibrary.getRelic(MemoryoftheDream.ID).makeCopy());
        }
        if(SamePhase(next, SkyKey)){
            p().currentHealth = 1;
            p().healthBarUpdatedEvent();
            UsedSky = true;
        }
        if(SamePhase(next, SeaKey)){
            UsedSea = true;
            AddMysticNextEnergy();
            AbstractDungeon.player.heal(HEAL());
        }
        if(SamePhase(next, SilenceKey)){
            UsedSilence = true;
            AddMysticNextDraw();
        }
        if(SamePhase(next, ValorKey)){
            UsedValor = true;
            p().loseGold(p().gold);
            ObtainSynergisticCards();
        }
        if(SamePhase(next, "WakeUp")){
            this.title = titler;
            this.imageEventText.loadImage("coyotle/images/events/SmokeMysticEvent.png");
        }

        super.transitionPhase(next);
    }

    private void ObtainSynergisticCards() {
        SynergisticCardBundle bundle = new SynergisticCardBundle();
        bundle.obtain();
    }



    private String FirstEncounterText() {
        if(p() instanceof CoyotleCharacter){
            return DESCRIPTIONS[1];
        }
        return DESCRIPTIONS[2];
    }

    private boolean SamePhase(EventPhase e, String s){
        if(e == getPhase(s)){
            return true;
        }
        return false;
    }


    private boolean SkySelected = false;
    private boolean SkyDone = false;
    private boolean SilenceSelected = false;
    private boolean SilenceDone = false;
    @Override
    public void update() {
        super.update();
        if(!SkyDone && UsedSky){
            if(!SkySelected){
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 3, OPTIONS[12], false, false, false, true);
                SkySelected = true;
            }else{
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
                }else{
                    SkyDone = true;
                }
            }
        }

        if(!SilenceDone && UsedSilence){
            if(!SilenceSelected){
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 2, OPTIONS[13], false, false, false, false);
                SilenceSelected = true;
            }else{
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    c.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
                }else{
                    SilenceDone = true;
                }
            }
        }
    }
}