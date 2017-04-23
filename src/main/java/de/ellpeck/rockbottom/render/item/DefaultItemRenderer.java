package de.ellpeck.rockbottom.render.item;

import de.ellpeck.rockbottom.RockBottom;
import de.ellpeck.rockbottom.assets.AssetManager;
import de.ellpeck.rockbottom.item.Item;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class DefaultItemRenderer implements IItemRenderer{

    private final String texture;

    public DefaultItemRenderer(String texture){
        this.texture = "items."+texture;
    }

    @Override
    public void render(RockBottom game, AssetManager manager, Graphics g, Item item, float x, float y, float scale, Color filter){
        manager.getImage(this.texture).draw(x, y, 1F*scale, 1F*scale, filter);
    }
}