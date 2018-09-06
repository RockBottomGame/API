/*
 * This file ("IEventHandler.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event;

import de.ellpeck.rockbottom.api.RockBottomAPI;

/**
 * The class for handling and firing different {@link Event} objects that can be
 * used by mods to hook into the game's and other mods' functionalities. You can
 * receive an instance of this class by using {@link RockBottomAPI#getEventHandler()}.
 * You can find a list of events that are fired in the normal game in the {@code
 * impl} sub-package.
 */
public interface IEventHandler {

    /**
     * Registers an {@link IEventListener} that will listen to a certain kind of
     * event. Doing this will cause the listener's {@link
     * IEventListener#listen(EventResult, Event)} method to be called whenever
     * the event is fired.
     *
     * @param type     The type of event to listen for
     * @param listener The listener
     * @param <T>      A generic type representing the event that is listened
     *                 to
     * @see IEventListener#listen(EventResult, Event)
     */
    <T extends Event> void registerListener(Class<T> type, IEventListener<T> listener);

    /**
     * Unregisters an {@link IEventListener} from listening to a certain kind of
     * event.
     *
     * @param type     The type of event to unregister from
     * @param listener The listener to unregister
     * @param <T>      A generic type representing the event
     */
    <T extends Event> void unregisterListener(Class<T> type, IEventListener<T> listener);

    /**
     * Unregisters all {@link IEventListener} objects that are listening to a
     * certain kind of event.
     *
     * @param type The type of event to unregister all listeners of
     */
    void unregisterAllListeners(Class<? extends Event> type);

    /**
     * Fires an event, making all of the registered listeners listen to it and,
     * in the end, return an {@link EventResult} representing if the event was
     * {@link EventResult#MODIFIED} or {@link EventResult#CANCELLED}. Firing
     * events is only needed if you add your own implementations of the {@link
     * Event} class.
     *
     * @param event The event to fire
     * @return The result
     * @see #registerListener(Class, IEventListener)
     */
    EventResult fireEvent(Event event);
}
