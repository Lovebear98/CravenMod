package coyotle.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import coyotle.cards.generated.Valor;
import coyotle.cardsmods.ShardCardMod;
import coyotle.character.CoyotleCharacter;
import coyotle.util.CardStats;
import coyotle.util.CustomActions.MillAction;
import coyotle.util.CustomActions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static coyotle.util.CustomTags.Shard;
import static coyotle.util.otherutil.ShardManager.*;
import static coyotle.util.otherutil.variables.Variables.p;


public abstract class AbstractShardCard extends AbstractProphecyCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;
    public static final String ID = makeID(AbstractShardCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CoyotleCharacter.Meta.CARD_COLOR,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    /** REMEMBER TO UPDATE ANYTHING YOU ADD TO THESE IN MAKECOPY ON SHARDS */

    /// Keep track of our number of Thresholds of each type
    public int BloodNum = 0;
    public int DiamondNum = 0;
    public int RubyNum = 0;
    public int SapphireNum = 0;
    public int WildNum = 0;

    public boolean NoEffects = true;

    public int Hitcount = 1;
    public int BlockCount = 1;
    ///Generic conditions
    public boolean XCost = false;
    public boolean Continue = false;

    ///Offense related conditions
    public boolean HitAll = false;
    public boolean Random = false;
    public boolean Vuln = false;
    public boolean VulnAll = false;
    public boolean Vigor = false;

    ///Defense related conditions
    public boolean BlockNext = false;
    public boolean Thorns = false;
    public boolean Weak = false;
    public boolean WeakAll = false;
    public boolean Blur = false;

    ///Utility related conditions
    public boolean Draw = false;
    public boolean Energy = true;
    public boolean Mill = false;

    public boolean ValorDraw = false;
    public boolean ValorHand = false;
    ///Bools for normal card functions being enabled
    public boolean NormalBlock = true;


    public AbstractShardCard(String ID, CardStats info) {
        super(ID, info);
        CardModifierManager.addModifier(this, new ShardCardMod());
        tags.add(Shard);
        PurgeField.purge.set(this, true);
    }

    @Override
    public void applyPowers() {
        this.isMultiDamage = HitAll;
        FixStats();
        super.applyPowers();
        FixTargetAndType();
    }

    private void FixStats() {

        List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(this, ShardCardMod.CardModID);
        if(!sameMod.isEmpty()){
            AbstractCardModifier a = sameMod.get(0);
            if(a instanceof ShardCardMod){
                ((ShardCardMod)a).statFix(GetRubyDamage(), GetDiamondBlock(), GetSapphireMagic());
            }
        }
    }

    private void FixTargetAndType() {
        boolean TargetsOthers = false;
        boolean AffectsSelf = false;

        if(this.damage > 0 || this.Vuln || this.Weak){
            TargetsOthers = true;
        }
        if(this.block > 0 || this.Blur || this.Thorns || this.Vigor){
            AffectsSelf = true;
        }

        if(AffectsSelf && !TargetsOthers){
            this.target = CardTarget.SELF;
        }
        if(!AffectsSelf && TargetsOthers){
            this.target = CardTarget.ENEMY;
        }
        if(AffectsSelf && TargetsOthers && !(Random || HitAll)){
            this.target = CardTarget.SELF_AND_ENEMY;
        }
        if(!AffectsSelf && (Random || HitAll)){
            this.target = CardTarget.ALL_ENEMY;
        }
        if(AffectsSelf && (Random || HitAll)){
            this.target = CardTarget.ALL;
        }

        if(this.damage > 0){
            this.type = CardType.ATTACK;
        }else{
            this.type = CardType.SKILL;
        }
        UpdateImage();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            if(XCost){
                PayXCost();
                int e = GetXEnergy();
                if(e > 0){
                    for(int i = e; i > 0; i -= 1){
                        actualOnUse(p, m);
                    }
                }
                ///Do any damage-affecting stuff AFTER any damage
                for(int i = e; i > 0; i -= 1){
                    if(Vigor){
                        addToBot(new ApplyPowerAction(p(), p(), new VigorPower(p(), magicNumber)));
                    }
                    if(Vuln){
                        if(Random){
                            addToBot(new RandomVulnerableAction(magicNumber));
                        }else{
                            addToBot(new ApplyPowerAction(m, p(), new VulnerablePower(p(), magicNumber, false)));
                        }
                    }
                    if(VulnAll){
                        addToBot(new VulnAllAction(p(), magicNumber));
                    }
                }
            }else{
                actualOnUse(p, m);
            }
    }

    public void actualOnUse(AbstractPlayer p, AbstractMonster m) {
        if(block > 0){
            for(int i = Hitcount; i > 0; i -= 1){
                addToBot(new GainBlockAction(p(), block));
            }
            if(BlockNext){
                addToBot(new ApplyPowerAction(p(), p(), new NextTurnBlockPower(p(), block)));
            }
        }

        if(damage > 0){
            for(int i = Hitcount; i > 0; i -= 1){
                if(HitAll){
                    addToBot(new DamageAllEnemiesAction(p(), damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }else{
                    if(Random){
                        addToBot(new DamageRandomEnemyAction(new DamageInfo(p(), damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    }else{
                        addToBot(new DamageAction(m, new DamageInfo(p(), damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    }
                }
            }
        }

        if(magicNumber > 0){
            if(Vigor && !XCost){
                addToBot(new ApplyPowerAction(p(), p(), new VigorPower(p(), magicNumber)));
            }
            if(Thorns){
                addToBot(new ApplyPowerAction(p(), p(), new ThornsPower(p(), magicNumber)));
            }
            if(Vuln && !XCost){
                if(Random){
                    addToBot(new RandomVulnerableAction(magicNumber));
                }else{
                    addToBot(new ApplyPowerAction(m, p(), new VulnerablePower(p(), magicNumber, false)));
                }
            }
            if(VulnAll && !XCost){
                addToBot(new VulnAllAction(p(), magicNumber));
            }
            if(Weak){
                if(Random){
                    addToBot(new RandomWeakAction(magicNumber));
                }else{
                    addToBot(new ApplyPowerAction(m, p(), new WeakPower(p(), magicNumber, false)));
                }
            }
            if(WeakAll){
                addToBot(new WeakAllAction(p(), magicNumber));
            }
            if(Draw){
                addToBot(new DrawCardAction(magicNumber));
            }
            if(Energy){
                addToBot(new GainEnergyAction(magicNumber));
            }
            if(Mill){
                addToBot(new MillAction(magicNumber));
            }
            if(Blur){
                addToBot(new ApplyPowerAction(p, p, new BlurPower(p, magicNumber)));
            }
            if(ValorDraw){
                addToBot(new MakeTempCardInDrawPileAction(new Valor(), magicNumber, true, true));
            }
            if(ValorHand){
                addToBot(new MakeTempCardInHandAction(new Valor(), magicNumber));
            }
        }
    }


    ///Methods to add given Thresholds
    protected void setShards(int Blood, int Diamond, int Ruby, int Sapphire, int Wild){
        AddBloodShard(Blood);
        AddDiamondShard(Diamond);
        AddRubyShard(Ruby);
        AddSapphireShard(Sapphire);
        AddWildShard(Wild);
    }
    protected void setShards(int i){
        setShards(i, i, i, i, i);
    }
    public void AddBloodShard(int i){
        this.BloodNum += i;
        if(BloodNum < 0){
            BloodNum = 0;
        }
        for(int e = (i*2); e > 0; e -= 1){
            RemoveRandomStatShard();
        }
        FixCost();
    }



    public void AddDiamondShard(int i){
        this.DiamondNum += i;
        if(DiamondNum < 0){
            DiamondNum = 0;
        }
    }
    public void AddRubyShard(int i){
        this.RubyNum += i;
        if(RubyNum < 0){
            RubyNum = 0;
        }
    }
    public void AddSapphireShard(int i){
        this.SapphireNum += i;
        if(SapphireNum < 0){
            SapphireNum = 0;
        }
        if(SapphireNum > 0 && NoEffects){
            this.Energy = true;
            NoEffects = false;
        }
    }
    public void AddWildShard(int i){
        this.WildNum += i;
        if(WildNum < 0){
            WildNum = 0;
        }
        FixCost();
    }

    int AppliedBloodShards = 0;
    int AppliedWildShards = 0;
    public void FixCost() {
        ///If we're neither an X-cost or a -2 cost
        if(this.cost != -1 && this.cost != -2){
            ///Define everything starting at 0
            int ToIncrease = 0;
            int ToDecrease = 0;
            ///And get our "drift" from our original cost due to cost modifying effects for later
            int CostDifference = this.costForTurn - this.cost;
            ///If we have Wild Shards to apply
            if(this.WildNum > this.AppliedWildShards){
                ///Get the amount of Wild Shards we need to apply
                int WildToApply = this.WildNum - this.AppliedWildShards;
                ///Then make sure we remember that we applied this many
                this.AppliedWildShards = this.WildNum;
                ///And note how much we need to increase the cost by later
                ToIncrease = WildToApply;
            }
            ///If we have Blood shards to apply
            if(this.BloodNum > this.AppliedBloodShards){
                ///Get how many we need to apply
                int BloodToApply = this.BloodNum - this.AppliedBloodShards;
                ///Then increase what we applied by what we ACTUALLY applied
                this.AppliedBloodShards = this.BloodNum;
                ///And mark down how much we need to decrease it later
                ToDecrease = BloodToApply;
            }

            ///Figure out the final change of our card's cost
            int FinalModifier = ToIncrease - ToDecrease;
            ///Then change the card's cost if we should
            if(FinalModifier != 0){
                this.cost = this.cost + FinalModifier;
                this.costForTurn = this.costForTurn + FinalModifier;
            }
            ///Then if our cost was changed in some way before starting
            if(CostDifference != 0){
                ///Update the cost for the turn back to where it was relatively
                this.costForTurn += CostDifference;
            }
            ///If it costs negatives, pop it back to 0
            if(this.cost < 0){
                this.cost = 0;
            }
            if(this.costForTurn < 0){
                this.costForTurn = 0;
            }
        }
    }

    public void BecomeXCost(){
        this.XCost = true;
        this.cost = -1;
        this.costForTurn = -1;
    }



    private static final int DiamondBlock = 2;
    private static final int DiamondBlockUpg = 1;
    private static final int RubyDmg = 3;
    private static final int RubyDmgUpg = 1;
    ///Sapphire bonuses are divided by 2 so we can do "half steps"
    private static final int SapphireMagic = 1;
    private static final int SapphireMagicUpg = 0;

    private int GetDiamondBlock(){
        int i = DiamondBlock;
        if(upgraded){
            i += DiamondBlockUpg;
        }
        return ((DiamondNum + GetWildCount()) * i);
    }
    private int GetSapphireMagic(){
        int i = SapphireMagic;
        if(upgraded){
            i += SapphireMagicUpg;
        }
        int sap = ((SapphireNum + GetWildCount()) * i);
        if(sap == 1){
            sap = 2;
        }
        return sap/2;
    }
    private int GetRubyDamage(){
        int i = RubyDmg;
        if(upgraded){
            i += RubyDmgUpg;
        }
        return ((RubyNum + GetWildCount()) * i);
    }
    private int GetWildCount(){
        return WildNum;
    }


    private void RemoveRandomStatShard() {
        ArrayList<String> list = new ArrayList<>();
        if(DiamondNum > 0){
            list.add(DiamondKey);
        }
        if(RubyNum > 0){
            list.add(RubyKey);
        }
        if(SapphireNum > 0){
            list.add(SapphireKey);
        }
        if(!list.isEmpty()){
            int i;
            if(p() != null){
                i = AbstractDungeon.miscRng.random(0, list.size()-1);
            }else{
                i = MathUtils.random(0, list.size()-1);
            }

            String target = list.get(i);
            if(Objects.equals(target, DiamondKey)){
               AddDiamondShard(-1);
               return;
            }
            if(Objects.equals(target, RubyKey)){
                AddRubyShard(-1);
                return;
            }
            if(Objects.equals(target, SapphireKey)){
                AddSapphireShard(-1);
                return;
            }
        }
    }


    public void FixDescription(){
        String DMGString = "";
        String BLOCKString = "";
        String BLOCKNEXTString = "";
        String VULNString = "";
        String WEAKString = "";
        String DRAWString = "";
        String ENERGYString = "";
        String THORNString = "";
        String VIGORString = "";
        String BLURString = "";
        String MILLString = "";
        String ValorDRAWString = "";
        String ValorHANDString = "";

        if(block > 0){
            BLOCKString += this.cardStrings.EXTENDED_DESCRIPTION[8];
            if(BlockCount > 1){
                BLOCKString += (BlockCount + this.cardStrings.EXTENDED_DESCRIPTION[2]);
            }
            BLOCKString += this.cardStrings.EXTENDED_DESCRIPTION[3];

            if(BlockNext){
                if(!Blank(BLOCKString)){
                    BLOCKNEXTString += Linebreak();
                }
                BLOCKNEXTString += this.cardStrings.EXTENDED_DESCRIPTION[8] + this.cardStrings.EXTENDED_DESCRIPTION[9];
            }
        }

        if(damage > 0){
            if(!Blank(BLOCKString, BLOCKNEXTString)){
                DMGString += Linebreak();
            }
            if(Random && !HitAll){
                DMGString += this.cardStrings.EXTENDED_DESCRIPTION[5];
            }else{
                DMGString += this.cardStrings.EXTENDED_DESCRIPTION[6];
            }
            DMGString += this.cardStrings.EXTENDED_DESCRIPTION[7];
            if(HitAll){
                DMGString += this.cardStrings.EXTENDED_DESCRIPTION[4];
            }
            if(Hitcount > 1){
                DMGString += (Hitcount + this.cardStrings.EXTENDED_DESCRIPTION[2]);
            }
            DMGString += this.cardStrings.EXTENDED_DESCRIPTION[3];
        }

        if(magicNumber > 0){
            if(Vigor){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString)){
                    VIGORString += Linebreak();
                }
                VIGORString += this.cardStrings.EXTENDED_DESCRIPTION[15];
            }
            if(Thorns){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString)){
                    THORNString += Linebreak();
                }
                THORNString += this.cardStrings.EXTENDED_DESCRIPTION[16];
            }
            if(Vuln){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString)){
                    VULNString += Linebreak();
                }
                if(Random && !VulnAll){
                    VULNString += this.cardStrings.EXTENDED_DESCRIPTION[10];
                }else{
                    VULNString += this.cardStrings.EXTENDED_DESCRIPTION[11];
                }
                VULNString += this.cardStrings.EXTENDED_DESCRIPTION[12];
                VULNString += this.cardStrings.EXTENDED_DESCRIPTION[13];
                if(VulnAll){
                    VULNString += this.cardStrings.EXTENDED_DESCRIPTION[4];
                }
                VULNString += this.cardStrings.EXTENDED_DESCRIPTION[3];
            }
            if(Weak){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString, VULNString)){
                    WEAKString += Linebreak();
                }
                if(Random && !WeakAll){
                    WEAKString += this.cardStrings.EXTENDED_DESCRIPTION[10];
                }else{
                    WEAKString += this.cardStrings.EXTENDED_DESCRIPTION[11];
                }
                WEAKString += this.cardStrings.EXTENDED_DESCRIPTION[12];
                WEAKString += this.cardStrings.EXTENDED_DESCRIPTION[14];
                if(WeakAll){
                    WEAKString += this.cardStrings.EXTENDED_DESCRIPTION[4];
                }
                WEAKString += this.cardStrings.EXTENDED_DESCRIPTION[3];
            }
            if(Draw){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString, VULNString, WEAKString)){
                    DRAWString += Linebreak();
                }
                DRAWString += this.cardStrings.EXTENDED_DESCRIPTION[20];

            }
            if(Energy){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString, VULNString, WEAKString, DRAWString)){
                    ENERGYString += Linebreak();
                }
                ENERGYString += this.cardStrings.EXTENDED_DESCRIPTION[18];
            }
            if(Mill){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString, VULNString, WEAKString, DRAWString, ENERGYString)){
                    MILLString += Linebreak();
                }
                MILLString += this.cardStrings.EXTENDED_DESCRIPTION[21];
            }
            if(Blur){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString, VULNString, WEAKString, DRAWString, ENERGYString, MILLString)){
                    BLURString += Linebreak();
                }
                BLURString += this.cardStrings.EXTENDED_DESCRIPTION[17];
            }
            if(ValorHand){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString, VULNString, WEAKString, DRAWString, ENERGYString, MILLString, BLURString)){
                    ValorHANDString += Linebreak();
                }
                ValorHANDString += this.cardStrings.EXTENDED_DESCRIPTION[22];
            }
            if(ValorDraw){
                if(!Blank(DMGString, BLOCKString, BLOCKNEXTString, VIGORString, THORNString, VULNString, WEAKString, DRAWString, ENERGYString, MILLString, BLURString, ValorHANDString)){
                    ValorDRAWString += Linebreak();
                }
                ValorDRAWString += this.cardStrings.EXTENDED_DESCRIPTION[23];
            }
        }

        this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[0] + BLOCKString + DMGString  + BLOCKNEXTString + VIGORString + THORNString + VULNString + WEAKString + DRAWString + ENERGYString + MILLString + BLURString + ValorHANDString + ValorDRAWString;
        initializeDescription();
    }

    protected boolean Blank(String ... strings) {
        StringBuilder s = new StringBuilder();
        for(String e: strings){
            s.append(e);
        }
        return s.toString().equals("");
    }
    protected String Linebreak() {
        return this.cardStrings.EXTENDED_DESCRIPTION[1];
    }
}