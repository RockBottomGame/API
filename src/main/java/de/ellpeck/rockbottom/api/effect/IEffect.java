/*
 * This file ("IEffect.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.effect;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public interface IEffect {

    boolean isBad(Entity entity);

    boolean isInstant(Entity entity);

    void updateLasting(ActiveEffect effect, Entity entity);

    void activateInstant(ActiveEffect effect, Entity entity);

    void onAddedOrLoaded(ActiveEffect effect, Entity entity, boolean loaded);

    void onRemovedOrEnded(ActiveEffect effect, Entity entity, boolean ended);

    int getMaxDuration(Entity entity);

    ResourceName getName();

    ResourceName getUnlocalizedName(ActiveEffect effect, Entity entity);

    ResourceName getIcon(ActiveEffect effect, Entity entity);

    default void register() {
        RockBottomAPI.EFFECT_REGISTRY.register(this.getName(), this);
    }
}
