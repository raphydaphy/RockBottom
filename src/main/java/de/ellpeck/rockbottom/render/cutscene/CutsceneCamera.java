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

        x += 0.01;
        y += 0.001;
        scale -= 0.01f;

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
        return (float) Util.lerp(this.lastTickScale, this.scale, RockBottomAPI.getGame().getTickDelta());
    }
}
