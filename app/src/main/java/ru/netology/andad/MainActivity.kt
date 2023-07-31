package ru.netology.andad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.andad.ui.StatsView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<StatsView>(R.id.StatsView).data = 70
    }
}
