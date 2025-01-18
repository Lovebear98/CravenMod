package coyotle.powers.custompowers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import coyotle.patches.interfaces.DreamInterface;
import coyotle.util.TextureLoader;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.variables.Variables.PrintEnergy;

public class RhythmicSpiritualistPower extends TwoAmountPower implements CloneablePowerInterface, DreamInterface, BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID(RhythmicSpiritualistPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;
    private final AbstractPlayer source;


    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("coyotle/images/powers/large/RhythmicSpiritualistPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("coyotle/images/powers/RhythmicSpiritualistPower.png");
    public static Color myColor = new Color(255/255f, 255/255f, 0/255f, 1);
    public static Color myColor2 = new Color(0.529F, 0.922F, 0.7f, 1);

    public RhythmicSpiritualistPower(AbstractCreature owner, int draw) {
        this(owner, draw, 1);
    }

    public RhythmicSpiritualistPower(AbstractCreature owner, int draw, int energy) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = AbstractDungeon.player;
        this.amount = draw;
        amount2 = energy;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
    }




    public void updateDescription() {
        String s = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if(amount > 1){
            s += DESCRIPTIONS[2];
        }
        s += DESCRIPTIONS[3] + DESCRIPTIONS[4] + PrintEnergy(amount2) + DESCRIPTIONS[5];
        this.description = s;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new DrawCardAction(amount));
    }

    @Override
    public void onExitDream() {
        DreamInterface.super.onExitDream();
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        addToBot(new ApplyPowerAction(owner, owner, new EnergizedPower(owner, amount2)));
    }




    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (this.amount2 > 0) {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, myColor);
        }
    }






    @Override
    public AbstractPower makeCopy() {
        return new RhythmicSpiritualistPower(owner, amount, amount2);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(abstractPower instanceof RhythmicSpiritualistPower){
            this.amount2 += ((RhythmicSpiritualistPower) abstractPower).amount2;
        }
        return true;
    }
}
