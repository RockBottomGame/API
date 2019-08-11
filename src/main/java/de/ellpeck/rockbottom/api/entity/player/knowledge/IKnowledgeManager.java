/*
 * This file ("IKnowledgeManager.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity.player.knowledge;

import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public interface IKnowledgeManager {

    boolean knowsRecipe(PlayerCompendiumRecipe recipe);

    boolean knowsInformation(ResourceName name);

    Information getInformation(ResourceName name);

    <T extends Information> T getInformation(ResourceName name, Class<T> infoClass);

    /**
     * Tries to teach the recipe to the player
     * @param recipe the recipe to teach
     * @param announce Should the player recieve a toast to inform them of their discovery?
     * @return True if the player did not already know the recipe
     */
    boolean teachRecipe(PlayerCompendiumRecipe recipe, boolean announce);

    boolean teachRecipe(PlayerCompendiumRecipe recipe);

    /**
     * Teaches many recipes with a single toast notification
     * @param recipes A list of recipes to be taught
     */
    void teachRecipes(List<PlayerCompendiumRecipe> recipes);

    void teachInformation(Information information, boolean announce);

    void teachInformation(Information information);

    void forgetRecipe(PlayerCompendiumRecipe recipe, boolean announce);

    void forgetRecipe(PlayerCompendiumRecipe recipe);

    void forgetInformation(ResourceName name, boolean announce);

    void forgetInformation(ResourceName name);
}
