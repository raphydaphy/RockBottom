package de.ellpeck.rockbottom.render.cutscene;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.gui.Gui;

public class Cutscene {
    private RecordedPath path;

    private int ticks = 0;
    private RecordedPathCamera camera;

    public Cutscene(RecordedPath path) {
        this.path = path;
        this.camera = new RecordedPathCamera(path);
    }

    public RecordedPathCamera getCamera() {
        return camera;
    }

    public void update() {
        if (finished()) {
            return;
        }

        Gui gui = RockBottomAPI.getGame().getGuiManager().getGui();
        if (gui == null || !gui.doesPauseGame()) {
            ticks++;
            camera.update(ticks);
        }
    }

    public boolean finished() {
        return ticks >= path.getLength();
    }
}
