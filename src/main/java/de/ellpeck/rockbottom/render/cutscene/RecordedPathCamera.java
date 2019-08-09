package de.ellpeck.rockbottom.render.cutscene;

import de.ellpeck.rockbottom.api.RockBottomAPI;

public class RecordedPathCamera extends CutsceneCameraObject {
    private final RecordedPath path;

    public RecordedPathCamera(RecordedPath path) {
        super(path.getX(0), path.getY(0), path.getScale(0));
        this.path = path;
    }

    @Override
    public void update(int ticks) {
        lastTickX = x;
        lastTickY = y;
        lastTickScale = scale;

        x = path.getX(ticks);
        y = path.getY(ticks);
        scale = path.getScale(ticks);

        if (scale != lastTickScale) {
            RockBottomAPI.getGame().getRenderer().recalculateWorldScale();
        }
    }
}
