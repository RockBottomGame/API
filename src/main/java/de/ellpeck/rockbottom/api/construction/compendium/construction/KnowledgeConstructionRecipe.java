/*
 * This file ("KnowledgeConstructionRecipe.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.construction.compendium.construction;

import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class KnowledgeConstructionRecipe extends ConstructionRecipe {

    public KnowledgeConstructionRecipe(ResourceName name, List<ConstructionTool> tools, List<IUseInfo> inputs, List<ItemInstance> outputs, float skillReward) {
        super(name, tools, inputs, outputs, skillReward);
    }

    public KnowledgeConstructionRecipe(ResourceName name, List<ConstructionTool> tools, float skillReward, ItemInstance output, IUseInfo... inputs) {
        super(name, tools, skillReward, output, inputs);
    }

    public KnowledgeConstructionRecipe(float skillReward, List<ConstructionTool> tools, ItemInstance output, IUseInfo... inputs) {
        super(tools, skillReward, output, inputs);
    }

    @Override
    public boolean isKnown(AbstractEntityPlayer player) {
        return !player.world.isStoryMode() || player.getKnowledge().knowsRecipe(this);
    }
}
