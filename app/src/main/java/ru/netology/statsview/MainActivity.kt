package ru.netology.statsview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.statsview.ui.StatsView

class MainActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<StatsView>(R.id.statsView).data= listOf(
            500F,
            500F,
            500F,
            500F,
        )
        findViewById<StatsView>(R.id.statsView).pctTotal = 50F //Сколько процентов занимают данные
    }
}