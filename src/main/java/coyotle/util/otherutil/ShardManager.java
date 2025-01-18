package coyotle.util.otherutil;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import coyotle.cards.AbstractShardCard;
import coyotle.cards.generated.Mechanics.*;
import coyotle.powers.custompowers.WakunaCrowfeatherPower;
import coyotle.relics.BrightmoonBrave;
import coyotle.util.CustomActions.CreateShardAction;
import coyotle.util.CustomActions.ShardsToBottomAction;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.SoundManager.*;
import static coyotle.util.otherutil.Wiz.ValidForShards;
import static coyotle.util.otherutil.variables.Variables.p;

public class ShardManager {
    public static final int UniqueEffectsBaseCap = 3;
    public static final String BloodKey = makeID("BloodKey");
    public static final String DiamondKey = makeID("DiamondKey");
    public static final String RubyKey = makeID("RubyKey");
    public static final String SapphireKey = makeID("SapphireKey");
    public static final String WildKey = makeID("WildKey");

    public static final String HitcountKey = makeID("HitcountKey");
    public static final String BlockCountKey = makeID("BlockCountKey");
    public static final String XCostKey = makeID("XCostKey");
    public static final String ContinueKey = makeID("ContinueKey");
    public static final String HitAllKey = makeID("HitAllKey");
    public static final String RandomKey = makeID("RandomKey");
    public static final String VulnKey = makeID("VulnKey");
    public static final String VulnAllKey = makeID("VulnAllKey");
    public static final String VigorKey = makeID("VigorKey");
    public static final String BlockNextKey = makeID("BlockNextKey");
    public static final String ThornsKey = makeID("ThornsKey");
    public static final String WeakKey = makeID("WeakKey");
    public static final String WeakAllKey = makeID("WeakAllKey");
    public static final String BlurKey = makeID("BlurKey");
    public static final String DrawKey = makeID("DrawKey");
    public static final String EnergyKey = makeID("EnergyKey");
    public static final String MillKey = makeID("MillKey");
    public static final String ValorDrawKey = makeID("ValorDrawKey");
    public static final String ValorMillKey = makeID("ValorMillKey");


    public static void CombatStartShard(){
        if(ValidForShards()){
            if(p() != null){
                boolean MakeShard = true;
                for(AbstractCard c: p().drawPile.group){
                    if (c instanceof AbstractShardCard) {
                        MakeShard = false;
                        break;
                    }
                }
                AbstractDungeon.actionManager.addToTop(new ShardsToBottomAction());
                if(MakeShard){
                    AbstractDungeon.actionManager.addToTop(new CreateShardAction());
                }
            }
        }
    }

    public static AbstractShardCard RandomShard(){
        if(p() != null){
            if(p().hasRelic(BrightmoonBrave.ID)){
                AbstractRelic r = p().getRelic(BrightmoonBrave.ID);
                if(!r.usedUp){
                    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p(), r));
                    r.usedUp();
                    return new HowlingBluegrass();
                }
            }
            if(p().hasPower(WakunaCrowfeatherPower.POWER_ID)){
                return new HowlingBluegrass();
            }
        }
        int i = 0;
        if(p() != null){
            i = AbstractDungeon.miscRng.random(0, 5);
        }

        switch(i) {
            case 1:
                return new BloodShard();
            case 2:
                return new DiamondShard();
            case 3:
                return new RubyShard();
            case 4:
                return new SapphireShard();
            case 5:
                return new ShardsofFate();
            default:
                return new HowlingBluegrass();
        }
    }




    private static final int Bloodx = 1;
    private static final int BloodxUpg = 2;
    private static final int DiamondBlock = 2;
    private static final int DiamondBlockUpg = 3;
    private static final int RubyDmg = 3;
    private static final int RubyDmgUpg = 1;
    private static final int SapphireMagic = 1;
    private static final int SapphireMagicUpg = 1;

    public static int GetBloodx(int Num, AbstractCard card){
        int i = Bloodx;
        if(card.upgraded){
            i += BloodxUpg;
        }
        return ((Num + GetWildCount(card)) * i);
    }
    public static int GetDiamondBlock(int Num, AbstractCard card){
        int i = DiamondBlock;
        if(card.upgraded){
            i += DiamondBlockUpg;
        }
        return ((Num + GetWildCount(card)) * i);
    }
    public static int GetSapphireMagic(int Num, AbstractCard card){
        int i = SapphireMagic;
        if(card.upgraded){
            i += SapphireMagicUpg;
        }
        return ((Num + GetWildCount(card))/2) * i;
    }
    public static int GetRubyDamage(int Num, AbstractCard card){
        int i = RubyDmg;
        if(card.upgraded){
            i += RubyDmgUpg;
        }
        return ((Num + GetWildCount(card)) * i);
    }
    public static int GetWildCount(AbstractCard card){
        int i = 0;
        if(card instanceof AbstractShardCard){
            i = ((AbstractShardCard) card).WildNum;
        }
        return i;
    }

    public static String GetShardSound(AbstractCard c) {
        if(c != null){
            if(c instanceof HowlingBluegrass){
                return COYOTLESHARDSOUNDKEY;
            }
            if(c instanceof BloodShard){
                return BLOODSHARDSOUNDKEY;
            }
            if(c instanceof DiamondShard){
                return DIAMONDSHARDSOUNDKEY;
            }
            if(c instanceof RubyShard){
                return RUBYSHARDSOUNDKEY;
            }
            if(c instanceof SapphireShard){
                return SAPPHIRESHARDSOUNDKEY;
            }
            if(c instanceof WildShard){
                return WILDSHARDSOUNDKEY;
            }
        }
        return FATESHARDSOUNDKEY;
    }


    public static int UniqueEffectsCap(AbstractShardCard c){
        //if(c != null){
        //
        //}
        return UniqueEffectsBaseCap;
    }
    ///The number of "Special" effects on a Shard so that we can't just stack magicnumber effects on it
    public static int UniqueEffects(AbstractShardCard c){
        int i = 0;
        if(c != null){
            if(c.Vuln){
                i += 1;
            }
            if(c.VulnAll){
                i += 1;
            }
            if(c.Vigor){
                i += 1;
            }

            if(c.Thorns){
                i += 1;
            }
            if(c.Weak){
                i += 1;
            }
            if(c.WeakAll){
                i += 1;
            }
            if(c.Blur){
                i += 1;
            }
            if(c.BlockNext){
                i += 1;
            }

            if(c.Draw){
                i += 1;
            }
            if(c.Energy){
                i += 1;
            }
            if(c.Mill){
                i += 1;
            }

            if(c.ValorDraw){
                i += 1;
            }
            if(c.ValorHand){
                i += 1;
            }
        }
        return i;
    }
}
