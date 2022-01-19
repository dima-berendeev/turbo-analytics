package org.berendeev.turboanalytics

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

class AnalyticsEventConverter {
    fun convertToMap(event: AnalyticsEvent): Map<String, Any?> {
        return mapObjectToMap(event)
    }

    private fun mapObjectToMap(dataObject: Any): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        val kClass: KClass<Any> = dataObject::class as KClass<Any>
        for (property in kClass.memberProperties) {
            if (!property.hasEventPropertyAnnotation()) {
                continue
            }
            try {
                val eventPropertyPair = mapEventProperty(property, dataObject)
                map[eventPropertyPair.first] = eventPropertyPair.second
            } catch (t: Throwable) {
                // log and skip
            }
        }
        return map
    }

    private fun mapEventProperty(
        property: KProperty1<Any, *>,
        dataObject: Any
    ): Pair<String, Any?> {
        val eventPropertyKey = property.findAnnotation<EventProperty>()?.key
            ?: throw IllegalStateException("Event property not found")

        val eventPropertyValue = mapEventPropertyValue(property, dataObject)

        return eventPropertyKey to eventPropertyValue
    }

    private fun mapEventPropertyValue(property: KProperty1<Any, *>, dataObject: Any): Any? {
        return when (val objectPropertyValue = property.get(dataObject)) {
            null, is Number, is String -> {
                objectPropertyValue
            }

            else -> {
                mapObjectToMap(objectPropertyValue)
            }
        }
    }
}

private fun KProperty1<Any, *>.hasEventPropertyAnnotation(): Boolean {
    return this.findAnnotation<EventProperty>() != null
}
