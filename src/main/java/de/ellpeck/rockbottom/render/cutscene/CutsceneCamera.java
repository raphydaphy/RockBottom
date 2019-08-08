package de.ellpeck.rockbottom.render.cutscene;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Util;

public class CutsceneCamera {
    private double x;
    private double y;
    private float scale;
    private double lastTickX;
    private double lastTickY;
    private float lastTickScale;

    public CutsceneCamera(double x, double y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;

        this.lastTickX = x;
        this.lastTickY = y;
        this.lastTickScale = scale;
    }

    public void update() {
        lastTickX = x;
        lastTickY = y;
        lastTickScale = scale;

        x += 0.3;
        y += 0.01;
        scale -= 0.25f;
        if (scale < 0) {
            scale = 0;
        }

        if (scale != lastTickScale) {
            RockBottomAPI.getGame().getRenderer().recalculateWorldScale();
        }
    }

    public double getLerpedX() {
        return Util.lerp(this.lastTickX, this.x, RockBottomAPI.getGame().getTickDelta());
    }

    public double getLerpedY() {
        return Util.lerp(this.lastTickY, this.y, RockBottomAPI.getGame().getTickDelta());
    }

    public float getLerpedScale() {
        return (float) Util.lerp(this.lastTickScale / 100f, this.scale / 100f, RockBottomAPI.getGame().getTickDelta());
    }
}
