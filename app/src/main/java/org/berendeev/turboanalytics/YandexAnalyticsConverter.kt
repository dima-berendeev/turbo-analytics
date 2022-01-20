package org.berendeev.turboanalytics

class YandexAnalyticsConverter : BaseAnalyticsConverter<MutableMap<String, Any>>() {

    override fun createContainer(): MutableMap<String, Any> {
        return mutableMapOf()
    }

    override fun MutableMap<String, Any>.putEventProperty(key: String, value: Any) {
        val mappedValue = when (value) {
            is Number, is Boolean, is String -> value
            else -> convertObject(value)
        }
        this[key] = mappedValue
    }
}
