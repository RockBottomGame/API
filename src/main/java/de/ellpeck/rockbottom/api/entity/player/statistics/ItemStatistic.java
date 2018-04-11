/*
 * This file ("ItemStatistic.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.gui.AbstractStatGui;
import de.ellpeck.rockbottom.api.gui.component.ComponentMenu;
import de.ellpeck.rockbottom.api.gui.component.ComponentStatistic;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.util.Counter;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemStatistic extends StatisticInitializer<ItemStatistic.Stat>{

    private final ResourceName textureLocation;

    public ItemStatistic(ResourceName name, ResourceName textureLocation){
        super(name);
        this.textureLocation = textureLocation;
    }

    @Override
    public Stat makeStatistic(IStatistics statistics){
        return new Stat(this);
    }

    @Override
    public List<ComponentStatistic> getDisplayComponents(IGameInstance game, Stat stat, AbstractStatGui gui, ComponentMenu menu){
        return RockBottomAPI.getInternalHooks().makeItemStatComponents(game, stat, stat.map, gui, menu, this.textureLocation);
    }

    public static class Stat extends Statistic{

        private final Map<Item, Counter> map = new HashMap<>();
        private int total;

        public Stat(StatisticInitializer initializer){
            super(initializer);
        }

        public void update(Item item){
            this.map.computeIfAbsent(item, t -> new Counter(0)).add(1);
            this.total++;
        }

        public int getTotal(){
            return this.total;
        }

        public int getValue(Item item){
            Counter value = this.map.get(item);
            return value != null ? value.get() : 0;
        }

        @Override
        public void save(DataSet set){
            int counter = 0;
            for(Map.Entry<Item, Counter> entry : this.map.entrySet()){
                set.addString("item_"+counter, entry.getKey().getName().toString());
                set.addInt("value_"+counter, entry.getValue().get());
                counter++;
            }
            set.addInt("count", counter);
            set.addInt("total", this.total);
        }

        @Override
        public void load(DataSet set){
            this.map.clear();

            int count = set.getInt("count");
            for(int i = 0; i < count; i++){
                ResourceName name = new ResourceName(set.getString("item_"+i));
                Item item = RockBottomAPI.ITEM_REGISTRY.get(name);
                if(item != null){
                    int counter = set.getInt("value_"+i);
                    this.map.put(item, new Counter(counter));
                }
            }
            this.total = set.getInt("total");
        }

        @Override
        public String toString(){
            return this.map.toString();
        }
    }
}
