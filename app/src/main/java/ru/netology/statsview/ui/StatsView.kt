package ru.netology.statsview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.netology.statsview.R
import ru.netology.statsview.utils.AndroidUtils

import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,


    ) : View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes
) {
    private var textSize = AndroidUtils.dp(context, 30).toFloat()
    private var lineWidth = AndroidUtils.dp(context, 5)
    private var colors = emptyList<Int>()
    private var colorEmpty = 0

    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView) {
            textSize = getDimension(R.styleable.StatsView_textSize, textSize)
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth.toFloat()).toInt()
            colors = listOf(
                getColor(R.styleable.StatsView_color1, generateRandomColor()),
                getColor(R.styleable.StatsView_color2, generateRandomColor()),
                getColor(R.styleable.StatsView_color3, generateRandomColor()),
                getColor(R.styleable.StatsView_color4, generateRandomColor()),
            )
            colorEmpty=getColor(R.styleable.StatsView_colorEmpty, 0xFFFFFFFF.toInt())
        }
    }

    var data: List<Float> = emptyList()
        set(value) {
            field = value
            invalidate()
        }
    var pctTotal = 100F



    private var radius = 0F
    private var center = PointF()
    private var oval = RectF()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            strokeWidth = lineWidth.toFloat()
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            textSize = this@StatsView.textSize
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        radius = min(w, h) / 2F - lineWidth
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius,
            center.y - radius,
            center.x + radius,
            center.y + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()) {
            return
        }
        val dataOfShare : List<Float> = changeToShare(data,pctTotal)
        var startAngle = -90F
        dataOfShare.forEachIndexed() { index, datum ->
            val angle = datum * 360F
            if(dataOfShare.lastIndex==index && pctTotal<100){
                paint.color = colorEmpty
            }
            else {
                paint.color = colors.getOrElse(index) { generateRandomColor() }
            }
            canvas.drawArc(oval, startAngle, angle, false, paint)
            startAngle += angle
        }

        paint.color = colors.first()
        canvas.drawPoint(center.x,center.y-radius,paint)
        canvas.drawText(
            "%.2f%%".format(pctTotal),
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint
        )

    }
}

fun changeToShare(data: List<Float>, pctTotal: Float): List<Float> {
    if (pctTotal>=100F) {
        var dataOfShare = emptyList<Float>()
        data.forEach { dataOfShare = dataOfShare + (it / data.sum()) }
        return dataOfShare
    }
    else{
        var dataOfShare = emptyList<Float>()
        val sum = data.sum()/pctTotal*100
        data.forEach { dataOfShare = dataOfShare + (it / sum) }
        dataOfShare = dataOfShare + (100-pctTotal)/100
        return dataOfShare
    }
}
fun generateRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())