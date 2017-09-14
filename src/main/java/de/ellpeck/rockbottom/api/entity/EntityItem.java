/*
 * This file ("EntityItem.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.render.entity.ItemEntityRenderer;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;

public class EntityItem extends Entity{

    private final BoundBox boundingBox = new BoundBox(-0.25, -0.25, 0.25, 0.25);
    private final IEntityRenderer renderer;
    public ItemInstance item;

    private int pickupDelay = 10;

    public EntityItem(IWorld world){
        super(world);
        this.renderer = new ItemEntityRenderer();
    }

    public EntityItem(IWorld world, ItemInstance item){
        this(world);
        this.item = item;
    }

    public static void spawn(IWorld world, ItemInstance inst, double x, double y, double motionX, double motionY){
        EntityItem item = new EntityItem(world, inst);
        item.setPos(x, y);
        item.motionX = motionX;
        item.motionY = motionY;
        world.addEntity(item);
    }

    @Override
    public IEntityRenderer getRenderer(){
        return this.renderer;
    }

    @Override
    public void update(IGameInstance game){
        super.update(game);

        if(this.pickupDelay > 0){
            this.pickupDelay--;
        }

        if(!this.world.isClient()){
            if(this.item != null){
                if(this.ticksExisted >= this.item.getItem().getDespawnTime(this.item)){
                    this.kill();
                }
            }
            else{
                this.kill();
            }
        }
    }

    @Override
    public int getSyncFrequency(){
        return 5;
    }

    @Override
    public int getRenderPriority(){
        return 1;
    }

    public boolean canPickUp(){
        return this.pickupDelay <= 0;
    }

    @Override
    public BoundBox getBoundingBox(){
        return this.boundingBox;
    }

    @Override
    public void applyMotion(){
        this.motionY -= 0.015;

        this.motionX *= this.onGround ? 0.8 : 0.98;
        this.motionY *= 0.98;
    }

    @Override
    public void save(DataSet set){
        super.save(set);

        DataSet itemSet = new DataSet();
        this.item.save(itemSet);
        set.addDataSet("item", itemSet);

        set.addInt("pickup_delay", this.pickupDelay);
    }

    @Override
    public void load(DataSet set){
        super.load(set);

        DataSet itemSet = set.getDataSet("item");
        this.item = ItemInstance.load(itemSet);
        this.pickupDelay = set.getInt("pickup_delay");
    }
}
