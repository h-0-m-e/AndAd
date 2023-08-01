package ru.netology.andad

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.netology.andad.ui.StatsView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = findViewById<StatsView>(R.id.StatsView)
        view.data = listOf(
            0.25F,
            0.25F,
            0.25F,
            0.25F
        )

//        val textView = findViewById<TextView>(R.id.label)

//        view.startAnimation(
//            AnimationUtils.loadAnimation(this, R.anim.animation).apply {
//                setAnimationListener(object: Animation.AnimationListener{
//                    override fun onAnimationStart(p0: Animation?) {
//                        textView.text = "Start"
//                    }
//
//                    override fun onAnimationEnd(p0: Animation?) {
//                        textView.text = "End"
//                    }
//
//                    override fun onAnimationRepeat(p0: Animation?) {
//                        textView.text = "Repeat"
//                    }
//                })
//            }
//        )
    }
}
