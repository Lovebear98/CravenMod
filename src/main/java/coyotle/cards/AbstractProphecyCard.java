package coyotle.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import coyotle.util.CardStats;

import static coyotle.util.otherutil.Wiz.FlashCard;

public abstract class AbstractProphecyCard extends BaseCard {

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
    protected boolean UpgradePreviewCard = true;


    public AbstractProphecyCard(String ID, CardStats info) {
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

        CurrentType = this.type;
    }


    ///Set the second magic stats on the card
    protected final void setSecondMagic(int magic)
    {
        this.setSecondMagic(magic, 0);
    }
    public final void setSecondMagic(int base, int upg) {
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
        if (card instanceof AbstractProphecyCard) {
            ///Pass along whether it CAN upgrade
            ((AbstractProphecyCard) card).secondMagicCanUpgrade = this.secondMagicCanUpgrade;
            ///Pass along whether it's upgraded
            ((AbstractProphecyCard) card).secondMagicUpgraded = this.secondMagicUpgraded;
            ///How much it upgrades by
            ((AbstractProphecyCard) card).secondMagicUpgrade = this.secondMagicUpgrade;
            ///Its base amount
            ((AbstractProphecyCard) card).baseSecondMagic = this.baseSecondMagic;
            ///And its current amount
            ((AbstractProphecyCard) card).secondMagic = this.secondMagic;
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


    protected void PayXCost(){
        if(!freeToPlay()){
            addToBot(new LoseEnergyAction(energyOnUse));
        }
    }

    protected void SpendXEnergy(){
        PayXCost();
    }

    protected int GetXEnergy(int i){
        int e = i;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            e += 2;
        }
        return e;
    }

    protected int GetXEnergy(){
        return GetXEnergy(energyOnUse);
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

    protected void setSecondBlock(int base, int upg){
        setCustomVar("B2", VariableType.BLOCK, base, upg,
                (card, m, val)->{ //Do things before calculating damage
                    return val;
                },
                (card, m, val)->{ //Do things after calculating damage
                    return val;
                });
    }

    protected int getSecondBlock(){
        return customVar("B2");
    }


    protected static final String BasePath = "coyotle/images/cards";
    protected static final String AttackPath = "/attack/";
    protected static final String SkillPath = "/skill/";
    protected static final String PowerPath = "/power/";
    protected static final String CursePath = "/curse/";
    protected static final String StatusPath = "/status/";
    protected static final String PortraitPath ="_p";
    protected static final String PathExtension =".png";


    protected CardType CurrentType;
    protected void UpdateImage(){
        if(this.type != CurrentType){
            String s;
            switch(this.type){
                case ATTACK:
                    s = AttackPath;
                    break;
                case SKILL:
                    s = SkillPath;
                    break;
                case POWER:
                    s = PowerPath;
                    break;
                case CURSE:
                    s = CursePath;
                    break;
                default:
                    s = StatusPath;
                    break;
            }
            s = BasePath + s + this.getClass().getSimpleName();
            loadCardImage(s+PathExtension);
            CurrentType = this.type;

            FlashCard(this, GlowColor());
        }
    }
    protected Color GlowColor() {
        return Color.WHITE.cpy();
    }
}
