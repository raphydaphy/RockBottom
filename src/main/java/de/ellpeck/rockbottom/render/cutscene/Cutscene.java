package de.ellpeck.rockbottom.render.cutscene;

public class Cutscene {
    private int duration;

    private int ticks = 0;
    private CutsceneCamera camera = new CutsceneCamera(0,0,30);

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

        camera.update();

        ticks++;
    }

    public boolean finished() {
        return ticks < duration;
    }
}
