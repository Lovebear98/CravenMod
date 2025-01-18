package coyotle.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.AbstractShardCard;

import java.util.List;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.ImageManager.*;

public class ShardCardMod extends AbstractCardModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(ShardCardMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));
    private int Damage = 0;
    private int Block = 0;
    private int Magic = 0;


    public ShardCardMod(){
    }
    ///Name our cardmod
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    ///It's innateo n whatever it's applied to, because it's applied on EVERYTHING
    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }
    ///When applied, make sure we don't double up.
    @Override
    public boolean shouldApply(AbstractCard targetCard) {
        ///We only add a mod if it doesn't already have the mod
        List<AbstractCardModifier> sameMod = CardModifierManager.getModifiers(targetCard, identifier(null));
        return sameMod.isEmpty();
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(Damage > 0){
            float f = damage + Damage;
            if(card instanceof AbstractShardCard){
                if(((AbstractShardCard) card).XCost){
                    f = f * 0.5f;
                }
            }
            return Math.max(1, f);
        }

        return super.modifyBaseDamage(damage, type, card, target);
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if(Block > 0){
            float f = block + Block;
            if(card instanceof AbstractShardCard){
                if(((AbstractShardCard) card).XCost){
                    f = f * 0.5f;
                }
            }
            return Math.max(1, f);
        }
        return super.modifyBaseBlock(block, card);
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if(Magic > 0){
            float f = magic + Magic;
            if(card instanceof AbstractShardCard){
                if(((AbstractShardCard) card).XCost){
                    f = f * 0.5f;
                }
            }
            return Math.max(1, f);
        }
        return super.modifyBaseMagic(magic, card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        super.onSingleCardViewRender(card, sb);
        RenderIcon(card, sb);
    }

    private void RenderIcon(AbstractCard card, SpriteBatch sb) {
        if(card instanceof AbstractShardCard){
            ExtraIcons.icon(BloodCardIcon).text(String.valueOf(((AbstractShardCard) card).BloodNum)).render(card);
            ExtraIcons.icon(DiamondCardIcon).text(String.valueOf(((AbstractShardCard) card).DiamondNum)).render(card);
            ExtraIcons.icon(RubyCardIcon).text(String.valueOf(((AbstractShardCard) card).RubyNum)).render(card);
            ExtraIcons.icon(SapphireCardIcon).text(String.valueOf(((AbstractShardCard) card).SapphireNum)).render(card);
            ExtraIcons.icon(WildCardIcon).text(String.valueOf(((AbstractShardCard) card).WildNum)).render(card);
        }
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        super.onApplyPowers(card);
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        super.onRender(card, sb);
        RenderIcon(card, sb);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ShardCardMod();
    }

    public void statFix(int rubyNum, int diamondNum, int sapphireNum) {
        this.Damage = rubyNum;
        this.Block = diamondNum;
        this.Magic = sapphireNum;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return super.modifyDescription(rawDescription, card);
    }
}
