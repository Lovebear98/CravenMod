package coyotle.util.icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import coyotle.util.TextureLoader;

import static coyotle.CoyotleMod.makeID;

public class WildProphecyIcon extends AbstractCustomIcon {
    public static final String ID = makeID("WP");
    private static WildProphecyIcon singleton;

    public WildProphecyIcon() {
        super(ID, TextureLoader.getTexture("coyotle/images/icons/WildProphIcon.png"));
    }

    public static WildProphecyIcon get()
    {
        if (singleton == null) {
            singleton = new WildProphecyIcon();
        }
        return singleton;
    }
}