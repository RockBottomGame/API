package de.ellpeck.rockbottom.api.construction.compendium;

import com.google.common.collect.ImmutableList;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ConstructComponent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.entity.IToolStation;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Collections;
import java.util.List;

public class SmithingRecipe extends PlayerCompendiumRecipe implements IToolRecipe {

    public static final ResourceName ID = ResourceName.intern("smithing");

    private List<IUseInfo> inputs;
    private List<ItemInstance> outputs;

    private ConstructionTool tool;
    private List<ConstructionTool> toolList;

    public SmithingRecipe(ResourceName name, List<IUseInfo> inputs, List<ItemInstance> outputs, boolean isKnowledge, float skillReward, int usage) {
        super(name, isKnowledge, skillReward);
        this.inputs = inputs;
        this.outputs = outputs;
        this.tool = new ConstructionTool(ToolProperty.HAMMER, usage);
        this.toolList = ImmutableList.of(this.tool);
    }

    private boolean hasTools(TileEntity machine) {
        if (machine == null)
            return false;
        IToolStation station = (IToolStation) machine;
        return station.getTool(this.tool.type) != null;
    }

    @Override
    public ConstructComponent getConstructButton(Gui gui, AbstractPlayerEntity player, TileEntity machine, boolean canConstruct) {
        if (machine instanceof IToolStation) {
            return new ConstructComponent(gui, this, ((IToolStation) machine).getMissingTools(Collections.singletonList(this.tool)), canConstruct, () -> {
                if (this.hasTools(machine)) {
                    this.playerConstruct(player, machine, 1);
                    return true;
                }
                return false;
            });
        } else {
            return new ConstructComponent(gui, this, Collections.singletonList(this.tool), canConstruct, () -> false);
        }
    }

    @Override
    public List<ConstructionTool> getRequiredTools() {
        return this.toolList;
    }

    public SmithingRecipe register() {
        Registries.SMITHING_RECIPES.register(this.getName(), this);
        return this;
    }

    @Override
    public List<IUseInfo> getInputs() {
        return this.inputs;
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return this.outputs;
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
