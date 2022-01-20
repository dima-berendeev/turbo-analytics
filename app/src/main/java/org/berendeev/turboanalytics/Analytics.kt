package org.berendeev.turboanalytics

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AnalyticsEvent(val eventName: String, val service: AnalyticsService)

enum class AnalyticsService {
    GOOGLE,
    YANDEX
}

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventProperty(val key: String)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IgnoreProperty

interface AnalyticsReporter {
    fun send(event: Any)
}

interface AnalyticsServiceAdapter {
    fun send(event: Any)
}
