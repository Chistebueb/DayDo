package com.example.daydo

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)
        window.statusBarColor = Color.parseColor("#191919")

        val screenHeight = resources.displayMetrics.heightPixels
        val screenWidth = resources.displayMetrics.widthPixels

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#191919"))
            setPadding(dpToPx(16), dpToPx(46), dpToPx(16), dpToPx(16))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val titleTextView = TextView(this).apply {
            text = "your progress"
            textSize = 24f
            setTextColor(Color.parseColor("#FFFFFF"))
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = dpToPx(8)
            }
        }
        layout.addView(titleTextView)

        val dateTextView = TextView(this).apply {
            text = "march 18, 2025"
            textSize = 14f
            setTextColor(Color.parseColor("#777777"))
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = dpToPx(8)
            }
        }
        layout.addView(dateTextView)

        addVerticalSpace(layout, 16)
        layout.addView(createStreakCard())

        addVerticalSpace(layout, 24)
        val recentTasksLabel = TextView(this).apply {
            text = "recent tasks"
            textSize = 14f
            setTextColor(Color.parseColor("#777777"))
            typeface = Typeface.create("sans-serif-regular", Typeface.NORMAL)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = dpToPx(8)
            }
        }
        layout.addView(recentTasksLabel)

        addVerticalSpace(layout, 16)

        val tasks = listOf(
            "Call an old friend",
            "Greet 5 people",
            "Drink 6 Glasses of water",
            "Eat an Apple",
            "Do 15 Pushups"
        )

        tasks.forEach { taskText ->
            layout.addView(createSmallCard(taskText))
            addVerticalSpace(layout, 8)
        }

        setContentView(layout)
    }

    private fun createStreakCard(): CardView {
        val cardView = CardView(this).apply {
            radius = dpToPx(5).toFloat()
            setCardBackgroundColor(Color.parseColor("#232223"))
            cardElevation = 0f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val horizontalLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val leftBar = View(this).apply {
            setBackgroundColor(Color.parseColor("#BB86FC"))
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(6),
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        val textLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val currentStreakLabel = TextView(this).apply {
            text = "current streak"
            textSize = 18f
            setTextColor(Color.parseColor("#888888"))
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        textLayout.addView(currentStreakLabel)

        addVerticalSpace(textLayout, 8)

        val streakNumber = TextView(this).apply {
            text = "11"
            textSize = 72f
            setTextColor(Color.parseColor("#FFFFFF"))
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
        }
        textLayout.addView(streakNumber)

        horizontalLayout.addView(leftBar)
        horizontalLayout.addView(textLayout)
        cardView.addView(horizontalLayout)

        return cardView
    }

    private fun createSmallCard(content: String): CardView {
        val cardView = CardView(this).apply {
            radius = dpToPx(5).toFloat()
            setCardBackgroundColor(Color.parseColor("#232223"))
            cardElevation = 0f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val horizontalLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val leftBar = View(this).apply {
            setBackgroundColor(Color.parseColor("#9B70CF"))
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(3),
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        val textLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val taskTextView = TextView(this).apply {
            text = content
            textSize = 16f
            setTextColor(Color.parseColor("#888888"))
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        textLayout.addView(taskTextView)

        horizontalLayout.addView(leftBar)
        horizontalLayout.addView(textLayout)
        cardView.addView(horizontalLayout)

        return cardView
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density + 0.5f).toInt()
    }

    private fun addVerticalSpace(parent: LinearLayout, heightDp: Int) {
        val space = View(this)
        space.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(heightDp)
        )
        parent.addView(space)
    }
}
