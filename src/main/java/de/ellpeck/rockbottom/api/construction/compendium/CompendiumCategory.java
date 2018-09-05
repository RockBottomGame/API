package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Set;

public abstract class CompendiumCategory {

    protected final ResourceName name;

    public CompendiumCategory(ResourceName name) {
        this.name = name;
    }

    public Gui getGuiOverride(Gui parent) {
        return null;
    }

    public boolean shouldDisplay(AbstractEntityPlayer player) {
        return true;
    }

    public String getDisplayText(IAssetManager manager){
        return manager.localize(this.getName().addPrefix("info.compendium_category."));
    }

    public abstract ResourceName getIcon(IGameInstance game, IAssetManager assetManager, IRenderer g);

    public abstract Set<? extends ICompendiumRecipe> getRecipes();

    public ResourceName getName() {
        return this.name;
    }

    public CompendiumCategory register() {
        Registries.COMPENDIUM_CATEGORY_REGISTRY.register(this.getName(), this);
        return this;
    }
}