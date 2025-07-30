package craven.relics;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import craven.character.CravenCharacter;
import craven.util.TextureLoader;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class DinnerMenu extends AtlasRelic implements CustomSavable<Integer> {

    private static final String NAME = DinnerMenu.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.SOLID;


    private boolean CloneCard = true;
    private int CardType = 4;
    private static final int CounterCap = 4;


    private static final Texture ATKImg = TextureLoader.getTexture("craven/images/relics/AttackMenu.png");
    private static final Texture SKLImg = TextureLoader.getTexture("craven/images/relics/SkillMenu.png");
    private static final Texture PWRImg = TextureLoader.getTexture("craven/images/relics/PowerMenu.png");
    private static final Texture ALLImg = TextureLoader.getTexture("craven/images/relics/DinnerMenu.png");
    private static final Texture LATKImg = TextureLoader.getTexture("craven/images/relics/large/AttackMenu.png");
    private static final Texture LSKLImg = TextureLoader.getTexture("craven/images/relics/large/SkillMenu.png");
    private static final Texture LPWRImg = TextureLoader.getTexture("craven/images/relics/large/PowerMenu.png");
    private static final Texture LALLImg = TextureLoader.getTexture("craven/images/relics/large/DinnerMenu.png");

    public DinnerMenu() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;

        description = DESCRIPTIONS[11];
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public void onEquip() {
        super.onEquip();
        Randomize();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(MatchType(card)){
            if (!card.purgeOnUse && CloneCard) {
                addToBot(new RelicAboveCreatureAction(p(), this));
                AbstractMonster m = null;
                if (action.target != null) {
                    m = (AbstractMonster)action.target;
                }

                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
                this.counter = CounterCap;
                CloneCard = false;
                Randomize();
            }
        }
    }

    private boolean MatchType(AbstractCard card) {
        if(GetType() == AbstractCard.CardType.STATUS){
            return true;
        }
        return GetType() == card.type;
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        if(this.counter > 0){
            this.counter -= 1;
            if(this.counter <= 0){
                this.CloneCard = true;
                this.flash();
            }
            fixDescription();
        }
    }

    @Override
    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;
    }

    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
    }

    @Override
    public String getUpdatedDescription() {
        String s = "";
        if(this.counter > 0){
            s += DESCRIPTIONS[0]+this.counter+DESCRIPTIONS[1];
        }
        if(this.counter > 1){
            s += DESCRIPTIONS[2];
        }

        if(this.counter == 0){
            s += DESCRIPTIONS[3];
        }else{
            s += DESCRIPTIONS[4];
        }

        s += DESCRIPTIONS[5] + GetTypeString() + DESCRIPTIONS[10];
        return s;
    }

    /// Get our card type string from out card type
    private String GetTypeString() {
        switch(CardType){
            case 1: return DESCRIPTIONS[6];
            case 2: return DESCRIPTIONS[7];
            case 3: return DESCRIPTIONS[8];
            default: return DESCRIPTIONS[9];
        }
    }

    /// Get our card type from our card type int
    private AbstractCard.CardType GetType(){
        switch(CardType){
            case 1: return AbstractCard.CardType.ATTACK;
            case 2: return AbstractCard.CardType.SKILL;
            case 3: return AbstractCard.CardType.POWER;
            default: return AbstractCard.CardType.STATUS;
        }
    }

    /// When we spawn or trigger, randomize our card type
    private void Randomize() {
        if(p() != null){
            CardType = AbstractDungeon.miscRng.random(1, 3);
        }else{
            CardType = MathUtils.random(1, 3);
        }
        FixSelf();
    }

    /// Fix our image and description
    private void FixSelf() {
        switch(CardType){
            case 1:
                this.img = ATKImg;
                this.largeImg = LATKImg;
            break;
            case 2:
                this.img = SKLImg;
                this.largeImg = LSKLImg;
                break;
            case 3:
                this.img = PWRImg;
                this.largeImg = LPWRImg;
                break;
            default:
                this.img = ALLImg;
                this.largeImg = LALLImg;
        }
        fixDescription();
    }

    @Override
    public Integer onSave() {
        return this.CardType;
    }

    @Override
    public void onLoad(Integer integer) {
        if(integer != null){
            this.CardType = integer;
        }else{
            this.CardType = MathUtils.random(1, 3);
        }

        FixSelf();
    }
}
