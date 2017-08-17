package de.ellpeck.rockbottom.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Graphics;

import java.util.function.Supplier;

public class ComponentFancyButton extends ComponentButton{

    protected final IResourceName texture;

    public ComponentFancyButton(Gui gui, int x, int y, int sizeX, int sizeY, Supplier<Boolean> supplier, IResourceName texture, String... hover){
        super(gui, x, y, sizeX, sizeY, supplier, null, hover);
        this.texture = texture;
    }

    protected IResourceName getTexture(){
        return this.texture;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g){
        super.render(game, manager, g);

        manager.getTexture(this.getTexture()).draw(this.x, this.y, this.sizeX, this.sizeY);
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("fancy_button");
    }
}
