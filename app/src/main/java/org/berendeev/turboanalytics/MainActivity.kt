package org.berendeev.turboanalytics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.berendeev.turboanalytics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val analyticSystem = object : AnalyticsSystem {
        override fun send(eventName: String, map: Map<String, Any?>) {
            Log.d("AnalyticsEvent:", "name: $eventName, content$map")
        }
    }
    private val analyticsSystems = object : AnalyticsSystems {
        override fun getForEvent(eventName: String): List<AnalyticsSystem> {
            return listOf(analyticSystem)
        }
    }
    private val analyticsReporter = AnalyticsReporterImpl(
        analyticsSystems,
        AnalyticsEventConverter()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticsReporter.send(ActivityOpenedEvent(System.currentTimeMillis()))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            analyticsReporter.send(FloatButtonClickedEvent(System.currentTimeMillis()))
        }
    }
}

class FloatButtonClickedEvent(
    @EventProperty("time")
    val time: Long
) : AnalyticsEvent("FloatButton.Clicked")

class ActivityOpenedEvent(
    @EventProperty("time")
    val time: Long
) : AnalyticsEvent("Activity.Created")
