package coyotle.util.icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import coyotle.util.TextureLoader;

import static coyotle.CoyotleMod.makeID;

public class BloodProphecyIcon extends AbstractCustomIcon {
    public static final String ID = makeID("BP");
    private static BloodProphecyIcon singleton;

    public BloodProphecyIcon() {
        super(ID, TextureLoader.getTexture("coyotle/images/icons/BloodProphIcon.png"));
    }

    public static BloodProphecyIcon get()
    {
        if (singleton == null) {
            singleton = new BloodProphecyIcon();
        }
        return singleton;
    }
}