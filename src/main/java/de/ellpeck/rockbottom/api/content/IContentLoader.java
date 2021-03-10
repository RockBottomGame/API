/*
 * This file ("IContentLoader.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.content;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.compendium.ICriterion;
import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.content.pack.ContentPack;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public interface IContentLoader<T extends IContent> {

    default void register() {
        Registries.CONTENT_LOADER_REGISTRY.register(this.getContentIdentifier(), this);
    }

    ResourceName getContentIdentifier();

    void loadContent(IGameInstance game, ResourceName resourceName, String path, JsonElement element, String elementName, IMod loadingMod, ContentPack pack) throws Exception;

    void disableContent(IGameInstance game, ResourceName resourceName);

    default JsonObject getRecipeObject(IGameInstance game, String path) throws IOException {
        InputStreamReader reader = new InputStreamReader(game.getClassLoader().getResourceAsStream(path), Charsets.UTF_8);
        JsonElement recipeElement = JsonParser.parseReader(reader);
        reader.close();
        return recipeElement.getAsJsonObject();
    }

    default IUseInfo readUseInfo(JsonObject obj) throws Exception {
        String name = obj.get("name").getAsString();
        int amount = obj.has("amount") ? obj.get("amount").getAsInt() : 1;

        if (Util.isResourceName(name)) {
            int meta = obj.has("meta") ? obj.get("meta").getAsInt() : 0;
            ItemInstance instance = new ItemInstance(Registries.ITEM_REGISTRY.get(new ResourceName(name)), amount, meta);
            if (obj.has("data")) {
                ModBasedDataSet set = instance.getOrCreateAdditionalData();
                RockBottomAPI.getApiHandler().readDataSet(obj.get("data").getAsJsonObject(), set);
            }
            return new ItemUseInfo(instance);
        } else return new ResUseInfo(name, amount);
    }

    default List<IUseInfo> readUseInfos(JsonArray array) throws Exception {
        List<IUseInfo> infos = new ArrayList<>();
        for (JsonElement input : array) {
            infos.add(readUseInfo(input.getAsJsonObject()));
        }
        return infos;
    }

    default ItemInstance readItemInstance(JsonObject obj) throws Exception {
        Item item = Registries.ITEM_REGISTRY.get(new ResourceName(obj.get("name").getAsString()));
        int amount = obj.has("amount") ? obj.get("amount").getAsInt() : 1;
        int meta = obj.has("meta") ? obj.get("meta").getAsInt() : 0;
        ItemInstance instance = new ItemInstance(item, amount, meta);
        if (obj.has("data")) {
            ModBasedDataSet set = instance.getOrCreateAdditionalData();
            RockBottomAPI.getApiHandler().readDataSet(obj.get("data").getAsJsonObject(), set);
        }
        return instance;
    }

    default List<ItemInstance> readItemInstances(JsonArray array) throws Exception {
        List<ItemInstance> instances = new ArrayList<>();
        for (JsonElement output : array) {
            instances.add(readItemInstance(output.getAsJsonObject()));
        }
        return instances;
    }

    default void processCriteria(PlayerCompendiumRecipe recipe, JsonArray ca) {
        if (recipe.isKnowledge()) {
            for (JsonElement ce : ca) {
                JsonObject criteria = ce.getAsJsonObject();
                String name = criteria.get("name").getAsString();
                ICriterion icriteria = Registries.CRITERIA_REGISTRY.get(new ResourceName(name));
                if (icriteria == null) {
                    throw new IllegalArgumentException("Invalid criteria " + name + " for recipe " + recipe.getName());
                }
                JsonObject params = criteria.getAsJsonObject("params");
                if (!icriteria.deserialize(recipe, params)) {
                    RockBottomAPI.logger().warning("Failed to deserialize criteria " + name + " for recipe " + recipe.getName());
                }
            }
        } else {
            RockBottomAPI.logger().warning("Tried to register criteria for the recipe " + recipe.getName() + " which doesn't use knowledge!");
        }
    }

    default boolean dealWithSpecialCases(IGameInstance game, String resourceName, String path, JsonElement element, String elementName, IMod loadingMod, ContentPack pack) {
        return false;
    }

    default void finalize(IGameInstance game) {

    }
}
