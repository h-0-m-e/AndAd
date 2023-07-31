package ru.netology.andad.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.andad.R
import ru.netology.andad.utils.AndroidUtils
import java.lang.Integer.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes
) {

    private var radius = 0F
    private var center = PointF(0F, 0F)
    private var oval = RectF(0F, 0F, 0F, 0F)

    private var lineWidth = AndroidUtils.dp(context, 5).toFloat()
    private var fontSize = AndroidUtils.dp(context, 40).toFloat()
    private var colors = emptyList<Int>()
    private var backgroundColor: Int = randomColor()

    var data: Int = -1

    private var _data: List<Float> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView) {
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth)
            fontSize = getDimension(R.styleable.StatsView_textSize, fontSize)
            val resId = getResourceId(R.styleable.StatsView_colors, 0)
            colors = resources.getIntArray(resId).toList()
            backgroundColor = R.color.grey
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = fontSize
    }

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius, center.y - radius,
            center.x + radius, center.y + radius,
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data == -1) {
            return
        }

        _data = calculateDataToPercents(data)

        circlePaint.color = backgroundColor
        canvas.drawCircle(center.x,center.y, radius, circlePaint)

        var startFrom = -90F
        for ((index, datum) in _data.withIndex()) {
            val angle = 360F * datum
            paint.color = colors.getOrNull(index) ?: randomColor()
            canvas.drawArc(oval, startFrom, angle, false, paint)

            startFrom += angle
        }
        paint.color = colors.first()
        canvas.drawArc(oval,-90F, 0.1F, false,paint)

        canvas.drawText(
            "%.2f%%".format(_data.sum() * 100),
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint,
        )
    }

    private fun randomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())

    private fun calculateDataToPercents(data: Int): List<Float> {
        val fullPart = 0.25F
        var calculatedData = listOf(
            fullPart,
            fullPart,
            fullPart,
            fullPart
        )

        if (data >= 100){
            return calculatedData
        }

        if (data <= 0){
            return emptyList()
        }

        when(data/25){
            0->{ calculatedData = listOf(((data).toFloat()/100)) }
            1->{ calculatedData = listOf(fullPart, ((data).toFloat()/100 - fullPart)) }
            2->{ calculatedData = listOf(fullPart, fullPart, ((data).toFloat()/100 - fullPart * 2)) }
            3->{ calculatedData = listOf(fullPart, fullPart, fullPart, ((data).toFloat()/100 - fullPart * 3)) }
        }
        return calculatedData
    }
}
