/*
 * This file ("ItemSword.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.util.BoundingBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.util.ArrayList;
import java.util.List;

public class SwordItem extends ToolItem {

    private final double knockback;
    private final double attackDistance;
    private final int cooldown;

    public SwordItem(ResourceName name, int durability, int attackDamage, int cooldown, double attackDistance, double knockback) {
        super(name, 0F, durability, ToolProperty.SWORD, attackDamage);
        this.cooldown = cooldown;
        this.attackDistance = attackDistance;
        this.knockback = knockback;
    }

    @Override
    public List<Entity> getCustomAttackableEntities(IWorld world, double mouseX, double mouseY, AbstractPlayerEntity player, ItemInstance instance) {
        List<Entity> entities = new ArrayList<>();

        double x = player.getX();
        double y = player.getY();
        double minX = x - (player.facing == Direction.LEFT ? this.attackDistance : 0);
        double maxX = x + (player.facing == Direction.RIGHT ? this.attackDistance : 0);

        for (Entity entity : world.getEntities(new BoundingBox(minX, y - this.attackDistance, maxX, y + this.attackDistance), entity -> entity != player)) {
            if (Util.distanceSq(entity.getX(), entity.getY(), x, y) <= this.attackDistance * this.attackDistance) {
                entities.add(entity);
            }
        }

        return entities;
    }

    @Override
    public boolean onEntityAttack(IWorld world, double mouseX, double mouseY, AbstractPlayerEntity player, Entity entity, ItemInstance instance) {
        if (!entity.world.isClient()) {
            this.takeDamage(instance, player, 1);
            entity.applyKnockback(player, this.knockback);
        }
        return true;
    }

    @Override
    public boolean attacksMultipleEntities(IWorld world, double mouseX, double mouseY, AbstractPlayerEntity player, ItemInstance instance) {
        return true;
    }

    @Override
    public int getAttackDamage(IWorld world, Entity entity, double mouseX, double mouseY, AbstractPlayerEntity player, ItemInstance instance) {
        return this.getToolProperties(instance).get(ToolProperty.SWORD);
    }

    @Override
    public int getAttackCooldown(IWorld world, double mouseX, double mouseY, AbstractPlayerEntity player, ItemInstance instance) {
        return this.cooldown;
    }

    @Override
    public boolean canHoldButtonToAttack(IWorld world, double mouseX, double mouseY, AbstractPlayerEntity player, ItemInstance instance) {
        return true;
    }
}
