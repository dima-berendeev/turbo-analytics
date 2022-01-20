package org.berendeev.turboanalytics

import android.util.Log

class GoogleAnalyticsServiceAdapter(
    private val converter: GoogleAnalyticsConverter
) : AnalyticsServiceAdapter {

    override fun send(event: Any) {
        val bundle = converter.convertObject(event)
        Log.d(
            "AnalyticsEvent:",
            "service: Google, event: ${converter.getEventName(event)}, content: $bundle"
        )
    }
}
