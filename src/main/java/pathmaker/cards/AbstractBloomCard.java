package pathmaker.cards;

import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import pathmaker.patches.interfaces.BloomInterface;
import pathmaker.patches.interfaces.OasisReachedInterface;
import pathmaker.util.CardStats;

import static pathmaker.util.CustomTags.Bloom;
import static pathmaker.util.otherutil.BloomManager.*;

public abstract class AbstractBloomCard extends BaseCard implements BloomInterface, OasisReachedInterface {

    ///The base magic number
    public int baseSecondMagic;
    ///The "Upgrade" number of the second magic
    protected int secondMagicUpgrade;
    ///The current second magicr
    public int secondMagic;
    ///Whether the Magic Number CAN upgrade
    private boolean secondMagicCanUpgrade;
    ///If the Second Magic is upgraded
    public boolean secondMagicUpgraded;

    ///Whether the second magic is modified
    public boolean isSecondMagicModified;
    private boolean UpgradePreviewCard = true;


    public AbstractBloomCard(String ID, CardStats info) {
        super(ID, info);
        ///It's not upgraded by default
        this.secondMagicUpgraded = false;
        ///It can't upgrade by default
        this.secondMagicCanUpgrade = false;
        ///It's -1 by default
        this.baseSecondMagic = -1;
        ///It doesn't upgrade by default
        this.secondMagicUpgrade = 0;
        ///It's not modified by default
        this.isSecondMagicModified = false;
    }


    ///Set the second magic stats on the card
    protected final void setSecondMagic(int magic)
    {
        this.setSecondMagic(magic, 0);
    }
    protected final void setSecondMagic(int base, int upg) {
        this.baseSecondMagic = this.secondMagic = base;
        if (upg != 0) {
            this.secondMagicCanUpgrade = true;
            this.secondMagicUpgrade = upg;
        }
    }


    ///When we upgrade the card, if it isn't upgraded, if the second magic upgrades, upgrade the second magic.
    @Override
    public void upgrade() {
        if(!upgraded){
            if (secondMagicCanUpgrade) {
                this.upgradeSecondMagic(secondMagicUpgrade);
            }
        }
        if(UpgradePreviewCard && this.cardsToPreview != null){
            cardsToPreview.upgrade();
        }
        super.upgrade();
    }

    ///Upgrade our second magic
    protected void upgradeSecondMagic(int amount) {
        this.baseSecondMagic += amount;
        this.secondMagic = this.baseSecondMagic;
        this.secondMagicUpgraded = true;
    }

    ///When we copy this card, pass along its second magic
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        ///Get the default first
        AbstractCard card = super.makeStatEquivalentCopy();

        //Modify it if it fits
        if (card instanceof AbstractBloomCard) {
            ///Pass along whether it CAN upgrade
            ((AbstractBloomCard) card).secondMagicCanUpgrade = this.secondMagicCanUpgrade;
            ///Pass along whether it's upgraded
            ((AbstractBloomCard) card).secondMagicUpgraded = this.secondMagicUpgraded;
            ///How much it upgrades by
            ((AbstractBloomCard) card).secondMagicUpgrade = this.secondMagicUpgrade;
            ///Its base amount
            ((AbstractBloomCard) card).baseSecondMagic = this.baseSecondMagic;
            ///And its current amount
            ((AbstractBloomCard) card).secondMagic = this.secondMagic;
        }

        ///Then return the result
        return card;
    }

    ///When we're supposed to show upgrades, upgrade our second magic and color it
    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        ///If it's upgraded
        if (this.secondMagicUpgraded) {
            ///Show the un-modified base
            this.secondMagic = this.baseSecondMagic;
            ///And note that it's modified
            this.isSecondMagicModified = true;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(this.tags.contains(Bloom)){
            if(BloomReady()){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }
    }

    protected void PayXCost(int i){
        if(!freeToPlay()){
            addToBot(new LoseEnergyAction(energyOnUse));
        }
    }

    protected int GetXEnergy(int i){
        int e = i;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            e += 2;
        }
        return e;
    }

    protected void setSecondDamage(int base, int upg){
        setCustomVar("D2", VariableType.DAMAGE, base, upg,
                (card, m, val)->{ //Do things before calculating damage
                    return val;
                },
                (card, m, val)->{ //Do things after calculating damage
                    return val;
                });
    }

    protected int getSecondDamage(){
        return customVar("D2");
    }
}
