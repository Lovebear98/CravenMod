package craven.relics;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.MinionPower;
import craven.character.CravenCharacter;
import craven.potions.custompotions.IchorBordeaux;

import static craven.CravenMod.makeID;
import static craven.util.otherutil.variables.Variables.p;

public class WineCasket extends AtlasRelic {

    private static final String NAME = WineCasket.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.FLAT;
    public WineCasket() {
        super(ID, NAME, CravenCharacter.Meta.CARD_COLOR, RARITY, SOUND);

        AbstractPotion p = new IchorBordeaux();
        p.initializeData();
        this.tips.add(new PowerTip(p.name, p.description));
    }

    @Override
    public void onEquip() {
        super.onEquip();
        boolean AddedPotion = false;
        for(int i = p().potions.size()-1; i > 0; i -= 1){
            AbstractPotion p = p().potions.get(i);
            if((p instanceof PotionSlot)){
                p().obtainPotion(i, new IchorBordeaux());
                AddedPotion = true;
                break;
            }
        }

        if(!AddedPotion){
            p().obtainPotion(p().potions.size()-1, new IchorBordeaux());
        }
    }

    @Override
    public void atTurnStart() {
        UnlockPotion();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
    }

    @Override
    public void onVictory() {
        super.onVictory();
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0 && !m.hasPower(MinionPower.POWER_ID)) {
            ///this.flash();
            ///this.addToBot(new RelicAboveCreatureAction(m, this));
            BuffPotion();
        }
    }

    @Override
    public boolean canSpawn() {
        if(p() != null){
            if(p().hasPotion(PotionSlot.POTION_ID)){
                return super.canSpawn();
            }
        }
        return false;
    }

    IchorBordeaux PermPot = null;
    private void CheckForPotion() {
        if(PermPot == null){
            for(AbstractPotion p : p().potions){
                if(p instanceof IchorBordeaux){
                    PermPot = (IchorBordeaux) p;
                    break;
                }
            }
        }
    }
    private void BuffPotion() {
        CheckForPotion();
        if(PermPot != null){
            PermPot.AddStack();
        }
    }
    private void UnlockPotion() {
        CheckForPotion();
        if(PermPot != null){
            PermPot.UsedThisTurn = false;
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        UnlockPotion();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
