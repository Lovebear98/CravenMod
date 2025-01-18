package pathmaker.patches.interfaces;

public interface EnemyGainBlockPower {

    default float onThisEnemyBlock(int block){
        ///Start with the total block coming ni

        ///And return the modified block if we modify it
        return block;
    }
}
