/*
 * Copyright (C) 2018  Daniel Vrátil <me@dvratil.cz>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cz.dvratil.liflow.store

import android.arch.persistence.room.*

@Dao interface FlowDao {

    @Query("SELECT * FROM Flow")
    fun getAllFlows(): List<Flow>

    @Query("SELECT * FROM Flow WHERE id = :flowId")
    fun getFlowById(flowId: Long): Flow

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertFlow(flow: Flow): Long

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateFlow(flow: Flow)

    @Query("DELETE FROM Flow WHERE id = :flowId")
    fun deleteFlow(flowId: Long)
}
