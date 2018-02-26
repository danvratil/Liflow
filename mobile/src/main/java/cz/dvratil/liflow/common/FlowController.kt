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

package cz.dvratil.liflow.common

import android.content.Context
import cz.dvratil.liflow.Application
import cz.dvratil.liflow.store.Flow as StoreFlow

import java.lang.ref.WeakReference

class FlowController private constructor(context: Context) {
    init {
        // TODO: Load $stuff
        var flows = Application.getStore().flowDao().getAllFlows()
        for (flow in flows) {
            _flows.add(FlowFactory.create(flow.id))
        }

    }

    companion object: SingletonHolder<FlowController, Context>(::FlowController)

    private var _flows = mutableListOf<Flow>()
    protected var _observers = mutableListOf<WeakReference<Observer>>()

    abstract class Observer {
        abstract fun flowAdded(flow: Flow)
        abstract fun flowRemoved(flowId: Long)
    }

    fun addObserver(observer: Observer) {
        val obs = WeakReference(observer)
        if (!_observers.contains(obs)) {
            _observers.add(obs)
        }
    }

    fun removeObserver(observer: Observer) {
        _observers.remove(WeakReference(observer))
    }

    fun addFlow(name: String, type: String): Long {
        val id = Application.getStore().flowDao().insertFlow(StoreFlow(name, type))
        var flow = FlowFactory.create(id)

        for (observer in _observers) {
            observer.get()?.flowAdded(flow)
        }

        return id
    }

    fun removeFlow(flowId: Long) {
        if (_flows.remove(_flows.find { it.id() == flowId })) {
            Application.getStore().flowDao().deleteFlow(flowId)
            for (observer in _observers) {
                observer.get()?.flowRemoved(flowId)
            }
        }
    }

    fun flows() = _flows.toList()
}