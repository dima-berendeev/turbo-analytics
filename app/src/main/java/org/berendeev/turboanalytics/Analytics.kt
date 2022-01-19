package org.berendeev.turboanalytics

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventProperty(val key: String)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IgnoreProperty

abstract class AnalyticsEvent(@IgnoreProperty val eventName: String)

interface AnalyticsReporter {
    fun send(event: AnalyticsEvent)
}

interface AnalyticsSystems {
    fun getForEvent(eventName: String): List<AnalyticsSystem>
}

interface AnalyticsSystem {
    fun send(eventName: String, map: Map<String, Any?>)
}
