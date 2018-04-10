/*
 * This file ("TileStatistic.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentMenu;
import de.ellpeck.rockbottom.api.gui.component.ComponentStatistic;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Counter;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TileStatistic extends StatisticInitializer<TileStatistic.Stat>{

    public TileStatistic(ResourceName name){
        super(name);
    }

    @Override
    public Stat makeStatistic(IStatistics statistics){
        return new Stat(this);
    }

    @Override
    public List<ComponentStatistic> getDisplayComponents(IGameInstance game, Stat stat, Gui gui, ComponentMenu menu){
        List<ComponentStatistic> list = new ArrayList<>();
        for(Map.Entry<Tile, Counter> entry : stat.map.entrySet()){
            Tile tile = entry.getKey();
            Item item = tile.getItem();
            if(item != null){
                Counter value = entry.getValue();
                ItemInstance instance = new ItemInstance(item);
                String statName = game.getAssetManager().localize(this.getName().addPrefix("stat."));

                list.add(new ComponentStatistic(gui, () -> {
                    instance.setMeta((game.getTotalTicks()/Constants.TARGET_TPS)%(item.getHighestPossibleMeta()+1));
                    return String.format(statName, instance.getDisplayName());
                }, value :: toString, value.get()){
                    @Override
                    public void renderStatGraphic(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
                        ITileRenderer renderer = tile.getRenderer();
                        if(renderer != null){
                            renderer.renderItem(game, manager, g, tile, instance, x+1, y+1, 12F, Colors.WHITE);
                        }
                    }
                });
            }
        }
        return list;
    }

    public static class Stat extends Statistic{

        private final Map<Tile, Counter> map = new HashMap<>();

        public Stat(StatisticInitializer initializer){
            super(initializer);
        }

        public void update(Tile tile){
            this.map.computeIfAbsent(tile, t -> new Counter(0)).add(1);
        }

        @Override
        public void save(DataSet set){
            int counter = 0;
            for(Map.Entry<Tile, Counter> entry : this.map.entrySet()){
                set.addString("tile_"+counter, entry.getKey().getName().toString());
                set.addInt("value_"+counter, entry.getValue().get());
                counter++;
            }
            set.addInt("amount", counter);
        }

        @Override
        public void load(DataSet set){
            this.map.clear();

            int amount = set.getInt("amount");
            for(int i = 0; i < amount; i++){
                ResourceName name = new ResourceName(set.getString("tile_"+i));
                Tile tile = RockBottomAPI.TILE_REGISTRY.get(name);
                if(tile != null){
                    int counter = set.getInt("value_"+i);
                    this.map.put(tile, new Counter(counter));
                }
            }
        }

        @Override
        public String toString(){
            return this.map.toString();
        }
    }
}
