package org.berendeev.turboanalytics

import android.os.Bundle

class GoogleAnalyticsConverter : BaseAnalyticsConverter<Bundle>() {

    override fun Bundle.putEventProperty(key: String, value: Any) {
        when (value) {
            is Int -> this.putInt(key, value)
            is Long -> this.putLong(key, value)
            is Double -> this.putDouble(key, value)
            is Float -> this.putFloat(key, value)
            is Char -> this.putChar(key, value)
            is String -> this.putString(key, value)
            is Boolean -> this.putBoolean(key, value)
            is Short -> this.putShort(key, value)
            else -> this.putBundle(key, convertObject(value))
        }
    }

    override fun createContainer(): Bundle {
        return Bundle()
    }
}
