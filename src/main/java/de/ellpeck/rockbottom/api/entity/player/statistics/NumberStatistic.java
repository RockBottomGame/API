/*
 * This file ("NumberStatistic.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public final class NumberStatistic extends StatisticInitializer<NumberStatistic.Stat>{

    private final int defaultValue;

    public NumberStatistic(ResourceName name){
        this(name, 0);
    }

    public NumberStatistic(ResourceName name, int defaultValue){
        super(name);
        this.defaultValue = defaultValue;
    }

    @Override
    public Stat makeStatistic(IStatistics statistics){
        return new Stat(this, this.defaultValue);
    }

    public static class Stat extends Statistic{

        private int value;

        public Stat(StatisticInitializer initializer, int defaultValue){
            super(initializer);
            this.value = defaultValue;
        }

        public void update(){
            this.value++;
        }

        @Override
        public void save(DataSet set){
            set.addInt("value", this.value);
        }

        @Override
        public void load(DataSet set){
            this.value = set.getInt("value");
        }

        public int getValue(){
            return this.value;
        }

        @Override
        public String toString(){
            return String.valueOf(this.value);
        }
    }
}
