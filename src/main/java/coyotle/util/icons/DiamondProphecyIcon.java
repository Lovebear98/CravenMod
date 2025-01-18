package coyotle.util.icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import coyotle.util.TextureLoader;

import static coyotle.CoyotleMod.makeID;

public class DiamondProphecyIcon extends AbstractCustomIcon {
    public static final String ID = makeID("DP");
    private static DiamondProphecyIcon singleton;

    public DiamondProphecyIcon() {
        super(ID, TextureLoader.getTexture("coyotle/images/icons/DiamondProphIcon.png"));
    }

    public static DiamondProphecyIcon get()
    {
        if (singleton == null) {
            singleton = new DiamondProphecyIcon();
        }
        return singleton;
    }
}