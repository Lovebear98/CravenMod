package pathmaker.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import pathmaker.character.PathmakerCharacter;
import pathmaker.orbs.AttackFlower;
import pathmaker.orbs.PowerFlower;
import pathmaker.orbs.SkillFlower;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.variables.Variables.p;

public class FalconsMoonstone extends BaseRelic {
    private static final String NAME = FalconsMoonstone.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int amount = 3;
    public FalconsMoonstone() {
        super(ID, NAME, PathmakerCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + 1 + DESCRIPTIONS [2] + amount + DESCRIPTIONS[3];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, amount)));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        this.counter = 0;
        unusedUp();
    }


    @Override
    public void onVictory() {
        super.onVictory();

        this.counter = 0;
        unusedUp();
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if(this.counter < amount){
            this.flash();
            this.counter += 1;
            addToBot(new ReducePowerAction(p(), p(), DexterityPower.POWER_ID, 1));
            if(this.counter == amount){
             this.usedUp();
            }
        }
    }


    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
}
