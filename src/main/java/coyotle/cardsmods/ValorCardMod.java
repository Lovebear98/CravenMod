package coyotle.cardsmods;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.cards.generated.Valor;

import java.util.ArrayList;
import java.util.List;

import static coyotle.CoyotleMod.makeID;
import static coyotle.util.otherutil.ImageManager.ValorCardIcon;
import static coyotle.util.otherutil.ValorManager.*;

public class ValorCardMod extends AbstractCardModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(ValorCardMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));


    public ValorCardMod(){
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
        if(card instanceof Valor){
            return damage + (ValorBreakpointsReached() * ValorDamageGrowth());
        }
        return super.modifyBaseDamage(damage, type, card, target);
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if(card instanceof Valor){
            if(ValorsPlayed >= ValorDrawThreshold){
                return magic + ValorDraw(card.upgraded);
            }
        }
        return super.modifyBaseMagic(magic, card);
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if(card instanceof Valor){
            if(ValorsPlayed >= ValorBlockThreshold){
                return block + ValorBlock(card.upgraded);
            }
        }
        return super.modifyBaseBlock(block, card);
    }


    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        super.onRender(card, sb);
        RenderIcon(card, sb);
    }
    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        super.onSingleCardViewRender(card, sb);
        RenderIcon(card, sb);
    }

    private TooltipInfo tip;
    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        ArrayList<TooltipInfo> list = new ArrayList<>();
        if(tip == null){
            tip = new TooltipInfo(BaseMod.getKeywordTitle(makeID("valor")), BaseMod.getKeywordDescription(makeID("valor")));
        }
        list.add(tip);
        return list;
    }

    private void RenderIcon(AbstractCard card, SpriteBatch sb) {
        if(card instanceof Valor){
            ExtraIcons.icon(ValorCardIcon).text(String.valueOf(ValorsPlayed)).render(card);
        }
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ValorCardMod();
    }
}
