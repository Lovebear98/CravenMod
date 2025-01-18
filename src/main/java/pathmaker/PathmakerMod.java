package pathmaker;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import pathmaker.cards.BaseCard;
import pathmaker.character.PathmakerCharacter;
import pathmaker.potions.custompotions.AddersBlood;
import pathmaker.potions.custompotions.BlessedWater;
import pathmaker.potions.custompotions.OphidiansIchor;
import pathmaker.potions.custompotions.SerpentsSanguine;
import pathmaker.relics.BaseRelic;
import pathmaker.ui.BloomPanel;
import pathmaker.util.CustomActions.OasisAction;
import pathmaker.util.GeneralUtils;
import pathmaker.util.KeywordInfo;
import pathmaker.util.TextureLoader;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import pathmaker.util.otherutil.variables.secondMagicVar;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static pathmaker.cards.AbstractOasisCard.CanTriggerOasis;
import static pathmaker.patches.visual.TopPanelPatch.UpdateIcons;
import static pathmaker.util.otherutil.BloomManager.AttemptBloom;
import static pathmaker.util.otherutil.BloomManager.ResetBloom;
import static pathmaker.util.otherutil.ConfigManager.AddConfigButtons;
import static pathmaker.util.otherutil.ConfigManager.SetDefaults;
import static pathmaker.util.otherutil.SoundManager.*;

@SpireInitializer
public class PathmakerMod implements
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        AddAudioSubscriber,
        OnCardUseSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        PostDrawSubscriber,
        OnPlayerTurnStartSubscriber,
        OnPlayerTurnStartPostDrawSubscriber,
        StartGameSubscriber
{
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }


    public static BloomPanel BloomUI;


    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new PathmakerMod();

        PathmakerCharacter.Meta.registerColor();
    }

    public PathmakerMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");

        SetDefaults();
    }

    @Override
    public void receivePostInitialize() {
        BloomUI = new BloomPanel();

        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        ModPanel settingsPanel = new ModPanel();
        AddConfigButtons(settingsPanel);

        ///This step is last because it's what registers everything else in the settings panel
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, settingsPanel);

        UpdateIcons();

        BaseMod.addPotion(AddersBlood.class, Color.FIREBRICK, Color.SALMON, Color.SALMON, AddersBlood.POTION_ID, PathmakerCharacter.Meta.PATHMAKER_CHARACTER);
        BaseMod.addPotion(SerpentsSanguine.class, Color.FIREBRICK, Color.FOREST, Color.FOREST, SerpentsSanguine.POTION_ID, PathmakerCharacter.Meta.PATHMAKER_CHARACTER);
        BaseMod.addPotion(OphidiansIchor.class, Color.FIREBRICK, Color.BLUE, Color.BLUE, OphidiansIchor.POTION_ID, PathmakerCharacter.Meta.PATHMAKER_CHARACTER);
        BaseMod.addPotion(BlessedWater.class, Color.SKY, null, null, BlessedWater.POTION_ID, PathmakerCharacter.Meta.PATHMAKER_CHARACTER);
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = PathmakerMod.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + PathmakerMod.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }

        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(PathmakerMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        PathmakerCharacter.Meta.registerCharacter();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards

        BaseMod.addDynamicVariable(new secondMagicVar());
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }


    @Override
    public void receiveAddAudio() {
        ///A fantastical "Pop" sound for blooming
        BaseMod.addAudio(BLOOMSOUNDKEY, "pathmaker/audio/Sprout.wav");
        ///A "Rattle" sound for when an oasis is draw
        BaseMod.addAudio(OASISREACHEDKEY, "pathmaker/audio/Rattle2.ogg");
        ///A "Zinger" for when an Oasis is drawn (Unused)
        BaseMod.addAudio(OASISDRAWKEY, "pathmaker/audio/OasisPlayable2.ogg");
        ///A "Rustle" sound for when a Flower is channeled
        BaseMod.addAudio(FLOWERSHANNELSOUNDKEY, "pathmaker/audio/FlowerChannel.ogg");
        ///A "Picking" sound for when a Flower is used
        BaseMod.addAudio(FLOWEREVOKESOUNDKEY, "pathmaker/audio/FlowerEvoke.ogg");
        ///A rustling "Wind" sound for when Growing
        BaseMod.addAudio(GROWSOUNDKEY, "pathmaker/audio/WindSound2.ogg");
    }





    @Override
    public void receiveStartGame() {
        ///Change our HP and Gold icons to game-matching ones if it's enabled
        UpdateIcons();
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        AttemptBloom(abstractCard);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        ResetBloom();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        ResetBloom();
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        if(CanTriggerOasis){
            AbstractDungeon.actionManager.addToBottom(new OasisAction());
        }
    }
    @Override
    public void receiveOnPlayerTurnStart() {
        CanTriggerOasis = false;
    }
    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                CanTriggerOasis = true;
                isDone = true;
            }
        });
    }
}
