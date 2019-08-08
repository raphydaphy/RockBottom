package de.ellpeck.rockbottom.render.cutscene;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.gui.Gui;

public class Cutscene {
    private int duration;

    private int ticks = 0;
    private CutsceneCamera camera = new CutsceneCamera(35,0,100);

    public Cutscene(int duration) {
        this.duration = duration;
    }

    public CutsceneCamera getCamera() {
        return camera;
    }

    public void update() {
        if (finished()) {
            return;
        }

        Gui gui = RockBottomAPI.getGame().getGuiManager().getGui();
        if (gui == null || !gui.doesPauseGame()) {
            camera.update();

            ticks++;
        }
    }

    public boolean finished() {
        return ticks >= duration;
    }
}
