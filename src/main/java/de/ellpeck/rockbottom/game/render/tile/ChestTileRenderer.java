package de.ellpeck.rockbottom.game.render.tile;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.game.RockBottom;
import de.ellpeck.rockbottom.game.world.tile.entity.TileEntityChest;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ChestTileRenderer extends DefaultTileRenderer{

    private final IResourceName texOpen;

    public ChestTileRenderer(IResourceName name){
        super(name);
        this.texOpen = this.texture.addSuffix(".open");
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, Tile tile, int x, int y, float renderX, float renderY, Color filter){
        IResourceName tex;

        TileEntityChest chest = world.getTileEntity(x, y, TileEntityChest.class);
        if(chest != null && chest.openCount > 0){
            tex = this.texOpen;
        }
        else{
            tex = this.texture;
        }

        manager.getImage(tex).draw(renderX, renderY, 1F, 1F, filter);
    }
}