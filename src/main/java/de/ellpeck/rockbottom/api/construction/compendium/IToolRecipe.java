package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.construction.ConstructionTool;

import java.util.List;

public interface IToolRecipe {

    List<ConstructionTool> getRequiredTools();

}
