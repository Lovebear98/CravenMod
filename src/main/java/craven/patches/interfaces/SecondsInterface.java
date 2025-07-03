package craven.patches.interfaces;

public interface SecondsInterface {
    abstract int BaseSecondsCount();
    abstract int SecondsCount();
    abstract void OnSecondsTrigger();
    default boolean SecondsUpgraded(){
        return false;
    }
}
