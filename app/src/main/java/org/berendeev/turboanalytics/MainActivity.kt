package org.berendeev.turboanalytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.berendeev.turboanalytics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val googleAnalyticsServiceAdapter = GoogleAnalyticsServiceAdapter(
        GoogleAnalyticsConverter()
    )
    private val yandexAnalyticsServiceAdapter = YandexAnalyticsServiceAdapter(
        YandexAnalyticsConverter()
    )
    private val analyticsReporter = AnalyticsReporterImpl(
        googleAnalyticsServiceAdapter,
        yandexAnalyticsServiceAdapter
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.yandexEventButton.setOnClickListener {
            analyticsReporter.send(YandexButtonClickedEvent(System.currentTimeMillis()))
        }

        binding.googleEventButton.setOnClickListener {
            analyticsReporter.send(GoogleButtonClickedEvent(System.currentTimeMillis()))
        }
    }
}

@AnalyticsEvent("GoogleButton.Click", AnalyticsService.GOOGLE)
class GoogleButtonClickedEvent(
    @EventProperty("time")
    val time: Long
)

@AnalyticsEvent("YandexButton.Click", AnalyticsService.YANDEX)
class YandexButtonClickedEvent(
    @EventProperty("time")
    val time: Long
)
