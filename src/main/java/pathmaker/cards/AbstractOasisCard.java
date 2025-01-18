package pathmaker.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathmaker.cardsmods.OasisMod;
import pathmaker.util.CardStats;
import pathmaker.util.CustomActions.OasisReachedAction;

import static pathmaker.util.CustomTags.Oasis;
import static pathmaker.util.otherutil.ConfigManager.EnableSounds;
import static pathmaker.util.otherutil.SoundManager.*;
import static pathmaker.util.otherutil.variables.GeneralUtils.FlashCard;
import static pathmaker.util.otherutil.variables.GeneralUtils.OasisColor;

public abstract class AbstractOasisCard extends AbstractBloomCard {

    public static boolean CanTriggerOasis = false;

    ///How many times we'll need to draw cards to unbrick the Oasis
    public AbstractOasisCard(String ID, CardStats info) {
        super(ID, info);
        CardModifierManager.addModifier(this, new OasisMod());
        tags.add(Oasis);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    ///When we draw other cards with this card in hand, tick down the Oasis Count
    ///When it hits 0, the card becomes playable
    public void onDrawOtherCard(){
        if(CanTriggerOasis){
            if(this.secondMagic > 0){
                this.secondMagic -= 1;

                if(this.secondMagic <= 0){
                    if(EnableSounds){
                        PlaySound(OASISREACHEDKEY, -0.2f, 0.2f);
                    }
                    FlashCard(this, OasisColor());
                    addToBot(new OasisReachedAction(this));
                }
            }
        }
    }


    @Override
    public void upgrade() {
        boolean StartedAlive = false;
        if(this.secondMagic > 0){
            StartedAlive = true;
        }
        super.upgrade();
        if(this.secondMagic < 0){
            this.secondMagic = 0;
            if(StartedAlive){
                if(EnableSounds){
                    PlaySound(OASISREACHEDKEY, -0.2f, 0.2f);
                }
                FlashCard(this, OasisColor());
                addToBot(new OasisReachedAction(this));
            }
        }
    }

    ///Sound stinger when we draw the card
    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
    }

    ///We can only use the card if the OasisCount is 0
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(secondMagic <= 0){
            return super.canUse(p, m);
        }else{
            return false;
        }
    }
}
