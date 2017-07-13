/*
 * This file ("TileEntityFueled.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.tile.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class TileEntityFueled extends TileEntity{

    protected int coalTime;
    protected int maxCoalTime;

    private int lastCoal;
    private boolean lastActive;

    public TileEntityFueled(IWorld world, int x, int y){
        super(world, x, y);
    }

    @Override
    public void update(IGameInstance game){
        super.update(game);

        if(!RockBottomAPI.getNet().isClient()){
            boolean smelted = this.tryTickAction();

            if(this.coalTime > 0){
                this.coalTime--;
            }

            if(smelted){
                if(this.coalTime <= 0){
                    ItemInstance inst = this.getFuel();
                    if(inst != null){
                        int amount = (int)(RockBottomAPI.getFuelValue(inst)*this.getFuelModifier());
                        if(amount > 0){
                            this.maxCoalTime = amount;
                            this.coalTime = amount;

                            this.removeFuel();
                        }
                    }
                }
            }
        }

        boolean active = this.isActive();
        if(this.lastActive != active){
            this.lastActive = active;

            this.onActiveChange(active);
        }
    }

    @Override
    protected boolean needsSync(){
        return this.lastCoal != this.coalTime;
    }

    @Override
    protected void onSync(){
        this.lastCoal = this.coalTime;
    }

    public boolean isActive(){
        return this.coalTime > 0;
    }

    public float getFuelPercentage(){
        return (float)this.coalTime/(float)this.maxCoalTime;
    }

    protected abstract boolean tryTickAction();

    protected abstract float getFuelModifier();

    protected abstract ItemInstance getFuel();

    protected abstract void removeFuel();

    protected abstract void onActiveChange(boolean active);

    @Override
    public void save(DataSet set, boolean forSync){
        set.addInt("coal", this.coalTime);
        set.addInt("max_coal", this.maxCoalTime);
    }

    @Override
    public void load(DataSet set, boolean forSync){
        this.coalTime = set.getInt("coal");
        this.maxCoalTime = set.getInt("max_coal");
    }
}
