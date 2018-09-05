package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public abstract class BasicCompendiumRecipe implements ICompendiumRecipe {

    private final ResourceName name;

    public BasicCompendiumRecipe(ResourceName name) {
        this.name = name;
    }

    @Override
    public ResourceName getName() {
        return this.name;
    }
}
