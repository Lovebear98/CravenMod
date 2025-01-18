package pathmaker.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.relics.CursedKey;
import pathmaker.character.PathmakerCharacter;
import pathmaker.orbs.AttackFlower;
import pathmaker.orbs.PowerFlower;
import pathmaker.orbs.SkillFlower;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.CustomTags.Oasis;
import static pathmaker.util.otherutil.variables.Variables.p;

public class CobrasSerpentine extends BaseRelic {
    private static final String NAME = CobrasSerpentine.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.MAGICAL;
    public CobrasSerpentine() {
        super(ID, NAME, PathmakerCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.flash();
        addToBot(new ChannelAction(GetFlower()));
    }

    public void onEquip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    private AbstractOrb GetFlower() {
        int i;
        if(p() != null){
            i = AbstractDungeon.miscRng.random(1, 3);
        }else{
            i = MathUtils.random(1, 3);
        }

        switch(i){
            case 1:
                return new AttackFlower();
            case 2:
                return new SkillFlower();
            case 3:
                return new PowerFlower();
            default:
                return new Plasma();
        }
    }
}
