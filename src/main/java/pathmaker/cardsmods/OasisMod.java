package pathmaker.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import pathmaker.cards.AbstractOasisCard;

import java.util.List;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.otherutil.ImageManager.OasisFX;
import static pathmaker.util.otherutil.ImageManager.OasisNumIcon;

public class OasisMod extends AbstractCardModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(OasisMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));


    public OasisMod(){
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
    public void onRender(AbstractCard card, SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        if (OasisCount(card) != 0) {
            float w = OasisFX.getWidth();
            float h = OasisFX.getHeight();
            float x = card.current_x - w/2f;
            float y = card.current_y - h/2f;

            float tx = card.current_x;
            float ty = card.current_y;

            sb.draw(OasisFX, x, y, w/2f, h/2f, w, h, card.drawScale, card.drawScale,
                    card.angle, 0, 0, (int) w, (int) h, false, false);
            if(OasisCount(card) > 0){
                ExtraIcons.icon(OasisNumIcon).text(String.valueOf(OasisCount(card))).render(card);
            }
        }

        super.onRender(card, sb);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        super.onSingleCardViewRender(card, sb);
        if(OasisCount(card) > 0){
            ExtraIcons.icon(OasisNumIcon).text(String.valueOf(OasisCount(card))).render(card);
        }
    }

    private int OasisCount(AbstractCard card){
        int i = 0;
        if(card instanceof AbstractOasisCard){
            i = ((AbstractOasisCard) card).secondMagic;
        }

        return i;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OasisMod();
    }
}
