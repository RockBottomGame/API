package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.event.Event;

public class RecipeLearnEvent extends Event {

    public final AbstractPlayerEntity player;
    public PlayerCompendiumRecipe recipe;
    public boolean announce;

    public RecipeLearnEvent(AbstractPlayerEntity player, PlayerCompendiumRecipe recipe, boolean announce) {
        this.player = player;
        this.recipe = recipe;
        this.announce = announce;
    }

}
