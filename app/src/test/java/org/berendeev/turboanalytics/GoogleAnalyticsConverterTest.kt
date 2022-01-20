package org.berendeev.turboanalytics

import android.os.Bundle
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class GoogleAnalyticsConverterTest {

    private val converter = GoogleAnalyticsConverter()

    @Test
    fun map_object() {

        @AnalyticsEvent("test_event_name", AnalyticsService.GOOGLE)
        class User(
            @EventProperty("name")
            val name: String,

            @EventProperty("soname")
            val soname: String,
        )

        @AnalyticsEvent("test_event_name", AnalyticsService.GOOGLE)
        class TestEvent(
            @EventProperty(key = KEY)
            var user: User
        )

        val user = User("111", "222")
        val event = TestEvent(user)
        val bundle = converter.convertObject(event)

        assertTrue(KEY in bundle.keySet())
        val userMap = bundle[KEY] as Bundle
        assertNotNull(userMap)
        assertEquals(user.name, userMap["name"])
        assertEquals(user.soname, userMap["soname"])
    }

    @Test
    fun when_several_properties() {
        @AnalyticsEvent("test_event_name", AnalyticsService.GOOGLE)
        class TestEvent(
            @EventProperty(key = "key1")
            val valInConstructor: String,

            @EventProperty(key = "key2")
            var varInConstructor: String
        ) {
            @EventProperty(key = "key3")
            var valInBody: String = "val3"

            @EventProperty(key = "key4")
            var varInBody: String = "val4"
        }

        val event = TestEvent("val1", "val2")
        val result = converter.convertObject(event)
        assertEquals(4, result.keySet().size)
    }

    @Test
    fun map_string() {
        @AnalyticsEvent("test_event_name", AnalyticsService.GOOGLE)
        class TestEvent(
            @EventProperty(key = KEY)
            val userName: String,
        )

        val event = TestEvent("value")
        val result = converter.convertObject(event)

        assertEquals(event.userName, result[KEY])
    }

    @Test
    fun map_int() {
        @AnalyticsEvent("test_event_name", AnalyticsService.GOOGLE)
        class TestEvent(
            @EventProperty(key = KEY)
            val userName: String,
        )

        val event = TestEvent("value")
        val result = converter.convertObject(event)

        assertEquals(event.userName, result[KEY])
    }

    companion object {
        private const val KEY = "KEY"
    }
}
