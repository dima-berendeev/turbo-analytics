package org.berendeev.turboanalytics

class AnalyticsReporterImpl(
    private val analyticsSystems: AnalyticsSystems,
    private val eventConverter: AnalyticsEventConverter
) : AnalyticsReporter {
    override fun send(event: AnalyticsEvent) {
        val map = eventConverter.convertToMap(event)
        analyticsSystems.getForEvent(event.eventName).forEach { analyticsSystem ->
            analyticsSystem.send(event.eventName, map)
        }
    }
}
