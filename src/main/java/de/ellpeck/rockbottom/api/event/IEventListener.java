/*
 * This file ("IEventListener.java") is part of the RockBottomAPI by Ellpeck.
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

/**
 * An event listener that can listen to {@link Event} objects being fired once
 * they have been registered into the {@link IEventHandler} using {@link
 * IEventHandler#registerListener(Class, IEventListener)}.
 *
 * @param <T> A generic type representing the kind of event that this listener
 *            accepts
 */
public interface IEventListener<T extends Event> {

    /**
     * This method is called whenever the event that this listener listens to is
     * fired. It receivs the result of the event, which is {@link
     * EventResult#DEFAULT} by default, but which can be changed by other
     * listeners that are in the queue before this one returning a different
     * {@link EventResult} in their {@code listen} method. Generally, if you
     * don't change the event result, yourself, you should always return the
     * result parameter that you are given rather than just {@link
     * EventResult#DEFAULT}.
     * <p>
     * Returning {@link EventResult#CANCELLED} will cancel any event's listening
     * process, meaning the queue of listeners will be cleared so that listeners
     * later in the queue will not listen to this event.
     *
     * @param result The event result of the previous listener. This should be
     *               passed on if the event's result should not be changed
     * @param event  The actual event instance that can be checked or modified.
     *               Generally speaking, if an event has parameters that are
     *               {@code non-final}, they can be changed by the event
     *               listener to change the outcome of the event.
     * @return The result passed into this method if unchanged, else the
     * corresponding event result
     */
    EventResult listen(EventResult result, T event);
}
