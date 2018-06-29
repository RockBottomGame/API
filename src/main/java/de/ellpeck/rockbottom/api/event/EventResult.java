/*
 * This file ("EventResult.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.event;

/**
 * The result of any {@link Event} being fired in the {@link IEventHandler},
 * where {@link #DEFAULT} means that the event was unchanged, {@link #MODIFIED}
 * means that the data in the event was modified and {@link #CANCELLED} meaning
 * that the event was cancelled. The first two values have a dynamic usage, but
 * generally speaking, if you change any of the values in the event's class, you
 * should use {@link #MODIFIED} as the result if not otherwise stated in the
 * description of the event's class.
 */
public enum EventResult {
    DEFAULT,
    MODIFIED,
    CANCELLED
}
