/*
 * This file ("StatisticInitializer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity.player.statistics;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.gui.AbstractStatGui;
import de.ellpeck.rockbottom.api.gui.component.ComponentMenu;
import de.ellpeck.rockbottom.api.gui.component.ComponentStatistic;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public abstract class StatisticInitializer<T extends Statistic> {

    private final ResourceName name;

    public StatisticInitializer(ResourceName name) {
        this.name = name;
    }

    public ResourceName getName() {
        return this.name;
    }

    public abstract T makeStatistic(IStatistics statistics);

    public abstract List<ComponentStatistic> getDisplayComponents(IGameInstance game, T stat, AbstractStatGui gui, ComponentMenu menu);

    public StatisticInitializer<T> register() {
        RockBottomAPI.STATISTICS_REGISTRY.register(this.getName(), this);
        return this;
    }

}
