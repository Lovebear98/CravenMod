package craven.cards;

import craven.patches.interfaces.SecondsInterface;
import craven.util.CardStats;

public abstract class AbstractSecondsCard extends AbstractHungryCard implements SecondsInterface {

    private static final int BaseSecondsCount = 1;
    protected int SecondsUsed = 0;
    protected boolean SecondsUpgraded = false;

    public AbstractSecondsCard(String ID, CardStats info) {
        super(ID, info);
    }



    @Override
    public int BaseSecondsCount() {
        return BaseSecondsCount;
    }

    @Override
    public int SecondsCount() {
        return Math.max(BaseSecondsCount - SecondsUsed, 0);
    }

    @Override
    public void upgrade() {
        SecondsUpgraded = true;
        super.upgrade();
    }

    @Override
    public boolean SecondsUpgraded() {
        return SecondsUpgraded;
    }

    @Override
    public void OnSecondsTrigger() {
        SecondsUsed += 1;
    }
}
