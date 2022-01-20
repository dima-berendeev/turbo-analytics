package org.berendeev.turboanalytics

import android.util.Log

class YandexAnalyticsServiceAdapter(
    private val converter: YandexAnalyticsConverter
) : AnalyticsServiceAdapter {

    override fun send(event: Any) {
        val map = converter.convertObject(event)
        Log.d(
            "AnalyticsEvent:",
            "service: Yandex, event: ${converter.getEventName(event)}, content: $map"
        )
    }
}
