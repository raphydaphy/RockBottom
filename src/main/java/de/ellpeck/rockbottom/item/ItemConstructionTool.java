package de.ellpeck.rockbottom.item;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiMessageBox;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentTranslation;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.rockbottom.construction.ConstructionRegistry;
import de.ellpeck.rockbottom.render.cutscene.Cutscene;
import de.ellpeck.rockbottom.render.cutscene.CutsceneManager;
import de.ellpeck.rockbottom.world.entity.EntityItem;

import java.util.List;

public class ItemConstructionTool extends ItemBasic {
    private final int durability;

    public ItemConstructionTool(ResourceName name, int durability) {
        super(name);
        this.durability = durability;
        this.maxAmount = 1;
    }

    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        super.describeItem(manager, instance, desc, isAdvanced);

        int highest = this.getHighestPossibleMeta() + 1;
        desc.add(manager.localize(ResourceName.intern("info.durability"), highest - instance.getMeta(), highest));
    }

    @Override
    public boolean useMetaAsDurability() {
        return true;
    }

    @Override
    public int getHighestPossibleMeta() {
        return this.durability - 1;
    }
}
