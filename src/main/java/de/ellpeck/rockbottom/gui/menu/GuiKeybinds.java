package de.ellpeck.rockbottom.gui.menu;

import de.ellpeck.rockbottom.init.AbstractGame;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.gui.component.ComponentKeybind;

public class GuiKeybinds extends Gui{

    public int activeKeybind = -1;

    public GuiKeybinds(Gui parent){
        super(304, 150, parent);
    }

    @Override
    public void initGui(IGameInstance game){
        super.initGui(game);

        int x = 0;
        int y = 0;
        for(int i = 0; i < game.getSettings().keybinds.size(); i++){
            Settings.Keybind bind = game.getSettings().keybinds.get(i);
            this.components.add(new ComponentKeybind(this, i, this.guiLeft+x, this.guiTop+y, bind));

            y += 20;
            if(i == 5){
                x += 154;
                y = 0;
            }
        }

        this.components.add(new ComponentButton(this, -1, this.guiLeft+this.sizeX/2-40, this.guiTop+this.sizeY-16, 80, 16, game.getAssetManager().localize(AbstractGame.internalRes("button.back"))));
    }

    @Override
    public boolean onButtonActivated(IGameInstance game, int button){
        if(button == -1){
            game.getGuiManager().openGui(this.parent);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(!super.onMouseAction(game, button, x, y)){
            this.activeKeybind = -1;
        }
        return true;
    }
}
