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

package cz.dvratil.liflow

import android.arch.persistence.room.Room
import cz.dvratil.liflow.store.Store

class Application : android.app.Application {
    constructor(): super() {
        _instance = this
        _db = Room.databaseBuilder(applicationContext, Store::class.java, "liflow")
                .allowMainThreadQueries()
                .build()
    }

    private var _db : Store

    companion object {
        private var _instance : Application? = null

        // We can be sure that by the time getInstance() would be called by application
        // code _instance will already be initialized, thus the cast here is safe
        fun getInstance() = _instance as Application
        fun getContext() = getInstance().applicationContext
        fun getStore() = getInstance()._db
    }
}