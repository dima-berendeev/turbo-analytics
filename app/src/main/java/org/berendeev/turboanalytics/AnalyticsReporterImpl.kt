package org.berendeev.turboanalytics

import android.util.Log
import kotlin.reflect.full.findAnnotation

class AnalyticsReporterImpl(
    private val googleAnalyticsServiceAdapter: GoogleAnalyticsServiceAdapter,
    private val yandexAnalyticsServiceAdapter: YandexAnalyticsServiceAdapter,
) : AnalyticsReporter {
    override fun send(event: Any) {
        trySend(event)
    }

    private fun trySend(event: Any) {
        try {
            when (getAnalyticsService(event)) {
                AnalyticsService.GOOGLE -> googleAnalyticsServiceAdapter.send(event)
                AnalyticsService.YANDEX -> yandexAnalyticsServiceAdapter.send(event)
            }
        } catch (e: Throwable) {
            Log.e("AnalyticsReporter", e.message, e)
        }
    }

    private fun getAnalyticsService(event: Any): AnalyticsService {
        return (event::class.findAnnotation<AnalyticsEvent>()?.service
            ?: throw IllegalStateException("Annotation @AnalyticsEvent not found for event: ${event::class.simpleName}"))
    }
}
