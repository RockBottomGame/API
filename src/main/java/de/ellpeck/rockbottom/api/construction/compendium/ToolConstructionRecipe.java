package de.ellpeck.rockbottom.api.construction.compendium;

import com.google.common.collect.ImmutableList;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ConstructComponent;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IToolStation;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Collections;
import java.util.List;

public class ToolConstructionRecipe extends ConstructionRecipe implements IToolRecipe {

    protected final List<ConstructionTool> tools;

    public ToolConstructionRecipe(ResourceName name, List<ConstructionTool> tools, List<IUseInfo> inputs, List<ItemInstance> outputs, boolean manualOnly, boolean isKnowledge, float skillReward) {
        super(name, inputs, outputs, manualOnly, isKnowledge, skillReward);
        this.tools = ImmutableList.copyOf(tools);
    }

    @Override
    public void onConstruct(AbstractPlayerEntity player, IInventory inputInventory, IInventory outputInventory, TileEntity machine, List<ItemInstance> ingredients, float skillReward) {
        if (machine instanceof IToolStation) {
            IToolStation toolStation = (IToolStation) machine;
            for (ConstructionTool tool : this.tools) {
                toolStation.damageTool(tool);
            }
        }
    }

    @Override
    public boolean showInConstructionTable() {
        return true;
    }

    @Override
    public ConstructComponent getConstructButton(Gui gui, AbstractPlayerEntity player, TileEntity machine, boolean canConstruct) {
        if (machine instanceof IToolStation) {
            List<ConstructionTool> missingTools = ((IToolStation) machine).getMissingTools(this.tools);
            return new ConstructComponent(gui, this, missingTools, canConstruct, () -> {
                if (missingTools.isEmpty()) {
                    this.playerConstruct(player, machine, 1);
                }
                return true;
            });
        } else {
            return new ConstructComponent(gui, this, this.tools, canConstruct, () -> true);
        }
    }

    @Override
    public List<ConstructionTool> getRequiredTools() {
        return this.tools;
    }

    @Override
    public void fillRecipeInfo(Gui gui, IGameInstance game, IAssetManager manager, List<String> info, ItemInstance currentItem, ConstructComponent component) {
        if (!component.missingTools.isEmpty()) {
            info.add(FormattingCode.RED + "Missing Tools:");
            for (ConstructionTool tool : component.missingTools) {
                info.add(FormattingCode.RED + tool.type.getName().toString());
            }
        }
    }
}
