package de.ellpeck.rockbottom.api.construction.compendium;

import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

// TODO 0.4 Rename to Criterion (singular form)
public interface ICriterion {

    ResourceName getName();

    /**
     * This provides the raw JSON parameters for deserialization
     *
     * @param recipe The recipe for the criteria
     * @param params The parameters for this criteria
     * @return True if the criteria was successfully deserialized
     */
    boolean deserialize(PlayerCompendiumRecipe recipe, JsonObject params);

    default void register() {
        Registries.CRITERIA_REGISTRY.register(getName(), this);
    }

}
