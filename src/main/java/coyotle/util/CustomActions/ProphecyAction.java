package coyotle.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import coyotle.cards.AbstractShardCard;
import coyotle.ui.ProphecyPanel;
import coyotle.util.CustomActions.CustomGameEffects.vfx.ProphecySmokeEffect;

import java.util.ArrayList;
import java.util.Arrays;

import static coyotle.util.CustomActions.CustomGameEffects.vfx.ProphecySmokeEffect.DrawProphecyFX;
import static coyotle.util.otherutil.ConfigManager.EnableEarCandy;
import static coyotle.util.otherutil.ConfigManager.EnableEyeCandy;
import static coyotle.util.otherutil.ShardManager.*;
import static coyotle.util.otherutil.SoundManager.PROPHECYSOUNDKEY;
import static coyotle.util.otherutil.SoundManager.PlaySound;
import static coyotle.util.otherutil.variables.Variables.p;

public class ProphecyAction extends AbstractGameAction {
    private final float y;
    private final float x;
    private ArrayList<String> KeyList;


    public ProphecyAction(AbstractCard c, String ... keys){
        this(c, 1, keys);
    }
    public ProphecyAction(AbstractCard c, int num, String ... keys) {
        KeyList = new ArrayList<>();
        KeyList.addAll(Arrays.asList(keys));
        this.amount = num;
        if(c != null){
            this.x = c.current_x;
            this.y = c.current_y;
        }else{
            this.x = p().hb.cX;
            this.y = p().hb.cY;
        }
    }

    public ProphecyAction(float MyX, float MyY, int num, ArrayList<String> list){
        this.KeyList = list;
        this.amount = num;
        this.x = MyX;
        this.y = MyY;
    }

    public ProphecyAction(float MyX, float MyY, ArrayList<String> list){
        this(MyX, MyY, 1, list);
    }

    public ProphecyAction(float MyX, float MyY, int num, String ... keys){
        KeyList = new ArrayList<>();
        KeyList.addAll(Arrays.asList(keys));

        this.amount = num;

        this.x = MyX;
        this.y = MyY;
    }

    public ProphecyAction(int num, String...keys){
        this(p().hb.cX, p().hb.cY, num, keys);
    }

    public ProphecyAction(float MyX, float MyY, String ...keys){
        this(MyX, MyY, 1, keys);
    }


    @Override
    public void update() {
        ///If we have no valid cards, reshuffle then try again
        if(AbstractDungeon.player.drawPile.isEmpty()){
            ///Adding a shard to mark makes The Dream too accessible,
            ///Reshuffling the draw pile makes discard pile synergies too clunky
            ///We need more cards to test this better

            ///AbstractDungeon.actionManager.addToTop(new ProphecyAction(x, y, amount, KeyList));
            ///AbstractDungeon.actionManager.addToTop(new CreateShardAction());
        }else{
            if(DrawProphecyFX){
                if(EnableEarCandy){
                    PlaySound(PROPHECYSOUNDKEY);
                }
                if(EnableEyeCandy){
                    AbstractDungeon.topLevelEffectsQueue.add(new ProphecySmokeEffect(x, y));
                }
            }

            for(AbstractCard c: p().drawPile.group){
                if(c instanceof AbstractShardCard){
                    if(KeyList.contains(HitcountKey)){
                        ((AbstractShardCard) c).Hitcount += amount;
                    }
                    if(KeyList.contains(BlockCountKey)){
                        ((AbstractShardCard) c).BlockCount += amount;
                    }



                    if(KeyList.contains(BloodKey)){
                        ((AbstractShardCard) c).AddBloodShard(amount);
                    }
                    if(KeyList.contains(DiamondKey)){
                        ((AbstractShardCard) c).AddDiamondShard(amount);
                    }
                    if(KeyList.contains(RubyKey)){
                        ((AbstractShardCard) c).AddRubyShard(amount);
                    }
                    if(KeyList.contains(SapphireKey)){
                        ((AbstractShardCard) c).AddSapphireShard(amount);
                    }
                    if(KeyList.contains(WildKey)){
                        ((AbstractShardCard) c).AddWildShard(amount);
                    }

                    AbstractShardCard c2 = (AbstractShardCard) c;
                    int i = UniqueEffects(c2);
                    int i2 = UniqueEffectsCap(c2);
                    if(i < i2){
                        if(KeyList.contains(XCostKey)){
                            ((AbstractShardCard) c).BecomeXCost();
                            i += 1;
                        }
                        if(KeyList.contains(ContinueKey) && i < i2){
                            ((AbstractShardCard) c).Continue = true;
                            i += 1;
                        }
                        if(KeyList.contains(HitAllKey) && i < i2){
                            ((AbstractShardCard) c).HitAll = true;
                            i += 1;
                        }
                        if(KeyList.contains(RandomKey) && i < i2){
                            ((AbstractShardCard) c).Random = true;
                            i += 1;
                        }
                        if(KeyList.contains(VulnKey) && i < i2){
                            ((AbstractShardCard) c).Vuln = true;
                            i += 1;
                        }
                        if(KeyList.contains(VulnAllKey) && i < i2){
                            ((AbstractShardCard) c).VulnAll = true;
                            i += 1;
                        }
                        if(KeyList.contains(VigorKey) && i < i2){
                            ((AbstractShardCard) c).Vigor = true;
                            i += 1;
                        }
                        if(KeyList.contains(BlockNextKey) && i < i2){
                            ((AbstractShardCard) c).BlockNext = true;
                            i += 1;
                        }
                        if(KeyList.contains(ThornsKey) && i < i2){
                            ((AbstractShardCard) c).Thorns = true;
                            i += 1;
                        }
                        if(KeyList.contains(WeakKey) && i < i2){
                            ((AbstractShardCard) c).Weak = true;
                            i += 1;
                        }
                        if(KeyList.contains(WeakAllKey) && i < i2){
                            ((AbstractShardCard) c).WeakAll = true;
                            i += 1;
                        }
                        if(KeyList.contains(BlurKey) && i < i2){
                            ((AbstractShardCard) c).Blur = true;
                            i += 1;
                        }
                        if(KeyList.contains(DrawKey) && i < i2){
                            ((AbstractShardCard) c).Draw = true;
                            i += 1;
                        }
                        if(KeyList.contains(EnergyKey) && i < i2){
                            ((AbstractShardCard) c).Energy = true;
                            i += 1;
                        }
                        if(KeyList.contains(MillKey) && i < i2){
                            ((AbstractShardCard) c).Mill = true;
                            i += 1;
                        }
                        if(KeyList.contains(ValorDrawKey) && i < i2){
                            ((AbstractShardCard) c).ValorDraw = true;
                            i += 1;
                        }
                        if(KeyList.contains(ValorMillKey) && i < i2){
                            ((AbstractShardCard) c).ValorHand = true;
                            i += 1;
                        }

                        if(c2.NoEffects && i > 0){
                            c2.NoEffects = false;
                        }
                    }
                    c.applyPowers();
                    c.initializeDescription();
                }
            }
        }

        ProphecyPanel.FixPreviewInstantly = true;
        isDone = true;
    }
}
