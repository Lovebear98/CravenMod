package coyotle.util.icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import coyotle.util.TextureLoader;

import static coyotle.CoyotleMod.makeID;

public class RubyProphecyIcon extends AbstractCustomIcon {
    public static final String ID = makeID("RP");
    private static RubyProphecyIcon singleton;

    public RubyProphecyIcon() {
        super(ID, TextureLoader.getTexture("coyotle/images/icons/RubyProphIcon.png"));
    }

    public static RubyProphecyIcon get()
    {
        if (singleton == null) {
            singleton = new RubyProphecyIcon();
        }
        return singleton;
    }
}