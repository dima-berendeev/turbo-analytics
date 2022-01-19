package org.berendeev.turboanalytics

import org.junit.Assert.*
import org.junit.Test

class AnalyticsEventConverterTest {
    @Test
    fun map_object() {
        class User(
            @EventProperty("name")
            val name: String,

            @EventProperty("soname")
            val soname: String,
        )

        class TestEvent(
            @EventProperty(key = KEY)
            var user: User
        ) : AnalyticsEvent("test_event_name")

        val user = User("111", "222")
        val event = TestEvent(user)
        val map = AnalyticsEventConverter()
            .convertToMap(event)

        assertTrue(KEY in map.keys)
        val userMap = map[KEY] as Map<String, String>
        assertNotNull(userMap)
        assertEquals(user.name, userMap["name"])
        assertEquals(user.soname, userMap["soname"])
    }

    @Test
    fun when_several_properties() {
        class TestEvent(
            @EventProperty(key = "key1")
            val valInConstructor: String,

            @EventProperty(key = "key2")
            var varInConstructor: String
        ) : AnalyticsEvent("test_event_name") {
            @EventProperty(key = "key3")
            var valInBody: String = "val3"

            @EventProperty(key = "key4")
            var varInBody: String = "val4"
        }

        val event = TestEvent("val1", "val2")
        val map = AnalyticsEventConverter()
            .convertToMap(event)
        assertEquals(4, map.keys.size)
    }

    @Test
    fun map_string() {
        class TestEvent(
            @EventProperty(key = KEY)
            val userName: String,
        ) : AnalyticsEvent("test_event_name")

        val event = TestEvent("value")
        val map = AnalyticsEventConverter()
            .convertToMap(event)

        assertEquals(event.userName, map[KEY])
    }

    @Test
    fun map_int() {
        class TestEvent(
            @EventProperty(key = KEY)
            val userName: String,
        ) : AnalyticsEvent("test_event_name")

        val event = TestEvent("value")
        val map = AnalyticsEventConverter()
            .convertToMap(event)

        assertEquals(event.userName, map[KEY])
    }

    companion object {
        private const val KEY = "KEY"
    }
}
