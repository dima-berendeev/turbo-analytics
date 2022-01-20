package org.berendeev.turboanalytics

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

abstract class BaseAnalyticsConverter<T : Any> {
    fun getEventName(event: Any): String {
        return (event::class.findAnnotation<AnalyticsEvent>()?.eventName
            ?: throw IllegalStateException("Annotation @AnalyticsEvent not found for event: ${event::class.simpleName}"))
    }

    fun convertObject(eventObject: Any): T {
        val container = createContainer()
        val kClass = eventObject::class as KClass<Any>
        for (property in kClass.memberProperties) {
            try {
                val key = property.getEventPropertyKey()
                val value = property.get(eventObject)
                if (value != null) {
                    container.putEventProperty(key, value)
                }
            } catch (t: Throwable) {
                // log and skip
            }
        }
        return container
    }

    private fun <T> KProperty1<T, *>.getEventPropertyKey(): String {
        return this.findAnnotation<EventProperty>()?.key
            ?: throw IllegalStateException("Event property not found")
    }

    protected abstract fun T.putEventProperty(key: String, value: Any)

    protected abstract fun createContainer(): T
}