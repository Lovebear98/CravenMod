package coyotle.util.icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import coyotle.util.TextureLoader;

import static coyotle.CoyotleMod.makeID;

public class SapphireProphecyIcon extends AbstractCustomIcon {
    public static final String ID = makeID("SP");
    private static SapphireProphecyIcon singleton;

    public SapphireProphecyIcon() {
        super(ID, TextureLoader.getTexture("coyotle/images/icons/SaphProphIcon.png"));
    }

    public static SapphireProphecyIcon get()
    {
        if (singleton == null) {
            singleton = new SapphireProphecyIcon();
        }
        return singleton;
    }
}