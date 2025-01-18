package pathmaker.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pathmaker.character.PathmakerCharacter;

import java.util.ArrayList;

import static pathmaker.PathmakerMod.makeID;
import static pathmaker.util.CustomTags.Oasis;

public class ColorfulStones extends BaseRelic {
    private static final String NAME = ColorfulStones.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.SOLID;
    public ColorfulStones() {
        super(ID, NAME, PathmakerCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        if(!AbstractDungeon.player.hand.isEmpty()){
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c.hasTag(Oasis)){
                    c.retain = true;
                }
            }
            ///ArrayList<AbstractCard> tmp = new ArrayList<>();
            ///for(AbstractCard c: AbstractDungeon.player.hand.group){
            ///    if(c.hasTag(Oasis) && !c.selfRetain && !c.retain){
            ///        tmp.add(c);
            ///    }
            ///}
///
            ///if(!tmp.isEmpty()){
            ///    int i = AbstractDungeon.miscRng.random(0, tmp.size()-1);
            ///    tmp.get(i).retain = true;
            ///}
        }
        super.onPlayerEndTurn();
    }
}
