package de.ellpeck.rockbottom.render.cutscene;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.init.RockBottom;

public class CutsceneManager {
    private static CutsceneManager INSTANCE = new CutsceneManager();

    private Cutscene cutscene;

    public static CutsceneManager getInstance() {
        return INSTANCE;
    }

    public void update() {
        if (cutscene == null) {
            return;
        } else if (cutscene.finished()) {
            cutscene = null;
            return;
        }
        cutscene.update();
    }

    /**
     * Start a new cutscene
     * @param cutscene The cutscene to play
     * @return True if the cutscene started successfully
     */
    public boolean startCutscene(Cutscene cutscene) {
        if (isPlaying()) {
            RockBottomAPI.logger().warning("Tried to start a cutscene when one was already playing");
            return false;
        }
        RockBottomAPI.logger().info("Started Cutscene!");
        this.cutscene = cutscene;
        return true;
    }

    public Cutscene getActiveCutscene() {
        return cutscene;
    }

    public boolean isPlaying() {
        return RockBottomAPI.getGame() != null && RockBottomAPI.getGame().getPlayer() != null && cutscene != null && !cutscene.finished();
    }
}
