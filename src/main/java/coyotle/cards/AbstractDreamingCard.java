package coyotle.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import coyotle.patches.interfaces.DreamInterface;
import coyotle.util.CardStats;

import static coyotle.util.CustomTags.Dreaming;
import static coyotle.util.otherutil.DreamManager.inDream;
import static coyotle.util.otherutil.Wiz.FlashCard;

public abstract class AbstractDreamingCard extends AbstractProphecyCard implements DreamInterface {
    protected final CardType startType;
    protected CardType dreamType;
    protected final CardTarget startTarget;
    protected CardTarget dreamTarget = CardTarget.ALL_ENEMY;

    public AbstractDreamingCard(String ID, CardStats info) {
        super(ID, info);
        this.startType = this.type;
        this.dreamType = this.type;
        this.startTarget = this.target;
        this.dreamTarget = this.target;
        tags.add(Dreaming);
    }

    protected void setDreamType(CardType t){
        this.dreamType = t;
    }
    protected void setDreamTarget(CardTarget t){
        this.dreamTarget = t;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(inDream){
            dreamUse(p, m);
        }else{
            standardUse(p, m);
        }
    }

    public boolean DreamMode = false;
    @Override
    public void onEnterDream() {
        DreamMode = true;
        DreamInterface.super.onEnterDream();


        FlashCard(this, GlowColor());
        this.type = this.dreamType;
        this.target = dreamTarget;
        if(upgraded){
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        }else{
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
        if(this.cardsToPreview instanceof AbstractDreamingCard){
            ((AbstractDreamingCard)cardsToPreview).onExitDream();
            cardsToPreview.initializeDescription();
        }
    }



    @Override
    public void onExitDream() {
        DreamMode = false;
        DreamInterface.super.onExitDream();

        FlashCard(this, GlowColor());
        this.type = this.startType;
        this.target = startTarget;
        if(upgraded){
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        }else{
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
        if(this.cardsToPreview instanceof AbstractDreamingCard){
            ((AbstractDreamingCard)cardsToPreview).onEnterDream();
            cardsToPreview.initializeDescription();
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if(DreamMode){
            onEnterDream();
        }else{
            onExitDream();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(DreamMode){
            this.glowColor = GlowColor();
        }
    }

    protected abstract void standardUse(AbstractPlayer p, AbstractMonster m);
    protected abstract void dreamUse(AbstractPlayer p, AbstractMonster m);
}
