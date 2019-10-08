package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;

public class RecipeLearnEvent extends Event {

    public final AbstractEntityPlayer player;
    public PlayerCompendiumRecipe recipe;
    public boolean announce;

    public RecipeLearnEvent(AbstractEntityPlayer player, PlayerCompendiumRecipe recipe, boolean announce) {
        this.player = player;
        this.recipe = recipe;
        this.announce = announce;
    }

}
