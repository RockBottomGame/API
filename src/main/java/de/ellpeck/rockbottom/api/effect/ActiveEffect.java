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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Objects;

public class ActiveEffect {

    private final IEffect effect;
    private int time;

    public ActiveEffect(IEffect effect, int time) {
        this.effect = effect;
        this.time = time;
    }

    public ActiveEffect(IEffect effect) {
        this(effect, 0);
    }

    public static ActiveEffect load(DataSet set) {
        String name = set.getString("effect_name");
        IEffect effect = RockBottomAPI.EFFECT_REGISTRY.get(new ResourceName(name));

        if (effect != null) {
            int time = set.getInt("time");
            return new ActiveEffect(effect, time);
        } else {
            RockBottomAPI.logger().info("Could not load active effect from data set " + set + " because name " + name + " is missing!");
            return null;
        }
    }

    public IEffect getEffect() {
        return this.effect;
    }

    public int getTime() {
        return this.time;
    }

    public ActiveEffect setTime(int time) {
        this.time = time;
        return this;
    }

    public ActiveEffect addTime(int time) {
        return this.setTime(this.time + time);
    }

    public ActiveEffect removeTime(int time) {
        return this.setTime(this.time - time);
    }

    public ActiveEffect copy() {
        return new ActiveEffect(this.getEffect(), this.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ActiveEffect) {
            ActiveEffect effect = (ActiveEffect) o;
            return this.time == effect.time && Objects.equals(this.effect, effect.effect);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.effect, this.time);
    }

    public void save(DataSet set) {
        set.addString("effect_name", this.effect.getName().toString());
        set.addInt("time", this.time);
    }
}
