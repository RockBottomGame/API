/*
 * This file ("AITask.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity.ai;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;

public abstract class AITask<T extends Entity>{

    private final int priority;

    public AITask(int priority){
        this.priority = priority;
    }

    public int getPriority(){
        return this.priority;
    }

    public abstract boolean shouldStartExecution(T entity);

    public abstract boolean shouldEndExecution(T entity);

    public abstract void execute(IGameInstance game, T entity);

    public void onExecutionStarted(AITask<T> previousTask, T entity){

    }

    public void onExecutionEnded(AITask<T> nextTask, T entity){

    }

    public AITask getNextTask(AITask<T> expectedNextTask, T entity){
        return expectedNextTask;
    }

    public void save(DataSet set, boolean forSync){

    }

    public void load(DataSet set, boolean forSync){

    }
}
