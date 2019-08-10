package de.ellpeck.rockbottom.inventory;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.compendium.construction.ConstructionRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.toast.IToast;
import de.ellpeck.rockbottom.api.toast.ToastItem;
import de.ellpeck.rockbottom.construction.ConstructionRegistry;
import de.ellpeck.rockbottom.construction.criteria.CriteriaPickupItem;
import de.ellpeck.rockbottom.net.packet.toclient.PacketKnowledge;
import de.ellpeck.rockbottom.net.packet.toclient.PacketRecipesToast;
import de.ellpeck.rockbottom.world.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class InventoryPlayer extends Inventory {

    public int selectedSlot;

    public InventoryPlayer(EntityPlayer player) {
        super(32);

        if (!player.world.isClient()) {
            this.addChangeCallback((inv, slot) -> {
                if (ConstructionRegistry.chest != null && !ConstructionRegistry.chest.isKnown(player)) {
                    int fullness = 0;
                    for (ItemInstance instance : inv) {
                        if (instance != null) {
                            fullness++;

                            if (fullness >= inv.getSlotAmount() / 2) {
                                player.getKnowledge().teachRecipe(ConstructionRegistry.chest);
                                break;
                            }
                        }
                    }
                }
            });

            this.addChangeCallback((inv, slot) -> {
                ItemInstance instance = inv.get(slot);
                if (instance == null) return;
                List<ICompendiumRecipe> allRecipes = CriteriaPickupItem.getRecipesFor(instance.getItem());
                if (allRecipes == null) allRecipes = new ArrayList<>();
                List<String> names = RockBottomAPI.getResourceRegistry().getNames(instance);
                for (String name : names) {
                    List<ICompendiumRecipe> resRecipes = CriteriaPickupItem.getRecipesFor(name);
                    if (resRecipes != null) allRecipes.addAll(resRecipes);
                }
                player.getKnowledge().teachRecipes(allRecipes);
            });
        }
    }

    @Override
    public void save(DataSet set) {
        super.save(set);

        set.addInt("selected_slot", this.selectedSlot);
    }

    @Override
    public void load(DataSet set) {
        super.load(set);

        this.selectedSlot = set.getInt("selected_slot");
    }
}
