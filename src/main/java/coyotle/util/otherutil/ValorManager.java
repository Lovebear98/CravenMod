package coyotle.util.otherutil;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import coyotle.cards.generated.Valor;

import static coyotle.util.otherutil.SoundManager.PlaySound;
import static coyotle.util.otherutil.SoundManager.VALORSOUNDKEY;
import static coyotle.util.otherutil.Wiz.FlashCard;
import static coyotle.util.otherutil.variables.Variables.p;

public class ValorManager {

    public static int ValorsPlayed = 0;
    public static final int BaseValorCap = 30;
    public static final int ValorDrawThreshold = 15;
    public static final int ValorUpgradeThreshold = 25;
    public static final int ValorBlockThreshold = 30;
    public static final int BaseValorDamageGrowth = 2;
    public static final int BaseValorDraw = 1;
    public static final int BaseValorBlock = 4;

    public static void IncreaseValor(){
        IncreaseValor(1);
    }

    public static void IncreaseValor(int i) {
        int e = ValorBreakpointsReached();
        boolean ExceededGrowthCap = false;
        ValorsPlayed += i;
        if(ValorsPlayed > ValorCap()){
            ExceededGrowthCap = true;
        }
        if((e != ValorBreakpointsReached()) || ValorsPlayed == ValorUpgradeThreshold || ValorsPlayed == ValorBlockThreshold || ValorsPlayed == ValorDrawThreshold){
            if(!ExceededGrowthCap){
                UpdateValors();
                PlaySound(VALORSOUNDKEY, 0.2f, 0.2f);
            }
        }
    }

    public static void DecreaseValor(){
        DecreaseValor(1);
    }

    public static void DecreaseValor(int i) {
        int e = ValorBreakpointsReached();
        ValorsPlayed -= i;
        if(ValorsPlayed < 0){
            ValorsPlayed = 0;
        }
        if((e != ValorBreakpointsReached()) || ValorsPlayed == ValorUpgradeThreshold || ValorsPlayed == ValorBlockThreshold || ValorsPlayed == ValorDrawThreshold){
            UpdateValors();
        }
    }

    private static void UpdateValors() {
        if(p() != null){
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c instanceof Valor){
                    FlashCard(c, Color.YELLOW.cpy());
                    if(ValorsPlayed == ValorUpgradeThreshold){
                        c.upgrade();
                    }
                    c.initializeDescription();
                }
            }
            for(AbstractCard c: AbstractDungeon.player.drawPile.group){
                if(c instanceof Valor){
                    if(ValorsPlayed == ValorUpgradeThreshold){
                        c.upgrade();
                    }
                    c.initializeDescription();
                }
            }
            for(AbstractCard c: AbstractDungeon.player.discardPile.group){
                if(c instanceof Valor){
                    if(ValorsPlayed == ValorUpgradeThreshold){
                        c.upgrade();
                    }
                    c.initializeDescription();
                }
            }
        }
    }

    public static int ValorBreakpointsReached(){
        if(ValorsPlayed == 0){
            return 0;
        }

        int i = ValorsPlayed/5;

        if(ValorsPlayed >= ValorDrawThreshold){
            i -= 1;
        }
        if(ValorsPlayed >= ValorUpgradeThreshold){
            i -= 1;
        }
        if(ValorsPlayed >= ValorBlockThreshold){
             i -= 1;
        }

        if(i < 0){
            i = 0;
        }
        return i;
    }

    public static int ValorCap(){
        return BaseValorCap;
    }

    public static int ValorDamageGrowth(){
        return BaseValorDamageGrowth;
    }

    public static int ValorDraw(boolean upgraded){
        return BaseValorDraw;
    }

    public static int ValorBlock(boolean upgraded){
        return BaseValorBlock;
    }

    public static void ResetValor(){
        ValorsPlayed = 0;
    }

}
