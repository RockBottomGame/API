/*
 * This file ("ActiveEffect.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Objects;

public class ActiveEffect {

    private final IEffect effect;
    private int time;
    private int level;

    public ActiveEffect(IEffect effect, int time, int level) {
        this.effect = effect;
        this.time = time;
        this.level = level;
    }

    public ActiveEffect(IEffect effect, int time) {
        this(effect, time, 1);
    }

    public ActiveEffect(IEffect effect) {
        this(effect, 0);
    }

    public static ActiveEffect load(DataSet set) {
        String name = set.getString("effect_name");
        IEffect effect = Registries.EFFECT_REGISTRY.get(new ResourceName(name));

        if (effect != null) {
            int time = set.getInt("time");
            int level = set.getInt("level");
            return new ActiveEffect(effect, time, level);
        } else {
            RockBottomAPI.logger().info("Could not load active effect from data set " + set + " because name " + name + " is missing!");
            return null;
        }
    }

    public IEffect getEffect() {
        return this.effect;
    }

    public String getDisplayName(IAssetManager manager, Entity entity) {
        return manager.localize(this.effect.getUnlocalizedName(this, entity)) + " (" + this.level + ')';
    }

    public String getDisplayTime() {
        int seconds = this.getTime() / Constants.TARGET_TPS;
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    public int getTime() {
        return this.time;
    }

    public ActiveEffect setTime(int time) {
        this.time = time;
        return this;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ActiveEffect addTime(int time) {
        return this.setTime(this.time + time);
    }

    public ActiveEffect removeTime(int time) {
        return this.setTime(this.time - time);
    }

    public ActiveEffect copy() {
        return new ActiveEffect(this.getEffect(), this.getTime(), this.getLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ActiveEffect) {
            ActiveEffect effect = (ActiveEffect) o;
            return this.time == effect.time && this.level == effect.level && Objects.equals(this.effect, effect.effect);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.effect, this.time, this.level);
    }

    public void save(DataSet set) {
        set.addString("effect_name", this.effect.getName().toString());
        set.addInt("time", this.time);
        set.addInt("level", this.level);
    }
}
