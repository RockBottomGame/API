/*
 * This file ("StatisticNotifyEvent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.entity.player.statistics.IStatistics;
import de.ellpeck.rockbottom.api.entity.player.statistics.Statistic;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

/**
 * This event is fired when a {@link Statistic} is being notified to update via
 * {@link IStatistics#notify(IResourceName)}. Cancelling the event will make the
 * statistic not be notified.
 */
public class StatisticNotifyEvent extends Event{

    public final AbstractEntityPlayer player;
    public final IStatistics statistics;
    public Statistic statistic;

    public StatisticNotifyEvent(AbstractEntityPlayer player, IStatistics statistics, Statistic statistic){
        this.player = player;
        this.statistics = statistics;
        this.statistic = statistic;
    }
}
