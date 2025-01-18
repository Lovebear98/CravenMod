package coyotle.util.otherutil;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;

import static coyotle.CoyotleMod.makeID;
import static coyotle.patches.PlayerFields.*;
import static coyotle.util.otherutil.variables.Variables.p;

public class SaveFieldManager {


    public static void SetupSaveFields(){
        /** SPLIT */
        BaseMod.addSaveField(WindsingerEncountersSaveKey, new CustomSavable<Integer>() {
            @Override
            public Integer onSave() {
                ///Save the Fractured HP
                return WindsingerEncounters();
            }

            @Override
            public void onLoad(Integer SavedInt) {
                ///Return what we saved, or default to 0 if we can't find it
                int i = 0;
                if(SavedInt != null){
                    i = SavedInt;
                }
                WindsingerEncounters.set(p(), i);
            }
        });
        /** SPLIT */
        BaseMod.addSaveField(WindsingerNeutralSaveKey, new CustomSavable<Boolean>() {
            @Override
            public Boolean onSave() {
                ///Save the Fractured HP
                return WindsingerIsNeutral();
            }

            @Override
            public void onLoad(Boolean SavedBool) {
                ///Return what we saved, or default to 0 if we can't find it
                 boolean b = false;
                if(SavedBool != null){
                    b = SavedBool;
                }
                WindsingerNeutral.set(p(), b);
            }
        });
        /** SPLIT */
        BaseMod.addSaveField(WindsingerFriendSaveKey, new CustomSavable<Boolean>() {
            @Override
            public Boolean onSave() {
                ///Save the Fractured HP
                return WindsingerIsFriend();
            }

            @Override
            public void onLoad(Boolean SavedBool) {
                ///Return what we saved, or default to 0 if we can't find it
                boolean b = false;
                if(SavedBool != null){
                    b = SavedBool;
                }
                WindsingerFriend.set(p(), b);
            }
        });
        /** SPLIT */
        BaseMod.addSaveField(MysticNextDrawSaveKey, new CustomSavable<Integer>() {
            @Override
            public Integer onSave() {
                ///Save the Fractured HP
                return MysticNextDraw();
            }

            @Override
            public void onLoad(Integer SavedInt) {
                ///Return what we saved, or default to 0 if we can't find it
                int i = 0;
                if(SavedInt != null){
                    i = SavedInt;
                }
                MysticNextDraw.set(p(), i);
            }
        });
        /** SPLIT */
        BaseMod.addSaveField(MysticNextEnergySaveKey, new CustomSavable<Integer>() {
            @Override
            public Integer onSave() {
                ///Save the Fractured HP
                return MysticNextEnergy();
            }

            @Override
            public void onLoad(Integer SavedInt) {
                ///Return what we saved, or default to 0 if we can't find it
                int i = 0;
                if(SavedInt != null){
                    i = SavedInt;
                }
                MysticNextEnergy.set(p(), i);
            }
        });
        /** SPLIT */
        BaseMod.addSaveField(WindsingerNeutralSaveKey, new CustomSavable<Boolean>() {
            @Override
            public Boolean onSave() {
                ///Save the Fractured HP
                return MetMystic.get(p());
            }

            @Override
            public void onLoad(Boolean SavedBool) {
                ///Return what we saved, or default to 0 if we can't find it
                boolean b = false;
                if(SavedBool != null){
                    b = SavedBool;
                }

                if(b){
                    EncounterMystic();
                }
            }
        });
        /** SPLIT */


    }





    public static final String WindsingerEncountersSaveKey = makeID("WindsingerEncounters");
    public static final String WindsingerNeutralSaveKey = makeID("WindsingerNeutral");
    public static final String WindsingerFriendSaveKey = makeID("WindsingerFriend");
    public static final String MetMysticSaveKey = makeID("MetMystic");
    public static final String MysticNextDrawSaveKey = makeID("MysticNextDraw");
    public static final String MysticNextEnergySaveKey = makeID("MysticNextEnergy");
}
