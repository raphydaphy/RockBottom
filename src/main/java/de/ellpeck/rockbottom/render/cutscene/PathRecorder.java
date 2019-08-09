package de.ellpeck.rockbottom.render.cutscene;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.gui.Gui;

public class PathRecorder extends CutsceneCameraObject {
    private float velocityX;
    private float velocityY;
    private float velocityScale;
    private boolean recording = false;
    private RecordedPath.Builder builder = new RecordedPath.Builder();

    public PathRecorder(float x, float y, float scale) {
        super (x, y, scale);
    }

    @Override
    public void update(int ticks) {
        Gui gui = RockBottomAPI.getGame().getGuiManager().getGui();
        if (gui != null && gui.doesPauseGame()) {
            return;
        }
        if (recording) {
            builder.with((float) x, (float) y, scale);
        }

        lastTickX = x;
        lastTickY = y;
        lastTickScale = scale;

        x += velocityX;
        y += velocityY;
        scale += velocityScale;

        if (scale < 5) {
            scale = 5;
        } else if (scale > 250) {
            scale = 250;
        }

        System.out.println(scale);

        velocityX *= 0.8f;
        velocityY *= 0.8f;
        velocityScale *= 0.8f;

        if (scale != lastTickScale) {
            RockBottomAPI.getGame().getRenderer().recalculateWorldScale();
        }
    }

    public void addVelocity(float x, float y, float scale) {
        velocityX += x;
        velocityY += y;
        velocityScale += scale;
    }

    public void startRecording() {
        if (recording) {
            RockBottomAPI.logger().warning("Tried to start a path recording that had already begun!");
            return;
        }
        this.recording = true;
    }

    public RecordedPath saveRecording() {
        if (!recording) {
            RockBottomAPI.logger().warning("Tried to stop a path recording that wasn't active!");
            return null;
        }
        this.recording = false;
        return builder.build();
    }

    public boolean isRecording() {
        return recording;
    }
}
