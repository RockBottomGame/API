package de.ellpeck.rockbottom.api.construction.compendium;

import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public interface ICriteria {

    ResourceName getName();

    /**
     * This provides the raw JSON parameters for deserialization
     * @param params The parameters for this criteria
     * @return True if the criteria was successfully deserialized
     */
    boolean deserialize(ICompendiumRecipe recipe, JsonObject params);

    default void register() {
        Registries.CRITERIA_REGISTRY.register(getName(), this);
    }

}
