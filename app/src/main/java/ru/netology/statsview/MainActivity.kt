package ru.netology.statsview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.statsview.ui.StatsView

class MainActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<StatsView>(R.id.statsView)
        view.data = listOf(
            500F,
            500F,
            500F,
            500F,
        )
        findViewById<StatsView>(R.id.statsView).pctTotal = 50F //Сколько процентов занимают данные

    }
}